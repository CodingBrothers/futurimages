package com.codingbrothers.futurimages.config;

import java.util.List;

import com.codingbrothers.appengine.testing.GAETestConfigurator;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestConfig;

public class HighReplicationDatastore implements GAETestConfigurator {

	@Override
	public void configure(List<LocalServiceTestConfig> configs) {
		for (LocalServiceTestConfig localServiceTestConfig : configs) {
			if (localServiceTestConfig instanceof LocalDatastoreServiceTestConfig) {
				((LocalDatastoreServiceTestConfig) localServiceTestConfig)
						.setDefaultHighRepJobPolicyUnappliedJobPercentage(50);
			}
		}
	}
}
