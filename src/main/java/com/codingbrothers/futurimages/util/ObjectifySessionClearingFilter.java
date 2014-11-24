package com.codingbrothers.futurimages.util;

import java.io.IOException;

import javax.inject.Singleton;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.util.AbstractFilter;

@Singleton
public class ObjectifySessionClearingFilter extends AbstractFilter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
			ServletException {
		try {
			chain.doFilter(request, response);
		} finally {
			ObjectifyService.ofy().clear();
		}
	}
}
