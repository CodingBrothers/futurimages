package com.codingbrothers.appengine.testing;

import java.util.List;

import com.google.appengine.tools.development.testing.LocalServiceTestConfig;

public interface GAETestConfigurator {

	void configure(List<LocalServiceTestConfig> configs);
}
