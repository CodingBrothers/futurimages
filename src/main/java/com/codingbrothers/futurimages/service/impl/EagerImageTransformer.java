package com.codingbrothers.futurimages.service.impl;

import com.codingbrothers.futurimages.domain.ImageTransformation;
import com.google.appengine.api.taskqueue.DeferredTask;
import com.googlecode.objectify.Key;

public class EagerImageTransformer implements ImageTransformer {

	@Override
	public void create(ImageTransformation imageTransformation) {
		createTransformationTask(imageTransformation).run();
	}

	protected DeferredTask createTransformationTask(ImageTransformation imageTransformation) {
		return new ImageTransformingTask(Key.create(imageTransformation), imageTransformation.getTransform()
				.asAppEngineTransform());
	}
}
