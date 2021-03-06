package com.codingbrothers.futurimages.config;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;

public class FuturimagesGuiceConfig extends GuiceServletContextListener {

	@Override
	protected Injector getInjector() {
		return Guice.createInjector(new FuturimagesServletModule(), new FuturimagesCommonModule(),
				new FuturimagesApiV1Module());
	}
}
