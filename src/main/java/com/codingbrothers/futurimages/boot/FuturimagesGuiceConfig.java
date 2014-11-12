package com.codingbrothers.futurimages.boot;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;

public class FuturimagesGuiceConfig extends GuiceServletContextListener {

	@Override
	protected Injector getInjector() {
		return Guice.createInjector(new FuturimagesModule(), new FuturimagesServletModule());
	}
}
