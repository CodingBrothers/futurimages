package com.codingbrothers.futurimages.service.impl;

import javax.inject.Inject;
import javax.inject.Named;

import com.codingbrothers.futurimages.domain.ImageTransformation;
import com.google.appengine.api.taskqueue.DeferredTask;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;

public class DeferredImageTransformer implements ImageTransformer {

	private final Queue queue;

	@Inject
	DeferredImageTransformer(@Named("ImageTransformer") Queue queue) {
		this.queue = queue;
	}

	@Override
	public void create(ImageTransformation imageTransformation) {
		queue.add(ObjectifyService.ofy().getTransaction(),
				TaskOptions.Builder.withPayload(createTransformationTask(imageTransformation)));
	}

	protected DeferredTask createTransformationTask(ImageTransformation imageTransformation) {
		return new ImageTransformingTask(Key.create(imageTransformation), imageTransformation.getTransform()
				.asAppEngineTransform());
	}
}
