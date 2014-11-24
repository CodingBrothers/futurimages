package com.codingbrothers.futurimages.service.impl;

import javax.inject.Inject;

import org.jukito.UseModules;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.codingbrothers.appengine.testing.GAERunner;
import com.codingbrothers.appengine.testing.GAETest;
import com.codingbrothers.futurimages.config.FuturimagesCommonModule;
import com.codingbrothers.futurimages.config.FuturimagesTestModule;

@RunWith(GAERunner.class)
@UseModules({FuturimagesTestModule.class, FuturimagesCommonModule.class})
public class FuturimagesImplTest {

	@Inject
	private FuturimagesImpl futurimages;

	@Before
	public void setUp() {}

	@Test
	@GAETest
	public void test() {}
}
