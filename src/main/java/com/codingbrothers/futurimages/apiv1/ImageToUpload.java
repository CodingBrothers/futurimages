package com.codingbrothers.futurimages.apiv1;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.codingbrothers.futurimages.apiv1.util.AnyOf;
import com.codingbrothers.futurimages.apiv1.util.ImageContentMustMatchContentType;
import com.codingbrothers.futurimages.apiv1.util.LengthEL;
import com.google.api.server.spi.config.AnnotationBoolean;
import com.google.api.server.spi.config.ApiResourceProperty;
import com.google.appengine.api.images.Image;

@ImageContentMustMatchContentType
public class ImageToUpload implements Response {

	@NotBlank
	private String name;
	private String description;
	private boolean isPublic = true;
	@NotNull
	@LengthEL(max = "(validatedValue.length / 1.37) <= 10000000", message = "ImageToUpload.content.maxSizeMessage")
	// the size of Base64 decoded data is approx. 1.37 times as less
	private String content;
	@NotNull
	@AnyOf(value = { "image/jpeg", "image/png", "image/webp", "image/gif", "image/bmp", "image/x-bmp", "image/tiff",
			"image/x-icon" }, message = "ImageToUpload.contentType.anyOfMessage")
	private String contentType;

	// isn't a part of the public api
	// is here to let the api layer to create these Image instances, too
	// and so the service layer isn't forced to tinker with Base64 image data manipulation
	private Image image;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isPublic() {
		return isPublic;
	}

	public void setPublic(boolean isPublic) {
		this.isPublic = isPublic;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@ApiResourceProperty(name = "content_type")
	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	@ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

}
