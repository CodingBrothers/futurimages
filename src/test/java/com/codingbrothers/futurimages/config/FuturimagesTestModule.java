package com.codingbrothers.futurimages.config;

import java.io.File;

import com.codingbrothers.futurimages.util.ForwardingLocalServerEnvironment;
import com.codingbrothers.futurimages.util.ObjectifyLocalServiceTestConfig;
import com.google.appengine.api.datastore.dev.LocalDatastoreService.AutoIdAllocationPolicy;
import com.google.appengine.tools.development.LocalServerEnvironment;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;

public class FuturimagesTestModule extends AbstractModule {

	@Override
	protected void configure() {
	}

	@Provides
	LocalServiceTestHelper provideLocalServiceTestHelper() {
		return new LocalServiceTestHelper(new ObjectifyLocalServiceTestConfig(), new LocalDatastoreServiceTestConfig()
				.setDefaultHighRepJobPolicyUnappliedJobPercentage(1)
				.setAutoIdAllocationPolicy(AutoIdAllocationPolicy.SCATTERED).setNoIndexAutoGen(true)) {

			@Override
			protected LocalServerEnvironment newLocalServerEnvironment() {
				return new ForwardingLocalServerEnvironment(super.newLocalServerEnvironment()) {

					@Override
					public File getAppDir() {
						return new File(System.getProperty("webapp.dir", "."));
					}
				};
			}
		};
	}
}
