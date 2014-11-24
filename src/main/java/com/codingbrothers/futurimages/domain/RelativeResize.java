package com.codingbrothers.futurimages.domain;

import com.google.appengine.api.images.ImagesServiceFactory;

public class RelativeResize extends Transform {

	private int percent;

	private int baseWidth;
	private int baseHeight;

	public int getPercent() {
		return percent;
	}

	@Override
	public com.google.appengine.api.images.Transform asGoogleTransform() {
		return ImagesServiceFactory.makeResize((int) (baseWidth * (percent / 100.0)),
				(int) (baseHeight * (percent / 100.0)), percent < 100);
	}

	public static class Builder {

		private int percent = 100; // means no change in size
		private int baseWidth;
		private int baseHeight;

		public Builder(int baseWidth, int baseHeight) {
			this.baseWidth = baseWidth;
			this.baseHeight = baseHeight;
		}

		public Builder percent(int percent) {
			this.percent = percent;
			return this;
		}

		public RelativeResize build() {
			RelativeResize result = new RelativeResize();
			result.percent = percent;
			result.baseWidth = baseWidth;
			result.baseHeight = baseHeight;
			return result;
		}
	}

}
