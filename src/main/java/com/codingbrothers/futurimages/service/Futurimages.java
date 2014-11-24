package com.codingbrothers.futurimages.service;

import java.util.Date;

import com.codingbrothers.futurimages.domain.Image;

public interface Futurimages {

	Image createImage(Image image, com.google.appengine.api.images.Image imageData);

	Iterable<Image> getPublicImages(Date createdAfter, Date createdBefore, boolean asc);

	Iterable<Image> getUserImages(String userId, Date createdAfter, Date createdBefore, boolean asc);
}
