package com.codingbrothers.futurimages.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.googlecode.objectify.annotation.Subclass;
import com.googlecode.objectify.annotation.Unindex;

@Unindex
@Subclass(index = false)
public class CompositeTransform extends Transform {

	private List<Transform> transforms;

	CompositeTransform() {}

	CompositeTransform(List<Transform> transforms) {
		this.transforms = transforms;
	}

	@Override
	public com.google.appengine.api.images.Transform asAppEngineTransform() {
		return ImagesServiceFactory.makeCompositeTransform(Lists.transform(transforms,
				new Function<Transform, com.google.appengine.api.images.Transform>() {

					@Override
					public com.google.appengine.api.images.Transform apply(Transform t) {
						return t.asAppEngineTransform();
					}
				}));
	}

	public static class Builder {

		private final CompositeTransform compositeTransform;

		private Builder() {
			this.compositeTransform = new CompositeTransform(new ArrayList<Transform>());
		}

		public Builder addTransforms(Transform transform) {
			compositeTransform.transforms.add(transform);
			return this;
		}

		public Builder addTransforms(Transform t1, Transform t2) {
			compositeTransform.transforms.add(t1);
			compositeTransform.transforms.add(t2);
			return this;
		}

		public Builder addTransforms(Transform... transforms) {
			compositeTransform.transforms.addAll(Arrays.asList(transforms));
			return this;
		}

		public Builder addTransforms(Iterable<Transform> transforms) {
			Iterables.addAll(compositeTransform.transforms, transforms);
			return this;
		}

		public CompositeTransform build() {
			return compositeTransform;
		}

		public static Builder create() {
			return new Builder();
		}
	}
}
