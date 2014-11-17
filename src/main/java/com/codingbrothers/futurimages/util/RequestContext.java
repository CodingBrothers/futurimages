package com.codingbrothers.futurimages.util;

import java.util.Locale;
import java.util.Objects;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import com.google.inject.servlet.RequestScoped;

@RequestScoped
public class RequestContext {

	private final HttpServletRequest request;

	private Locale requestLocale;

	@Inject
	public RequestContext(HttpServletRequest request) {
		this.request = Objects.requireNonNull(request);
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	/**
	 * Equivalent to calling {@link #getRequestLocale(Locale) getRequestLocale(Locale.ROOT)}.
	 */
	public Locale getRequestLocale() {
		return getRequestLocale(Locale.ROOT);
	}

	public Locale getRequestLocale(Locale defaultValue) {
		if (requestLocale == null) {
			if (request.getHeader("Accept-Language") != null) {
				requestLocale = request.getLocale();
			}
			if (requestLocale == null) {
				// TODO: create a Locale based on X-AppEngine-Country, X-AppEngine-Region, ... headers
			}
		}
		return requestLocale != null ? requestLocale : defaultValue;
	}

}
