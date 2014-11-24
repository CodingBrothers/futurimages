package com.codingbrothers.futurimages.service.impl;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codingbrothers.futurimages.domain.Image;
import com.google.appengine.api.files.AppEngineFile;
import com.google.appengine.api.files.FileService;
import com.google.appengine.api.files.FileServiceFactory;
import com.google.appengine.api.files.FileWriteChannel;
import com.google.appengine.api.taskqueue.DeferredTask;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.VoidWork;

public class ImageDataUploadingTask implements DeferredTask {

	private static final Logger logger = LoggerFactory.getLogger(ImageDataUploadingTask.class);

	private static final long serialVersionUID = 1L;

	private Key<Image> imageKey;

	private String mimeType;

	private byte[] imageData;

	public ImageDataUploadingTask(Key<Image> imageKey, String mimeType, byte[] imageData) {
		this.imageKey = imageKey;
		this.mimeType = mimeType;
		this.imageData = imageData;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void run() {
		FileService fileService = null;
		FileWriteChannel writeChannel = null;
		AppEngineFile file = null;
		try {
			fileService = FileServiceFactory.getFileService();
			file = fileService.createNewBlobFile(mimeType);

			writeChannel = fileService.openWriteChannel(file, false);
			writeChannel.write(ByteBuffer.wrap(imageData));
			writeChannel.closeFinally();

			final String blobKey = fileService.getBlobKey(file).getKeyString();

			ObjectifyService.ofy().transact(new VoidWork() {

				@Override
				public void vrun() {
					Image image = ObjectifyService.ofy().load().key(imageKey).now();
					if (image != null) {
						if (image.getBlobKey() != null) {
							logger.debug("The image {} already has data.", imageKey.getRaw());
						} else {
							ObjectifyService.ofy().save()
									.entity(Image.Builder().fromImage(image).setBlobKey(blobKey).build());
						}
					} else {
						logger.debug("The image {} does not exist.", imageKey.getRaw());
					}
				}
			});
		} catch (Exception e) {
			logger.warn("Uploading image data for the image {} failed: {}", imageKey.getRaw(), e);
			if (writeChannel != null) {
				try {
					writeChannel.closeFinally();
				} catch (Exception ex) {
					logger.warn("Unable to close the write channel: {}", ex);
				}
			}
			if (file != null && file.hasFinalizedName()) {
				try {
					fileService.delete(file);
				} catch (Exception ex) {
					logger.warn("Unable to delete the no more needed file: {}", ex);
				}
			}
			throw (e instanceof RuntimeException ? (RuntimeException) e : new RuntimeException(e));
		}
	}

	private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException {
		ois.defaultReadObject();
		// validateState();
	}

	private void writeObject(ObjectOutputStream oos) throws IOException {
		oos.defaultWriteObject();
	}
}
