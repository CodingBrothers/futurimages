package com.codingbrothers.futurimages.domain;

import com.google.appengine.api.images.ImagesServiceFactory;
import com.googlecode.objectify.annotation.Subclass;
import com.googlecode.objectify.annotation.Unindex;

@Unindex
@Subclass(index = false)
public class Flip extends Transform {

	private boolean horizontal;

	Flip() {}

	public boolean isHorizontal() {
		return horizontal;
	}

	@Override
	public com.google.appengine.api.images.Transform asAppEngineTransform() {
		return horizontal ? ImagesServiceFactory.makeHorizontalFlip() : ImagesServiceFactory.makeVerticalFlip();
	}

	public static class Builder {

		private final Flip flip;

		private Builder() {
			this.flip = new Flip();
		}

		public Builder setHorizontal(boolean horizontal) {
			flip.horizontal = horizontal;
			return this;
		}

		public Flip build() {
			return flip;
		}

		public static Builder create() {
			return new Builder();
		}
	}
}
