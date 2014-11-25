package com.codingbrothers.futurimages.domain;

import javax.inject.Inject;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Unindex;

@Entity(name = "T")
@Unindex
// @Cache(expirationSeconds = 60)
public class ImageTransformation extends BaseImage<Image> {

	private Transform transform;

	@Inject
	private ImageTransformation() {}

	public Transform getTransform() {
		return transform;
	}

	void setTransform(Transform transform) {
		this.transform = transform;
	}
	
	public boolean isPublic() {
		return true; // at the moment, ImageTransformation is always public
	}

	public static Builder Builder() {
		return Builder.create();
	}

	public static class Builder {

		private final ImageTransformation imageTransformation;

		private Builder() {
			imageTransformation = new ImageTransformation();
		}

		public Builder of(Key<Image> imageKey) {
			imageTransformation.parent = Ref.create(imageKey);
			return this;
		}

		public Builder with(Transform transform) {
			imageTransformation.transform = transform;
			return this;
		}

		public Builder setBlobKey(String blobKey) {
			this.imageTransformation.blobKey = blobKey;
			return this;
		}

		public Builder proto(ImageTransformation imageTransformation) {
			this.imageTransformation.parent = imageTransformation.parent;

			this.imageTransformation.id = imageTransformation.id;
			this.imageTransformation.p = imageTransformation.p;
			this.imageTransformation.c = imageTransformation.c;
			this.imageTransformation.blobKey = imageTransformation.blobKey;

			this.imageTransformation.transform = imageTransformation.transform;
			return this;
		}

		public ImageTransformation build() {
			return imageTransformation;
		}

		public static Builder create() {
			return new Builder();
		}
	}
}
