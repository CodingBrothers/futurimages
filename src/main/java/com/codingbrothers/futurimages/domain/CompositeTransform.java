package com.codingbrothers.futurimages.domain;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

public class CompositeTransform extends Transform {

	private final List<Transform> transforms;

	public CompositeTransform() {
		this.transforms = new LinkedList<Transform>();
	}

	public CompositeTransform(Collection<Transform> transformsToAdd) {
		this();
		this.transforms.addAll(transformsToAdd);
	}

	public CompositeTransform concatenate(Transform other) {
		this.transforms.add(other);
		return this;
	}

	public CompositeTransform preConcatenate(Transform other) {
		this.transforms.add(0, other);
		return this;
	}

	@Override
	public com.google.appengine.api.images.Transform asGoogleTransform() {
		return ImagesServiceFactory.makeCompositeTransform(Lists.newLinkedList(Iterables.transform(transforms,
				new Function<Transform, com.google.appengine.api.images.Transform>() {

					@Override
					public com.google.appengine.api.images.Transform apply(Transform t) {
						return t.asGoogleTransform();
					}
				})));
	}

}
