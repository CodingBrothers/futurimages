package com.codingbrothers.futurimages.config;

import javax.inject.Singleton;

import com.codingbrothers.futurimages.apiv1.ImageTransformationV1;
import com.codingbrothers.futurimages.apiv1.ImageTransformationsV1;
import com.codingbrothers.futurimages.apiv1.ImageV1;
import com.codingbrothers.futurimages.apiv1.ImagesV1;
import com.codingbrothers.futurimages.apiv1.AuthenticatedUserV1;
import com.codingbrothers.futurimages.util.ObjectifySessionClearingFilter;
import com.google.api.server.spi.guice.GuiceSystemServiceServletModule;
import com.google.common.collect.Lists;
import com.googlecode.objectify.ObjectifyFilter;

public class FuturimagesServletModule extends GuiceSystemServiceServletModule {

	@Override
	@SuppressWarnings("unchecked")
	protected void configureServlets() {
		bind(ObjectifyFilter.class).in(Singleton.class);
		filter("/*").through(ObjectifyFilter.class);
		filter("/*").through(ObjectifySessionClearingFilter.class);

		serveGuiceSystemServiceServlet("/_ah/spi/*", Lists.newArrayList(ImagesV1.class, ImageV1.class,
				ImageTransformationsV1.class, ImageTransformationV1.class, AuthenticatedUserV1.class));
	}
}
