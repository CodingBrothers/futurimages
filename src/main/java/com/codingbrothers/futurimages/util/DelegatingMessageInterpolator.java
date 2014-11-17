package com.codingbrothers.futurimages.util;

import java.util.Locale;
import java.util.Objects;

import javax.validation.MessageInterpolator;

public abstract class DelegatingMessageInterpolator implements MessageInterpolator {

	private final MessageInterpolator delegate;

	public DelegatingMessageInterpolator(MessageInterpolator delegate) {
		this.delegate = Objects.requireNonNull(delegate);
	}

	@Override
	public String interpolate(String messageTemplate, Context context) {
		return delegate.interpolate(messageTemplate, context);
	}

	@Override
	public String interpolate(String messageTemplate, Context context, Locale locale) {
		return delegate.interpolate(messageTemplate, context, locale);
	}

}
