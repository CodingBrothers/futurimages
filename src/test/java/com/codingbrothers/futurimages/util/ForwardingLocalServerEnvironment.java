package com.codingbrothers.futurimages.util;

import java.io.File;

import com.google.appengine.tools.development.LocalServerEnvironment;

public class ForwardingLocalServerEnvironment implements LocalServerEnvironment {

	private final LocalServerEnvironment delegate;

	public ForwardingLocalServerEnvironment(LocalServerEnvironment delegate) {
		this.delegate = delegate;
	}

	@Override
	public boolean enforceApiDeadlines() {
		return delegate.enforceApiDeadlines();
	}

	@Override
	public String getAddress() {
		return delegate.getAddress();
	}

	@Override
	public File getAppDir() {
		return delegate.getAppDir();
	}

	@Override
	public String getHostName() {
		return delegate.getHostName();
	}

	@Override
	public int getPort() {
		return delegate.getPort();
	}

	@Override
	public boolean simulateProductionLatencies() {
		return delegate.simulateProductionLatencies();
	}

	@Override
	public void waitForServerToStart() throws InterruptedException {
		delegate.waitForServerToStart();
	}
}
