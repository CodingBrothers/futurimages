package com.codingbrothers.appengine.testing;

import java.util.List;

import com.google.appengine.tools.development.testing.LocalServiceTestConfig;

public interface GAELocalServicesConfigurator {

	void configure(List<LocalServiceTestConfig> configs);
}
