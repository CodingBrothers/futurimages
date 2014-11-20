package com.codingbrothers.futurimages.apiv1;

import javax.validation.constraints.NotNull;

import com.codingbrothers.futurimages.apiv1.util.AnyOf;
import com.google.api.server.spi.config.ApiResourceProperty;

public class ImageToUpload implements Response {

	@NotNull
	private String name;
	private String description;
	private Boolean isPublic;
	@NotNull
	private String content;
	@NotNull
	@AnyOf(value = { "image/jpeg", "image/png", "image/webp", "image/gif", "image/bmp", "image/x-bmp", "image/tiff",
			"image/x-icon" }, message = "ImageToUpload.contentType.oneOfMessage")
	private String contentType;

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

	public Boolean isPublic() {
		return isPublic != null ? isPublic : Boolean.TRUE;
	}

	public void setPublic(Boolean isPublic) {
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

}
