package com.codingbrothers.futurimages.service.impl;

import com.codingbrothers.futurimages.domain.Image;
import com.codingbrothers.futurimages.util.Utils;
import com.google.appengine.api.taskqueue.DeferredTask;
import com.googlecode.objectify.Key;

public class EagerImageDataUploader implements ImageDataUploader {

	@Override
	public void uploadData(Image image, com.google.appengine.api.images.Image imageData) {
		createUploadTask(image, imageData).run();
	}

	protected DeferredTask createUploadTask(Image image, com.google.appengine.api.images.Image imageData) {
		return new ImageDataUploadingTask(Key.create(image), Utils.getImageMediaType(imageData.getFormat()),
				imageData.getImageData());
	}
}
