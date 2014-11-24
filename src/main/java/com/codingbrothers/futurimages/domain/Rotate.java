package com.codingbrothers.futurimages.domain;

import com.google.appengine.api.images.ImagesServiceFactory;

public class Rotate extends Transform {

	private int degrees; // always in the clockwise direction

	public int getDegrees() {
		return degrees;
	}

	@Override
	public com.google.appengine.api.images.Transform asGoogleTransform() {
		return ImagesServiceFactory.makeRotate(degrees);
	}

	public static class Builder {

		private int degrees = 0;

		public Builder() {
		}

		public Builder percent(int degrees) {
			this.degrees = degrees;
			return this;
		}

		public Rotate build() {
			Rotate result = new Rotate();
			result.degrees = degrees;
			return result;
		}
	}

}
