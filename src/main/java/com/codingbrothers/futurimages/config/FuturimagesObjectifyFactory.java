package com.codingbrothers.futurimages.config;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.codingbrothers.futurimages.domain.CompositeTransform;
import com.codingbrothers.futurimages.domain.Flip;
import com.codingbrothers.futurimages.domain.Image;
import com.codingbrothers.futurimages.domain.ImageTransformation;
import com.codingbrothers.futurimages.domain.RelativeResize;
import com.codingbrothers.futurimages.domain.Rotate;
import com.codingbrothers.futurimages.domain.User;
import com.google.inject.Injector;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;

@Singleton
public class FuturimagesObjectifyFactory extends ObjectifyFactory {

	private final Injector injector;

	@Inject
	public FuturimagesObjectifyFactory(Injector injector) {
		this.injector = injector;

		this.register(User.class);
		this.register(Image.class);

		this.register(ImageTransformation.class);

		this.register(CompositeTransform.class);
		this.register(Flip.class);
		this.register(RelativeResize.class);
		this.register(Rotate.class);

		ObjectifyService.setFactory(this);
	}

	@Override
	public <T> T construct(Class<T> type) {
		return injector.getInstance(type);
	}
}
