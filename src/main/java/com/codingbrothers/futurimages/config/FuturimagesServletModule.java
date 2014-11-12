package com.codingbrothers.futurimages.config;

import java.util.Collections;

import com.codingbrothers.futurimages.FuturimagesAPI;
import com.google.api.server.spi.guice.GuiceSystemServiceServletModule;
import com.google.inject.servlet.GuiceFilter;
import com.googlecode.objectify.ObjectifyFilter;

public class FuturimagesServletModule extends GuiceSystemServiceServletModule {

	@Override
	protected void configureServlets() {
		filter("/*").through(GuiceFilter.class);
		filter("/*").through(ObjectifyFilter.class);

		serveGuiceSystemServiceServlet("/*", Collections.singletonList(FuturimagesAPI.class));
	}
}
