package com.codingbrothers.futurimages.service;

import java.util.Date;
import java.util.List;

import com.codingbrothers.futurimages.domain.Image;
import com.codingbrothers.futurimages.domain.ImageTransformation;
import com.codingbrothers.futurimages.domain.Transform;
import com.codingbrothers.futurimages.domain.User;
import com.googlecode.objectify.Key;

public interface Futurimages {

	Image createImage(Image image, com.google.appengine.api.images.Image imageData);

	ImageTransformation createImageTransformation(Key<Image> imageKey, Transform transform);

	Image getImage(Key<Image> imageKey);

	ImageTransformation getImageTransformation(Key<ImageTransformation> imageTransformationKey);

	void removeImage(Key<Image> imageKey);

	void removeImageTransformation(Key<ImageTransformation> imageTransformationKey);

	List<Image> getPublicImages(Date createdAfter, Date createdBefore, boolean asc, int offset, int limit);

	List<Image> getUserImages(Key<User> userKey, Date createdAfter, Date createdBefore, boolean asc, int offset,
			int limit);

	List<ImageTransformation> getImageTransformations(Key<ImageTransformation> key, Date createdAfter,
			Date createdBefore, boolean asc, int offset, int limit);
}
