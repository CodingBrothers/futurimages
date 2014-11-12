package com.codingbrothers.futurimages.config;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.codingbrothers.futurimages.entity.Image;
import com.google.inject.Injector;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;

@Singleton
public class OfyFactory extends ObjectifyFactory {

	private final Injector injector;

	@Inject
	public OfyFactory(Injector injector) {
		this.injector = injector;

		this.register(Image.class);

		ObjectifyService.setFactory(this);
	}

	@Override
	public <T> T construct(Class<T> type) {
		return injector.getInstance(type);
	}
}
