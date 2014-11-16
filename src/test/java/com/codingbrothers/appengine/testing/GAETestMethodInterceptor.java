package com.codingbrothers.appengine.testing;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import com.codingbrothers.futurimages.util.ForwardingLocalServerEnvironment;
import com.google.appengine.tools.development.LocalServerEnvironment;
import com.google.appengine.tools.development.testing.LocalCapabilitiesServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.google.inject.Binding;
import com.google.inject.Injector;
import com.google.inject.TypeLiteral;

class GAETestMethodInterceptor implements MethodInterceptor {

	@Inject
	private Injector injector;

	public Object invoke(MethodInvocation invocation) throws Throwable {
		List<Binding<LocalServiceTestConfig>> localServiceTestConfigBindings = injector.findBindingsByType(TypeLiteral
				.get(LocalServiceTestConfig.class));

		List<LocalServiceTestConfig> localServiceTestConfigs = new ArrayList<>(localServiceTestConfigBindings.size());
		LocalCapabilitiesServiceTestConfig capabilitiesServiceTestConfig = null;

		for (Binding<LocalServiceTestConfig> binding : localServiceTestConfigBindings) {
			LocalServiceTestConfig configInstance = injector.getInstance(binding.getKey());

			if (configInstance instanceof LocalCapabilitiesServiceTestConfig) {
				capabilitiesServiceTestConfig = (LocalCapabilitiesServiceTestConfig) configInstance;
			}

			localServiceTestConfigs.add(configInstance);
		}

		// Capabilities
		if (capabilitiesServiceTestConfig == null) {
			capabilitiesServiceTestConfig = new LocalCapabilitiesServiceTestConfig();
			localServiceTestConfigs.add(capabilitiesServiceTestConfig);
		}
		GAECapabilities gaeCapabilities = invocation.getMethod().getAnnotation(GAECapabilities.class);
		if (gaeCapabilities != null) {
			for (GAECapability gaeCapability : gaeCapabilities.value()) {
				capabilitiesServiceTestConfig.setCapabilityStatus(gaeCapability.capability().getCapability(),
						gaeCapability.status());
			}
		}

		// Configurer
		GAETest gaeTest = invocation.getMethod().getAnnotation(GAETest.class);
		for (Class<? extends GAELocalServicesConfigurator> configurer : gaeTest.configurators()) {
			configurer.newInstance().configure(localServiceTestConfigs);
		}

		LocalServiceTestHelper localServiceTestHelper = new LocalServiceTestHelper(
				localServiceTestConfigs.toArray(new LocalServiceTestConfig[0])) {

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

		localServiceTestHelper.setUp();
		try {
			return invocation.proceed();
		} finally {
			localServiceTestHelper.tearDown();
		}
	}
}
