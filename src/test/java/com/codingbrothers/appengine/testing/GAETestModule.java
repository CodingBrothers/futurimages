package com.codingbrothers.appengine.testing;

import com.google.inject.AbstractModule;
import com.google.inject.matcher.Matchers;

public class GAETestModule extends AbstractModule {

	@Override
	protected void configure() {
		GAETestMethodInterceptor gaeTestImpl = new GAETestMethodInterceptor();
		requestInjection(gaeTestImpl);
		bindInterceptor(Matchers.any(), Matchers.annotatedWith(GAETest.class), gaeTestImpl);
	}
}
