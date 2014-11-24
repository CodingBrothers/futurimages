package com.codingbrothers.futurimages.domain;

import com.google.appengine.api.images.ImagesServiceFactory;
import com.googlecode.objectify.annotation.Unindex;

@Unindex
public class Rotate extends Transform {

	private int degrees; // always in the clockwise direction

	Rotate() {}

	public int getDegrees() {
		return degrees;
	}

	@Override
	public com.google.appengine.api.images.Transform asAppEngineTransform() {
		return ImagesServiceFactory.makeRotate(degrees);
	}

	public static class Builder {

		private final Rotate rotate;

		private Builder() {
			this.rotate = new Rotate();
		}

		public Builder degrees(int degrees) {
			rotate.degrees = degrees;
			return this;
		}

		public Rotate build() {
			return rotate;
		}

		public static Builder create() {
			return new Builder();
		}
	}
}
