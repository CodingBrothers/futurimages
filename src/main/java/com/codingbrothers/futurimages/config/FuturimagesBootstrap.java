package com.codingbrothers.futurimages.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import com.google.inject.Guice;

public class FuturimagesBootstrap {

	public void contextInitialized(ServletContextEvent servletContextEvent) {
		final ServletContext servletContext = servletContextEvent.getServletContext();

		Guice.createInjector(new FuturimagesGuiceModule());

//		servletContext.setAttribute(INJECTOR_NAME, injector);
	}
}
