package com.codingbrothers.futurimages.apiv1;

import javax.inject.Inject;

import org.jukito.UseModules;
import org.junit.runner.RunWith;

import com.codingbrothers.appengine.testing.GAERunner;
import com.codingbrothers.futurimages.config.FuturimagesApiV1Module;
import com.codingbrothers.futurimages.config.FuturimagesCommonModule;
import com.codingbrothers.futurimages.config.FuturimagesTestModule;
import com.codingbrothers.futurimages.util.RequestContext;
import com.google.appengine.api.users.User;

@RunWith(GAERunner.class)
@UseModules({ FuturimagesTestModule.class, FuturimagesCommonModule.class, FuturimagesApiV1Module.class })
public abstract class ApiTestSupport<T> {

	@Inject
	protected User user;
	
	@Inject
	protected RequestContext requestContext;

	@Inject
	protected T api; // safe to inject it as apis (a.k.a. SystemServices) are singletons, anyway
}
