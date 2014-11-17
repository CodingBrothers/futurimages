package com.codingbrothers.futurimages.apiv1;

import javax.inject.Inject;

import org.jukito.UseModules;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.codingbrothers.appengine.testing.GAERunner;
import com.codingbrothers.futurimages.config.FuturimagesApiV1Module;
import com.codingbrothers.futurimages.config.FuturimagesCommonModule;
import com.codingbrothers.futurimages.config.FuturimagesTestModule;

@RunWith(GAERunner.class)
@UseModules({ FuturimagesTestModule.class, FuturimagesCommonModule.class, FuturimagesApiV1Module.class })
public class ImagesV1Test {

	@Inject
	private ImagesV1 api; // safe to inject it as apis (a.k.a. SystemServices) are singletons, anyway

	@Test
	public void getImageReturnsClientError() {
		System.out.println("getImageReturnsClientError");
		System.out.println(api.getImage("does-not-matter", null));
	}

}
