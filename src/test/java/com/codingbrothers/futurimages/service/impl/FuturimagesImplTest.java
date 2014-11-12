package com.codingbrothers.futurimages.service.impl;

import static com.google.appengine.api.datastore.FetchOptions.Builder.withLimit;
import static org.junit.Assert.assertEquals;

import java.io.Closeable;
import java.io.IOException;

import javax.inject.Inject;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.codingbrothers.futurimages.boot.FuturimagesModule;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.dev.LocalDatastoreService.AutoIdAllocationPolicy;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.google.inject.Guice;
import com.googlecode.objectify.ObjectifyService;

public class FuturimagesImplTest {

	private final LocalServiceTestHelper appengineServices = new LocalServiceTestHelper(
			new LocalDatastoreServiceTestConfig().setDefaultHighRepJobPolicyUnappliedJobPercentage(50)
					.setAutoIdAllocationPolicy(AutoIdAllocationPolicy.SCATTERED).setNoIndexAutoGen(true));

	private Closeable objectifyServiceHandle;

	@Inject
	private FuturimagesImpl futurimages;

	@Before
	public void setUp() {
		appengineServices.setUp();
		Guice.createInjector(new FuturimagesModule()).injectMembers(this);
		objectifyServiceHandle = ObjectifyService.begin();
	}

	@After
	public void tearDown() throws IOException {
		objectifyServiceHandle.close();
		appengineServices.tearDown();
	}

	@Test
	public void test() {
		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
		assertEquals(0, ds.prepare(new Query("yam")).countEntities(withLimit(10)));
		ds.put(new Entity("yam"));
		ds.put(new Entity("yam"));
		assertEquals(2, ds.prepare(new Query("yam")).countEntities(withLimit(10)));
	}
}
