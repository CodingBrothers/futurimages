package com.codingbrothers.futurimages.config;

import java.util.Collections;

import javax.inject.Singleton;

import com.codingbrothers.futurimages.FuturimagesAPI;
import com.codingbrothers.futurimages.util.ObjectifySessionClearingFilter;
import com.google.api.server.spi.guice.GuiceSystemServiceServletModule;
import com.googlecode.objectify.ObjectifyFilter;

public class FuturimagesServletModule extends GuiceSystemServiceServletModule {

	@Override
	protected void configureServlets() {
		bind(ObjectifyFilter.class).in(Singleton.class);
		filter("/*").through(ObjectifyFilter.class);
		filter("/*").through(ObjectifySessionClearingFilter.class);

		serveGuiceSystemServiceServlet("/*", Collections.singletonList(FuturimagesAPI.class));
	}
}
