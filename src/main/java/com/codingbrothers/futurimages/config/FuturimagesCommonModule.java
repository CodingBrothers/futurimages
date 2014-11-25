package com.codingbrothers.futurimages.config;

import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import com.codingbrothers.futurimages.domain.CompositeTransform;
import com.codingbrothers.futurimages.domain.Flip;
import com.codingbrothers.futurimages.domain.Image;
import com.codingbrothers.futurimages.domain.ImageTransformation;
import com.codingbrothers.futurimages.domain.RelativeResize;
import com.codingbrothers.futurimages.domain.Rotate;
import com.codingbrothers.futurimages.service.Futurimages;
import com.codingbrothers.futurimages.service.impl.EagerImageDataUploader;
import com.codingbrothers.futurimages.service.impl.EagerImageTransformer;
import com.codingbrothers.futurimages.service.impl.FuturimagesImpl;
import com.codingbrothers.futurimages.service.impl.ImageDataUploader;
import com.codingbrothers.futurimages.service.impl.ImageTransformer;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.users.User;
import com.google.inject.AbstractModule;
import com.google.inject.Provider;
import com.google.inject.Provides;
import com.google.inject.servlet.RequestScoped;

public class FuturimagesCommonModule extends AbstractModule {

	public static final String LOGGED_IN_USER_REQ_ATTR_NAME = "loggedInUser";

	@Override
	protected void configure() {
		bind(FuturimagesObjectifyFactory.class).asEagerSingleton();

		bind(Futurimages.class).to(FuturimagesImpl.class).asEagerSingleton();

		bind(com.codingbrothers.futurimages.domain.User.class);
		bind(Image.class);
		
		bind(ImageTransformation.class);
		
		bind(CompositeTransform.class);
		bind(Flip.class);
		bind(RelativeResize.class);
		bind(Rotate.class);

//		bind(ImageDataUploader.class).to(DeferredImageDataUploader.class);
		bind(ImageDataUploader.class).to(EagerImageDataUploader.class);

//		bind(ImageTransformer.class).to(DeferredImageTransformer.class);
		bind(ImageTransformer.class).to(EagerImageTransformer.class);
	}

	@Provides
	@Named("ImageDataUploader")
	Queue provideImageDataUploaderQueue() {
		return QueueFactory.getDefaultQueue();
	}

	@Provides
	@Named("ImageTransformer")
	Queue provideImageTransformerQueue() {
		return QueueFactory.getDefaultQueue();
	}

	@Provides
	@RequestScoped
	User provideUser(Provider<HttpServletRequest> requestProvider) {
		return (User) requestProvider.get().getAttribute(LOGGED_IN_USER_REQ_ATTR_NAME);
	}
}
