package com.codingbrothers.futurimages.domain;

import com.google.appengine.api.images.ImagesServiceFactory;

public class Flip extends Transform {

	private boolean horizontal;

	public boolean isHorizontal() {
		return horizontal;
	}

	@Override
	public com.google.appengine.api.images.Transform asGoogleTransform() {
		return horizontal ? ImagesServiceFactory.makeHorizontalFlip() : ImagesServiceFactory.makeVerticalFlip();
	}

	public static class Builder {

		private boolean horizontal = true;

		public Builder horizontal(boolean horizontal) {
			this.horizontal = horizontal;
			return this;
		}

		public Flip build() {
			Flip result = new Flip();
			result.horizontal = horizontal;
			return result;
		}
	}

}
