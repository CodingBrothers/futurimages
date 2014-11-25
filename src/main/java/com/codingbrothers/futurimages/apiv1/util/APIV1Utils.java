package com.codingbrothers.futurimages.apiv1.util;

import java.util.Collections;

import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;

import com.codingbrothers.futurimages.apiv1.Image;
import com.codingbrothers.futurimages.apiv1.ViewType;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.ServingUrlOptions;
import com.google.common.base.Function;
import com.googlecode.objectify.Key;

public class APIV1Utils {

	public static DateTime parseISODateTime(String isoDateTime) {
		return DateTime.parse(isoDateTime, ISODateTimeFormat.dateTimeParser());
	}

	public static String printISODateTime(long instant) {
		return ISODateTimeFormat.dateTime().print(instant);
	}

	public static Function<com.codingbrothers.futurimages.domain.Image, Image> convertToAPIImageFunction(final ViewType viewType, final boolean setImageData) {
		return new Function<com.codingbrothers.futurimages.domain.Image, Image>() {

			@Override
			public Image apply(com.codingbrothers.futurimages.domain.Image domainImage) {
				return convertToAPIImage(domainImage, viewType, setImageData);
			}
		};
	}

	public static Image convertToAPIImage(com.codingbrothers.futurimages.domain.Image domainImage, ViewType viewType, boolean setImageData) {
		com.codingbrothers.futurimages.apiv1.Image apiImage = new com.codingbrothers.futurimages.apiv1.Image();
		apiImage.setId(Key.create(domainImage).toWebSafeString());
		apiImage.setCreatedAt(APIV1Utils.printISODateTime(domainImage.getCreatedAt().getTime()));
		apiImage.setName(domainImage.getName());
		apiImage.setDescription(domainImage.getDescription());
		apiImage.setPublic(domainImage.isPublic());
		com.codingbrothers.futurimages.apiv1.User apiV1User = new com.codingbrothers.futurimages.apiv1.User();
//		apiV1User.setLogin(domainImage.getOwner().getEmail()); TODO
		apiImage.setOwner(apiV1User);
		apiImage.setTransformationsURLs(Collections.<String>emptyList());
		
		if(domainImage.getBlobKey() != null) {
			BlobKey blobKey = new BlobKey(domainImage.getBlobKey());
			apiImage.setContentURL(ImagesServiceFactory.getImagesService().getServingUrl(createServingUrlOptions(blobKey, viewType)));
			if(setImageData) {
				APIV1Utils.setImageData(apiImage, blobKey, null);
			}
		}
		
		return apiImage;
	}

	public static void setImageData(Image image, BlobKey blobKey, ViewType viewType) {
		// TODO - refactor: once an image is created with BlobKey; it's data isn't available; must be explicitly fetched
//		com.google.appengine.api.images.Image appEngineImage = ImagesServiceFactory.makeImageFromBlob(blobKey);
		//		image.setWidth(appEngineImage.getWidth());
//		image.setHeight(appEngineImage.getHeight());
//		image.setContentType(Utils.getImageMediaType(appEngineImage.getFormat()));
//		image.setSize(appEngineImage.getImageData().length);
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
