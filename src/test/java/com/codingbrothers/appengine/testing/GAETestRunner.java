package com.codingbrothers.appengine.testing;

import java.lang.reflect.InvocationTargetException;

import org.jukito.JukitoRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;

import com.google.inject.Injector;

public class GAETestRunner extends JukitoRunner {

	public GAETestRunner(Class<?> klass) throws InitializationError, InvocationTargetException, InstantiationException,
			IllegalAccessException {
		super(klass);
	}

	public GAETestRunner(Class<?> klass, Injector injector) throws InitializationError, InvocationTargetException,
			InstantiationException, IllegalAccessException {
		super(klass, injector);
	}

	@Override
	protected Statement methodBlock(FrameworkMethod method) {
		Statement testMethodCompleteStatement = super.methodBlock(method);
		if (method.getAnnotation(GAETest.class) != null) {
			return new GAEStatement(testMethodCompleteStatement, method, getInjector());
		} else {
			return testMethodCompleteStatement;
		}
	}
}
