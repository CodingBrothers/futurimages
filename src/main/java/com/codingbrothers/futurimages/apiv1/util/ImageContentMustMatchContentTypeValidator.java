package com.codingbrothers.futurimages.apiv1.util;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.codingbrothers.futurimages.apiv1.ImageToUpload;
import com.google.appengine.api.images.Image.Format;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.common.io.BaseEncoding;

public class ImageContentMustMatchContentTypeValidator implements
		ConstraintValidator<ImageContentMustMatchContentType, ImageToUpload> {

	@Override
	public void initialize(ImageContentMustMatchContentType constraintAnnotation) {
	}

	@Override
	public boolean isValid(ImageToUpload image, ConstraintValidatorContext context) {
		if (image == null) {
			return true;
		}

		boolean isValid = false;

		try {
			// making the image already here doesn't mind as the service layer would have to do so anyway
			image.setImage(ImagesServiceFactory.makeImage(BaseEncoding.base64().decode(image.getContent())));

			// note: contentType is validated elsewhere (via AnyOf validator)
			if ((image.getContentType().equals("image/jpeg") && image.getImage().getFormat() == Format.JPEG)
					|| (image.getContentType().equals("image/png") && image.getImage().getFormat() == Format.PNG)
					|| (image.getContentType().equals("image/webp") && image.getImage().getFormat() == Format.WEBP)
					|| (image.getContentType().equals("image/gif") && image.getImage().getFormat() == Format.GIF)
					|| (image.getContentType().equals("image/bmp") && image.getImage().getFormat() == Format.BMP)
					|| (image.getContentType().equals("image/tiff") && image.getImage().getFormat() == Format.TIFF)
					|| (image.getContentType().equals("image/x-icon") && image.getImage().getFormat() == Format.ICO)) {
				isValid = true;
			}
		} catch (Exception e) {
		}

		// bind the error to the 'content' property rather than have it as an ImageToUpload-wide error
		if (!isValid) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
					.addPropertyNode("content").addConstraintViolation();
		}

		return isValid;
	}

}
