package com.codingbrothers.futurimages.config;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import com.codingbrothers.futurimages.domain.Image;
import com.codingbrothers.futurimages.service.Futurimages;
import com.codingbrothers.futurimages.service.impl.FuturimagesImpl;
import com.codingbrothers.futurimages.util.RequestContext;
import com.google.appengine.api.users.User;
import com.google.inject.AbstractModule;
import com.google.inject.Provider;
import com.google.inject.Provides;
import com.google.inject.servlet.RequestScoped;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;

public class FuturimagesCommonModule extends AbstractModule {

	public static final String LOGGED_IN_USER_REQ_ATTR_NAME = "loggedInUser";

	@Override
	protected void configure() {
		bind(FuturimagesObjectifyFactory.class).asEagerSingleton();

		bind(Futurimages.class).to(FuturimagesImpl.class).asEagerSingleton();

		bind(Image.class);

		bind(RequestContext.class);

		bind(User.class).toProvider(new Provider<User>() {

			@Inject
			private HttpServletRequest request;

			@Override
			public User get() {
				return (User) request.getAttribute(LOGGED_IN_USER_REQ_ATTR_NAME);
			}
		}).in(RequestScoped.class);
	}

	@Provides
	Objectify provideObjectify() {
		return ObjectifyService.ofy();
	}
}
