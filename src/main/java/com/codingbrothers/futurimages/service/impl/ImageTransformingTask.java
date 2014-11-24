package com.codingbrothers.futurimages.service.impl;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codingbrothers.futurimages.domain.Image;
import com.codingbrothers.futurimages.domain.ImageTransformation;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.Transform;
import com.google.appengine.api.taskqueue.DeferredTask;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.VoidWork;

public class ImageTransformingTask implements DeferredTask {

	private static final Logger logger = LoggerFactory.getLogger(ImageTransformingTask.class);

	private static final long serialVersionUID = 1L;

	private Key<ImageTransformation> key;

	private Transform transform;

	public ImageTransformingTask(Key<ImageTransformation> key, Transform transform) {
		this.key = key;
		this.transform = transform;
	}

	@Override
	public void run() {
		Key<Image> parent = key.<Image>getParent();
		if (parent == null) {
			// log it
			return;
		}

		Image image = ObjectifyService.ofy().load().key(parent).now();
		if (image == null) {
			// log it
			return;
		}

		String blobKey = image.getBlobKey();
		if (blobKey == null) {
			// log it
			return;
		}

		ImagesService imagesService = ImagesServiceFactory.getImagesService();

		final String transformedImageBlobKey =
				imagesService.applyTransform(transform, ImagesServiceFactory.makeImageFromBlob(new BlobKey(blobKey)))
						.getBlobKey().getKeyString();

		ObjectifyService.ofy().transact(new VoidWork() {

			@Override
			public void vrun() {
				ImageTransformation imageTransformation = ObjectifyService.ofy().load().key(key).now();
				if (imageTransformation != null) {
					if (imageTransformation.getBlobKey() != null) {
						logger.debug("The image transformation {} already has image data.", key.getRaw());
					} else {
						ObjectifyService
								.ofy()
								.save()
								.entity(ImageTransformation.Builder().proto(imageTransformation)
										.setBlobKey(transformedImageBlobKey).build());
					}
				} else {
					logger.debug("The image transformation {} does not exist.", key.getRaw());
				}
			}
		});
	}

	private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException {
		ois.defaultReadObject();
		// validateState();
	}

	private void writeObject(ObjectOutputStream oos) throws IOException {
		oos.defaultWriteObject();
	}
}
