package com.codingbrothers.futurimages.util;

import java.io.Closeable;
import java.io.IOException;

import com.google.appengine.tools.development.testing.LocalServiceTestConfig;
import com.googlecode.objectify.ObjectifyService;

public class ObjectifyLocalServiceTestConfig implements LocalServiceTestConfig {

	private Closeable objectifyServiceHandle;

	@Override
	public void setUp() {
		objectifyServiceHandle = ObjectifyService.begin();
	}

	@Override
	public void tearDown() {
		try {
			objectifyServiceHandle.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
