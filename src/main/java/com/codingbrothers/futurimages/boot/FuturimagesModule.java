package com.codingbrothers.futurimages.boot;

import com.codingbrothers.futurimages.domain.Image;
import com.codingbrothers.futurimages.service.Futurimages;
import com.codingbrothers.futurimages.service.impl.FuturimagesImpl;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;

public class FuturimagesModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(FuturimagesObjectifyFactory.class).asEagerSingleton();

		bind(Futurimages.class).to(FuturimagesImpl.class).asEagerSingleton();

		bind(Image.class);
	}

	@Provides
	Objectify provideObjectify() {
		return ObjectifyService.ofy();
	}
}
