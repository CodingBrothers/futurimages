package com.codingbrothers.futurimages.config;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ValidationException;

import org.aopalliance.intercept.MethodInvocation;
import org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator;
import org.hibernate.validator.resourceloading.PlatformResourceBundleLocator;

import com.codingbrothers.futurimages.apiv1.ImagesV1;
import com.codingbrothers.futurimages.util.ValidatingInterceptor;
import com.google.api.server.spi.config.Api;
import com.google.inject.AbstractModule;
import com.google.inject.Provider;
import com.google.inject.matcher.Matchers;

public class FuturimagesApiV1Module extends AbstractModule {

	private Provider<ImagesV1> provider;

	@Override
	protected void configure() {
		bind(ImagesV1.class).asEagerSingleton();

		ResourceBundleMessageInterpolator messageInterpolator = new ResourceBundleMessageInterpolator(
				new PlatformResourceBundleLocator("messages"));

		ValidatingInterceptor validatingInterceptor = new ValidatingInterceptor(messageInterpolator) {

			@Override
			protected Object handleViolations(MethodInvocation invocation, Set<ConstraintViolation<Object>> violations) {
				throw new ValidationException("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
			}
		};
		// requestInjection(validatingInterceptor);

		bindInterceptor(Matchers.annotatedWith(Api.class), Matchers.any(), validatingInterceptor);

		provider = binder().getProvider(ImagesV1.class);
	}
}
