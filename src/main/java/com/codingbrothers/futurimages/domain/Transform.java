package com.codingbrothers.futurimages.domain;

import com.googlecode.objectify.annotation.Unindex;

@Unindex
public abstract class Transform {

	Transform() {}

	public abstract com.google.appengine.api.images.Transform asAppEngineTransform();
}
