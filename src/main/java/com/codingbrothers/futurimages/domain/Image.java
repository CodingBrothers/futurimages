package com.codingbrothers.futurimages.domain;

import javax.inject.Inject;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Unindex;

@Entity(name = "I")
@Unindex
// @Cache(expirationSeconds = 60)
public class Image extends BaseImage<User> {

	@Inject
	private Image() {}

	// hashCode, equals, toString

	public static Builder Builder() {
		return Builder.create();
	}

	public static class Builder {

		private final Image image;

		private Builder() {
			image = new Image();
		}

		public Builder fromImage(Image image) {
			this.image.parent = image.parent;
			this.image.id = image.id;
			this.image.p = image.p;
			this.image.c = image.c;
			this.image.blobKey = image.blobKey;
			return this;
		}

		public Builder setBlobKey(String blobKey) {
			image.blobKey = blobKey;
			return this;
		}

		public Image build() {
			return image;
		}

		public static Builder create() {
			return new Builder();
		}
	}
}
