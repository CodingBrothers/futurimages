package com.codingbrothers.futurimages.config;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;
import java.util.Collections;
import java.util.ResourceBundle;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.ConstraintViolation;
import javax.validation.MessageInterpolator;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.executable.ExecutableValidator;

import org.aopalliance.intercept.MethodInvocation;
import org.hibernate.validator.HibernateValidator;
import org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator;
import org.hibernate.validator.resourceloading.PlatformResourceBundleLocator;

import com.codingbrothers.futurimages.apiv1.APIV1Constants;
import com.codingbrothers.futurimages.apiv1.ClientError;
import com.codingbrothers.futurimages.apiv1.ImagesV1;
import com.codingbrothers.futurimages.apiv1.Response;
import com.codingbrothers.futurimages.apiv1.Responses;
import com.codingbrothers.futurimages.util.DelegatingMessageInterpolator;
import com.codingbrothers.futurimages.util.RequestContext;
import com.codingbrothers.futurimages.util.ValidatingInterceptor;
import com.google.api.server.spi.config.Api;
import com.google.inject.AbstractModule;
import com.google.inject.Provider;
import com.google.inject.Provides;
import com.google.inject.matcher.AbstractMatcher;
import com.google.inject.matcher.Matchers;

public class FuturimagesApiV1Module extends AbstractModule {

	@Override
	protected void configure() {
		configureApis();
		configureValidation();
	}

	@Provides
	@Named(APIV1Constants.API_MESSAGES_RESOURCE_BUNDLE_NAME)
	ResourceBundle provideResourceBundle(RequestContext requestContext) {
		return ResourceBundle.getBundle(APIV1Constants.API_MESSAGES_RESOURCE_BUNDLE_NAME,
				requestContext.getRequestLocale(APIV1Constants.API_MESSAGES_DEFAULT_LOCALE));
	}

	private void configureApis() {
		// bind the apis (a.k.a. SystemServices) as singletons as they're singletons, anyway
		// use asEagerSingleton to also resolve all their possible deps asap (e.g. while warmup requests are hitting the
		// app)
		bind(ImagesV1.class).asEagerSingleton();
	}

	private void configureValidation() {
		// bind BeanValidation
		MessageInterpolator messageInterpolator = new DelegatingMessageInterpolator(
				new ResourceBundleMessageInterpolator(new PlatformResourceBundleLocator(
						APIV1Constants.API_MESSAGES_RESOURCE_BUNDLE_NAME))) {

			@Inject
			private Provider<RequestContext> requestContext;

			@Override
			public String interpolate(String messageTemplate, Context context) {
				return super.interpolate(messageTemplate, context,
						requestContext.get().getRequestLocale(APIV1Constants.API_MESSAGES_DEFAULT_LOCALE));
			}
		};
		requestInjection(messageInterpolator);

		Validator validator = Validation.byProvider(HibernateValidator.class).configure()
				.messageInterpolator(messageInterpolator).buildValidatorFactory().getValidator();
		bind(Validator.class).toInstance(validator); // safe as validator is always thread-safe

		ExecutableValidator executableValidator = validator.forExecutables();
		bind(ExecutableValidator.class).toInstance(executableValidator); // safe as validator is always thread-safe

		// make use of little amount of aop to plumb automatic validation of endpoint parameters in apis
		// this way we don't have to deal with it directly in api impls
		ValidatingInterceptor validatingInterceptor = new ValidatingInterceptor(executableValidator, true, false) {

			@Inject
			@Named(APIV1Constants.API_MESSAGES_RESOURCE_BUNDLE_NAME)
			private Provider<ResourceBundle> resourceBundle;

			@Override
			protected Object handleParametersViolations(MethodInvocation invocation,
					Set<ConstraintViolation<Object>> violations) {
				ClientError errRes = Responses.createClientError(violations, resourceBundle.get());
				return !isIterableOfResponses(invocation.getMethod().getGenericReturnType()) ? errRes : Collections
						.singletonList(errRes);
			}
		};
		requestInjection(validatingInterceptor);
		bindInterceptor(Matchers.inPackage(APIV1Constants.class.getPackage()).and(Matchers.annotatedWith(Api.class)),
				new AbstractMatcher<Method>() {

					@Override
					public boolean matches(Method m) {
						int modifiers = m.getModifiers();
						return Modifier.isPublic(modifiers)
								&& !Modifier.isStatic(modifiers)
								&& !m.isBridge()
								&& (Response.class.isAssignableFrom(m.getReturnType()) || isIterableOfResponses(m
										.getGenericReturnType()));
					}
				}, validatingInterceptor);
	}

	private static boolean isIterableOfResponses(Type type) {
		if (type instanceof ParameterizedType) {
			if (Iterable.class.isAssignableFrom((Class<?>) ((ParameterizedType) type).getRawType())) {
				Type[] actualTypeArguments = ((ParameterizedType) type).getActualTypeArguments();
				if (actualTypeArguments != null && actualTypeArguments.length == 1) {
					if (actualTypeArguments[0] instanceof Class
							&& Response.class.isAssignableFrom((Class<?>) actualTypeArguments[0])) {
						return true;
					} else if (actualTypeArguments[0] instanceof WildcardType
							&& ((WildcardType) actualTypeArguments[0]).getUpperBounds()[0] instanceof Class) {
						if (Response.class.isAssignableFrom((Class<?>) ((WildcardType) actualTypeArguments[0])
								.getUpperBounds()[0])) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}

}
