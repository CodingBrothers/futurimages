package com.codingbrothers.futurimages.service.impl;

import javax.inject.Inject;
import javax.inject.Named;

import com.codingbrothers.futurimages.domain.Image;
import com.codingbrothers.futurimages.util.Utils;
import com.google.appengine.api.taskqueue.DeferredTask;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;

public class DeferredImageDataUploader implements ImageDataUploader {

	private final Queue queue;

	@Inject
	DeferredImageDataUploader(@Named("ImageDataUploader") Queue queue) {
		this.queue = queue;
	}

	@Override
	public void uploadData(Image image, com.google.appengine.api.images.Image imageData) {
		queue.add(ObjectifyService.ofy().getTransaction(),
				TaskOptions.Builder.withPayload(createUploadTask(image, imageData)));
	}

	protected DeferredTask createUploadTask(Image image, com.google.appengine.api.images.Image imageData) {
		return new ImageDataUploadingTask(Key.create(image), Utils.getImageMediaType(imageData.getFormat()),
				imageData.getImageData());
	}
}
