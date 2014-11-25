package com.codingbrothers.futurimages.apiv1.util;

import java.util.Collections;

import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;

import com.codingbrothers.futurimages.apiv1.Image;
import com.codingbrothers.futurimages.apiv1.ViewType;
import com.codingbrothers.futurimages.util.Utils;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.ServingUrlOptions;
import com.google.appengine.api.users.User;
import com.google.common.base.Function;
import com.googlecode.objectify.Key;

public class APIV1Utils {

	public static DateTime parseISODateTime(String isoDateTime) {
		return DateTime.parse(isoDateTime, ISODateTimeFormat.dateTimeParser());
	}

	public static String printISODateTime(long instant) {
		return ISODateTimeFormat.dateTime().print(instant);
	}

	public static Function<com.codingbrothers.futurimages.domain.Image, Image> convertToAPIImageFunction(
			final User user, final ViewType viewType) {
		return new Function<com.codingbrothers.futurimages.domain.Image, Image>() {

			@Override
			public Image apply(com.codingbrothers.futurimages.domain.Image domainImage) {
				return convertToAPIImage(domainImage, user, viewType);
			}
		};
	}

	public static Image convertToAPIImage(com.codingbrothers.futurimages.domain.Image domainImage, User user,
			ViewType viewType) {
		com.codingbrothers.futurimages.apiv1.Image apiImage = new com.codingbrothers.futurimages.apiv1.Image();
		apiImage.setId(Key.create(domainImage).toWebSafeString());
		apiImage.setCreatedAt(APIV1Utils.printISODateTime(domainImage.getCreatedAt().getTime()));
		apiImage.setName(domainImage.getName());
		apiImage.setDescription(domainImage.getDescription());
		apiImage.setPublic(domainImage.isPublic());
		com.codingbrothers.futurimages.apiv1.User apiV1User = new com.codingbrothers.futurimages.apiv1.User();
		apiV1User.setLogin(user.getEmail());
		apiImage.setOwner(apiV1User);
		apiImage.setTransformationsURLs(Collections.<String> emptyList());
		APIV1Utils.setImageData(apiImage, new BlobKey(domainImage.getBlobKey()), null);
		return apiImage;
	}

	public static void setImageData(Image image, BlobKey blobKey, ViewType viewType) {
		com.google.appengine.api.images.Image appEngineImage = ImagesServiceFactory.makeImageFromBlob(blobKey);
		image.setWidth(appEngineImage.getWidth());
		image.setHeight(appEngineImage.getHeight());
		image.setContentURL(ImagesServiceFactory.getImagesService().getServingUrl(
				createServingUrlOptions(blobKey, viewType)));
		image.setContentType(Utils.getImageMediaType(appEngineImage.getFormat()));
		// image.setSize(appEngineImage.getImageData().length); // TODO investigate - appEngineImage.getImageData() will
		// most likely return
		// null
	}

	public static ServingUrlOptions createServingUrlOptions(BlobKey blobKey, ViewType viewType) {
		ServingUrlOptions options = ServingUrlOptions.Builder.withBlobKey(blobKey);
		if (viewType != null) {
			options.imageSize(viewType.getImageSize());
			options.crop(viewType.getCrop());
		}
		return options;
	}
}
