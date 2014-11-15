package com.codingbrothers.futurimages.util;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.google.inject.Provider;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.util.AbstractFilter;

@Singleton
public class ObjectifySessionClearingFilter extends AbstractFilter {

	private final Provider<Objectify> objectifyProvider;

	@Inject
	public ObjectifySessionClearingFilter(Provider<Objectify> objectifyProvider) {
		this.objectifyProvider = objectifyProvider;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
			ServletException {
		try {
			chain.doFilter(request, response);
		} finally {
			objectifyProvider.get().clear();
		}
	}
}
