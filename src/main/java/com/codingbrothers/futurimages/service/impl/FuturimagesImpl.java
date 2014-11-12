package com.codingbrothers.futurimages.service.impl;

import javax.inject.Inject;

import com.codingbrothers.futurimages.service.Futurimages;
import com.google.inject.Provider;
import com.googlecode.objectify.Objectify;

public class FuturimagesImpl implements Futurimages {

	private final Provider<Objectify> objectifyProvider;

	@Inject
	FuturimagesImpl(Provider<Objectify> objectifyProvider) {
		this.objectifyProvider = objectifyProvider;
	}
}
