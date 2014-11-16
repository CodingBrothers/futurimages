package com.codingbrothers.futurimages.apiv1;

import org.jukito.UseModules;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.codingbrothers.appengine.testing.GAERunner;
import com.codingbrothers.futurimages.config.FuturimagesApiV1Module;
import com.codingbrothers.futurimages.config.FuturimagesCommonModule;
import com.google.inject.name.Named;

@RunWith(GAERunner.class)
@UseModules({ FuturimagesCommonModule.class, FuturimagesApiV1Module.class })
public class ImagesV1Test {

	private ImagesV1 api;

	@Before
	public void setUp(ImagesV1 api) {
		this.api = api;
		System.out.println("setUp");
		System.out.println(api);
	}

	@Test
	public void getImageReturnsClientError() {
		System.out.println("getImageReturnsClientError");
		System.out.println(api.getImage("does-not-matter", null));
	}

	@Test
	public void removeImageDoesNothingYet() {
		System.out.println("removeImageDoesNothingYet");
		api.removeImage("does-not-matter");
	}

	@Test
	public void testEndpointsGuiceAOP(@Named("AAA") Object api) {
		System.out.println("testEndpointsGuiceAOP");
		System.out.println(api);
	}
}
