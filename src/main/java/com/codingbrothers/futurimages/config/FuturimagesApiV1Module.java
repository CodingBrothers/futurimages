package com.codingbrothers.futurimages.config;

import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import javax.validation.executable.ExecutableValidator;

import org.hibernate.validator.HibernateValidator;
import org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator;
import org.hibernate.validator.resourceloading.PlatformResourceBundleLocator;

import com.google.inject.AbstractModule;

public class FuturimagesApiV1Module extends AbstractModule {

	@Override
	protected void configure() {
		bind(ExecutableValidator.class).toInstance(getValidationFactory().getValidator().forExecutables());
	}

	// is here primarily because not whole codebase can be managed by Guice (namely woven aspects)
	// unfortunately, the EndpointsValidationAspect needs an access to a ValidatorFactory impl
	public static ValidatorFactory getValidationFactory() {
		return ValidatorFactoryHolder.INSTANCE;
	}

	private static class ValidatorFactoryHolder {

		// TODO: sort out calling hibValidationFactory.close() when app is being teared down
		// - is it possible within AppEngine, at all? found out that, e.g., the contextDestroyed() is never called...
		private static final ValidatorFactory INSTANCE = Validation
				.byProvider(HibernateValidator.class)
				.configure()
				.messageInterpolator(
						new ResourceBundleMessageInterpolator(new PlatformResourceBundleLocator("messages")))
				.buildValidatorFactory();
	}

}
