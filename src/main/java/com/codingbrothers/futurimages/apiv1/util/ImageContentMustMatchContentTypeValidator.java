package com.codingbrothers.futurimages.apiv1.util;

import java.util.Objects;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.codingbrothers.futurimages.apiv1.ImageToUpload;
import com.codingbrothers.futurimages.util.Utils;
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
			if (Objects.equals(Utils.getImageFormat(image.getContentType()), image.getImage().getFormat())) {
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
