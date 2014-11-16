package com.codingbrothers.appengine.testing;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.MultipleFailureException;
import org.junit.runners.model.Statement;

import com.codingbrothers.futurimages.util.ForwardingLocalServerEnvironment;
import com.google.appengine.tools.development.LocalServerEnvironment;
import com.google.appengine.tools.development.testing.LocalCapabilitiesServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.google.inject.Binding;
import com.google.inject.Injector;
import com.google.inject.TypeLiteral;

class GAEStatement extends Statement {

	protected final Statement testMethodCompleteStatement;

	protected final FrameworkMethod testMethod;

	protected final Injector injector;

	public GAEStatement(Statement testMethodCompleteStatement, FrameworkMethod testMethod, Injector injector) {
		this.testMethodCompleteStatement = testMethodCompleteStatement;
		this.testMethod = testMethod;
		this.injector = injector;
	}

	@Override
	public void evaluate() throws Throwable {
		List<Throwable> errors = new ArrayList<Throwable>();
		LocalServiceTestHelper helper = null;
		try {
			(helper = initHelper()).setUp();
			testMethodCompleteStatement.evaluate();
		} catch (Throwable e) {
			errors.add(e);
		} finally {
			try {
				if (helper != null) {
					helper.tearDown();
				}
			} catch (Throwable e) {
				errors.add(e);
			}
		}
		MultipleFailureException.assertEmpty(errors);
	}

	protected LocalServiceTestHelper initHelper() throws InstantiationException, IllegalAccessException {
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
		GAECapabilities gaeCapabilities = testMethod.getAnnotation(GAECapabilities.class);
		if (gaeCapabilities != null) {
			for (GAECapability gaeCapability : gaeCapabilities.value()) {
				capabilitiesServiceTestConfig.setCapabilityStatus(gaeCapability.capability().getCapability(),
						gaeCapability.status());
			}
		}

		// Configurer
		GAETest gaeTest = testMethod.getAnnotation(GAETest.class);
		for (Class<? extends GAETestConfigurator> configurer : gaeTest.configurators()) {
			configurer.newInstance().configure(localServiceTestConfigs);
		}

		return new LocalServiceTestHelper(localServiceTestConfigs.toArray(new LocalServiceTestConfig[0])) {

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
