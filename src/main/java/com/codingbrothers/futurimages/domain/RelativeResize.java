package com.codingbrothers.futurimages.domain;

import com.google.appengine.api.images.ImagesServiceFactory;
import com.googlecode.objectify.annotation.Unindex;

@Unindex
public class RelativeResize extends Transform {

	private int percent;

	private int baseWidth;

	private int baseHeight;

	RelativeResize() {}

	RelativeResize(int baseWidth, int baseHeight) {
		this.percent = 100; // means no change in size
		this.baseWidth = baseWidth;
		this.baseHeight = baseHeight;
	}

	public int getPercent() {
		return percent;
	}

	@Override
	public com.google.appengine.api.images.Transform asAppEngineTransform() {
		return ImagesServiceFactory.makeResize((int) (baseWidth * (percent / 100.0)),
				(int) (baseHeight * (percent / 100.0)), percent < 100);
	}

	public static class Builder {

		private RelativeResize relativeResize;

		private Builder(int baseWidth, int baseHeight) {
			relativeResize = new RelativeResize(baseWidth, baseHeight);
		}

		public Builder percent(int percent) {
			relativeResize.percent = percent;
			return this;
		}

		public RelativeResize build() {
			return relativeResize;
		}

		public static Builder create(int baseWidth, int baseHeight) {
			return new Builder(baseWidth, baseHeight);
		}
	}
}
