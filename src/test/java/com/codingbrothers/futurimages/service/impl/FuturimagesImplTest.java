package com.codingbrothers.futurimages.service.impl;

import static com.google.appengine.api.datastore.FetchOptions.Builder.withLimit;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Date;

import javax.inject.Inject;

import org.jukito.JukitoRunner;
import org.jukito.UseModules;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.codingbrothers.futurimages.config.FuturimagesCommonModule;
import com.codingbrothers.futurimages.config.FuturimagesTestModule;
import com.codingbrothers.futurimages.domain.Image;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

@RunWith(JukitoRunner.class)
@UseModules({ FuturimagesCommonModule.class, FuturimagesTestModule.class })
public class FuturimagesImplTest {

	@Inject
	private FuturimagesImpl futurimages;

	@Inject
	private LocalServiceTestHelper appengineServices;

	@Before
	public void setUp() {
		appengineServices.setUp();
	}

	@After
	public void tearDown() throws IOException {
		appengineServices.tearDown();
	}

	@Test
	public void test() {
		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();

		Entity image = new Entity(Image.class.getName());
		image.setProperty("public", true);
		image.setProperty("userId", "dusan.kaloc@gmail.com");
		image.setProperty("createdAt", new Date());

		ds.put(image);

		Query q = new Query(Image.class.getName());
		q.setFilter(CompositeFilterOperator.and(FilterOperator.EQUAL.of("public", true),
				FilterOperator.EQUAL.of("userId", "dusan.kaloc@gmail.com"),
				FilterOperator.LESS_THAN_OR_EQUAL.of("createdAt", new Date())));
		q.addSort("createdAt", SortDirection.DESCENDING);

		assertEquals(1, ds.prepare(q).countEntities(withLimit(10)));
	}
}
