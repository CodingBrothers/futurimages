package com.codingbrothers.futurimages.service.impl;

import java.util.Date;

import javax.inject.Inject;

import com.codingbrothers.futurimages.domain.Image;
import com.codingbrothers.futurimages.domain.User;
import com.codingbrothers.futurimages.service.Futurimages;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.VoidWork;
import com.googlecode.objectify.Work;
import com.googlecode.objectify.cmd.Query;

public class FuturimagesImpl implements Futurimages {

	private final ImageDataUploader imageDataUploader;

	@Inject
	FuturimagesImpl(ImageDataUploader imageDataUploader) {
		this.imageDataUploader = imageDataUploader;
	}
	
	// from, to

	@Override
	public Image createImage(final Image image, final com.google.appengine.api.images.Image imageData) {
		ObjectifyService.ofy().transact(new VoidWork() {

			@Override
			public void vrun() {
				// validate image for creation
				// aspect for exceptions

				ObjectifyService.ofy().save().entity(image);
				imageDataUploader.uploadData(image, imageData);
			}
		});
		return image;
	}

	@Override
	public Iterable<Image> getPublicImages(Date createdAfter, Date createdBefore, boolean asc) {
		// validation after < before, ...
		Query<Image> query = ObjectifyService.ofy().load().type(Image.class).filter("p", Boolean.TRUE);

		if (createdAfter != null) {
			query = query.filter("c >", createdAfter);
		}
		if (createdBefore != null) {
			query = query.filter("c <", createdBefore);
		}
		query = query.order(asc ? "c" : "-c");

		return query.iterable();
	}

	@Override
	public Iterable<Image> getUserImages(String userId, final Date createdAfter, final Date createdBefore,
			final boolean asc) {
		// validation after < before, ...
		final Key<User> userKey = Key.create(User.class, userId);
		return ObjectifyService.ofy().transact(new Work<Iterable<Image>>() {

			@Override
			public Iterable<Image> run() {
				Query<Image> query = ObjectifyService.ofy().load().type(Image.class).ancestor(userKey);

				if (createdAfter != null) {
					query = query.filter("c >", createdAfter);
				}
				if (createdBefore != null) {
					query = query.filter("c <", createdBefore);
				}
				query = query.order(asc ? "c" : "-c");

				return query.iterable();
			}
		});
	}
}
