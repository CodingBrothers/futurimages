package com.codingbrothers.futurimages.apiv1;

import org.jukito.JukitoRunner;
import org.jukito.UseModules;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.codingbrothers.futurimages.config.FuturimagesApiV1Module;
import com.codingbrothers.futurimages.config.FuturimagesCommonModule;
import com.codingbrothers.futurimages.config.FuturimagesTestModule;

@RunWith(JukitoRunner.class)
@UseModules({ FuturimagesCommonModule.class, FuturimagesApiV1Module.class, FuturimagesTestModule.class })
public class ImagesV1Test {

	private ImagesV1 api;
	
	@Before
	public void setUp() {
		api = new ImagesV1();
	}
	
	@Test
	public void getImageReturnsClientError() {
		System.out.println(api.getImage("does-not-matter", null));
	}
	
	@Test
	public void removeImageDoesNothingYet() {
		api.removeImage("does-not-matter");
	}
	
}
