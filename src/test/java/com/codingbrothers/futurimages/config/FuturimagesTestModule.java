package com.codingbrothers.futurimages.config;

import org.jukito.JukitoModule;

import com.codingbrothers.appengine.testing.GAETestModule;
import com.codingbrothers.futurimages.util.ObjectifyLocalServiceTestConfig;
import com.google.appengine.api.datastore.dev.LocalDatastoreService.AutoIdAllocationPolicy;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestConfig;
import com.google.inject.Provides;
import com.google.inject.name.Named;

public class FuturimagesTestModule extends JukitoModule {

	@Override
	protected void configureTest() {
		install(new GAETestModule());
	}

	@Provides
	@Named("FuturimagesObjectifyLocalServiceTestConfig")
	LocalServiceTestConfig provideObjectifyLocalServiceTestConfig() {
		return new ObjectifyLocalServiceTestConfig();
	}

	@Provides
	@Named("FuturimagesLocalDatastoreServiceTestConfig")
	LocalServiceTestConfig provideLocalDatastoreServiceTestConfig() {
		return new LocalDatastoreServiceTestConfig().setDefaultHighRepJobPolicyUnappliedJobPercentage(0)
				.setAutoIdAllocationPolicy(AutoIdAllocationPolicy.SCATTERED).setNoIndexAutoGen(false);
	}
}
