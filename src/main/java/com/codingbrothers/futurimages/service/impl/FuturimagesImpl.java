package com.codingbrothers.futurimages.service.impl;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import com.codingbrothers.futurimages.domain.Image;
import com.codingbrothers.futurimages.domain.ImageTransformation;
import com.codingbrothers.futurimages.domain.Transform;
import com.codingbrothers.futurimages.domain.User;
import com.codingbrothers.futurimages.service.Futurimages;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.VoidWork;
import com.googlecode.objectify.Work;
import com.googlecode.objectify.cmd.Query;

public class FuturimagesImpl implements Futurimages {

	private final ImageDataUploader imageDataUploader;

	private final ImageTransformer imageTransformer;

	@Inject
	FuturimagesImpl(ImageDataUploader imageDataUploader, ImageTransformer imageTransformer) {
		this.imageDataUploader = imageDataUploader;
		this.imageTransformer = imageTransformer;
	}

	/*
	 * !!!!!!!!!!!!!!
	 * 
	 * Transactions are mostly left non-idempotent. Despite it is not very likely to receive an exception for an already
	 * committed tx, in rare cases it may happen. if it does happen, identical entities may be created for example.
	 */

	// validate contextually
	// aspect for exceptions

	@Override
	public Image createImage(final Image image, final com.google.appengine.api.images.Image imageData) {
		ObjectifyService.ofy().transact(new VoidWork() {

			@Override
			public void vrun() {
				ObjectifyService.ofy().save().entity(image);

				imageDataUploader.uploadData(image, imageData);
			}
		});
		return image;
	}

	@Override
	public ImageTransformation createImageTransformation(final Key<Image> imageKey, final Transform transform) {
		return ObjectifyService.ofy().transact(new Work<ImageTransformation>() {

			@Override
			public ImageTransformation run() {
				ImageTransformation imageTransformation =
						ImageTransformation.Builder().of(imageKey).with(transform).build();

				ObjectifyService.ofy().save().entity(imageTransformation).now();

				imageTransformer.create(imageTransformation);

				return imageTransformation;
			}
		});
	}

	@Override
	public Image getImage(Key<Image> imageKey) {
		return ObjectifyService.ofy().load().key(imageKey).now();
	}

	@Override
	public ImageTransformation getImageTransformation(Key<ImageTransformation> imageTransformationKey) {
		return ObjectifyService.ofy().load().key(imageTransformationKey).now();
	}

	@Override
	public void removeImage(final Key<Image> imageKey) {
		ObjectifyService.ofy().transact(new VoidWork() {

			@SuppressWarnings({"unchecked", "rawtypes"})
			@Override
			public void vrun() {
				List keys =
						ObjectifyService.ofy().load().type(ImageTransformation.class).ancestor(imageKey).keys().list();
				keys.add(imageKey);
				ObjectifyService.ofy().delete().keys(keys);
			}
		});
	}

	@Override
	public void removeImageTransformation(Key<ImageTransformation> imageTransformationKey) {
		ObjectifyService.ofy().delete().key(imageTransformationKey).now();
	}

	@Override
	public Iterable<Image> getPublicImages(Date createdAfter, Date createdBefore, boolean asc, final int offset,
			final int limit) {
		Query<Image> query =
				ObjectifyService.ofy().load().type(Image.class).filter("p", Boolean.TRUE).offset(offset).limit(limit);

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
	public Iterable<Image> getUserImages(final Key<User> userKey, final Date createdAfter, final Date createdBefore,
			final boolean asc, final int offset, final int limit) {
		return ObjectifyService.ofy().transact(new Work<Iterable<Image>>() {

			@Override
			public Iterable<Image> run() {
				Query<Image> query =
						ObjectifyService.ofy().load().type(Image.class).ancestor(userKey).offset(offset).limit(limit);

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

	@Override
	public Iterable<ImageTransformation> getImageTransformations(final Key<ImageTransformation> imgTranKey,
			final Date createdAfter, final Date createdBefore, final boolean asc, final int offset, final int limit) {
		return ObjectifyService.ofy().transact(new Work<Iterable<ImageTransformation>>() {

			@Override
			public Iterable<ImageTransformation> run() {
				Query<ImageTransformation> query =
						ObjectifyService.ofy().load().type(ImageTransformation.class).ancestor(imgTranKey)
								.offset(offset).limit(limit);

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
