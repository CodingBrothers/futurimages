package com.codingbrothers.futurimages.util;

import com.google.appengine.api.urlfetch.URLFetchServicePb.URLFetchRequest;
import com.google.appengine.tools.development.testing.LocalTaskQueueTestConfig.DeferredTaskCallback;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.util.Closeable;

public class ObjectifiedDeferredTaskCallback extends DeferredTaskCallback {

	@Override
	public int execute(URLFetchRequest req) {
		try (Closeable closeable = ObjectifyService.begin()) {
			return super.execute(req);
		}
	}
}
