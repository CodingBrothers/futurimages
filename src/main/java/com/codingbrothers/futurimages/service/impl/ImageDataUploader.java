package com.codingbrothers.futurimages.service.impl;

import com.codingbrothers.futurimages.domain.Image;

public interface ImageDataUploader {

	void uploadData(Image image, com.google.appengine.api.images.Image imageData);
}
