package com.codingbrothers.futurimages.config;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import java.util.Arrays;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import com.codingbrothers.futurimages.util.RequestContext;
import com.google.common.collect.Iterators;
import com.google.inject.Key;
import com.google.inject.Provider;
import com.google.inject.Scope;

public class TestRequestScope implements Scope {

	private final Provider<RequestContext> reqCtxProvider = new Provider<RequestContext>() {

		@Override
		public RequestContext get() {
			HttpServletRequest req = mock(HttpServletRequest.class);
			doReturn(Locale.getDefault()).when(req).getLocale();
			doReturn(Iterators.asEnumeration(Arrays.asList(Locale.getDefault()).iterator())).when(req).getLocales();
			return new RequestContext(req);
		}
	};

	@Override
	@SuppressWarnings("unchecked")
	public <T> Provider<T> scope(Key<T> key, Provider<T> unscoped) {
		if (RequestContext.class.isAssignableFrom(key.getTypeLiteral().getType().getClass())) {
			return (Provider<T>) reqCtxProvider;
		}
		return unscoped;
	}

}
