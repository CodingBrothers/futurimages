package com.codingbrothers.futurimages.apiv1;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.google.api.server.spi.config.ApiTransformer;

// awful ApiTransformers; desperately looking out for the day they allow to use Jackson while coding Endpoints
@ApiTransformer(ViewTypeStringTransformer.class)
public class ViewType {

	@NotNull
	@Size(max = 1600)
	private Integer imageSize;
	private boolean crop;

	public Integer getImageSize() {
		return imageSize;
	}

	public ViewType setImageSize(Integer imageSize) {
		this.imageSize = imageSize;
		return this;
	}

	public boolean getCrop() {
		return crop;
	}

	public ViewType setCrop(boolean crop) {
		this.crop = crop;
		return this;
	}

}
