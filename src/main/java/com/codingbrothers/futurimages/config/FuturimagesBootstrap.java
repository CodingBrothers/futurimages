package com.codingbrothers.futurimages.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.google.inject.Guice;

public class FuturimagesBootstrap implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		final ServletContext servletContext = servletContextEvent.getServletContext();

		Guice.createInjector(new FuturimagesGuiceModule());

//		servletContext.setAttribute(INJECTOR_NAME, injector);
	}

	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		
	}
}
