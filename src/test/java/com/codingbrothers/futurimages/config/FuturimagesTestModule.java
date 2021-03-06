package com.codingbrothers.futurimages.config;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import java.util.Arrays;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.jukito.JukitoModule;
import org.jukito.TestSingleton;

import com.codingbrothers.futurimages.util.ObjectifiedDeferredTaskCallback;
import com.codingbrothers.futurimages.util.ObjectifyLocalServiceTestConfig;
import com.google.appengine.api.datastore.dev.LocalDatastoreService.AutoIdAllocationPolicy;
import com.google.appengine.api.users.User;
import com.google.appengine.tools.development.testing.LocalBlobstoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalFileServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalImagesServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalTaskQueueTestConfig;
import com.google.appengine.tools.development.testing.LocalTaskQueueTestConfig.TaskCountDownLatch;
import com.google.common.collect.Iterators;
import com.google.inject.Key;
import com.google.inject.Provider;
import com.google.inject.Provides;
import com.google.inject.Scope;
import com.google.inject.name.Named;
import com.google.inject.servlet.RequestScoped;

public class FuturimagesTestModule extends JukitoModule {

	@Override
	protected void configureTest() {
		bindScope(RequestScoped.class, new Scope() {

			@Override
			public <T> Provider<T> scope(Key<T> key, Provider<T> unscoped) {
				return unscoped;
			}
		});

		bind(HttpServletRequest.class).toProvider(new Provider<HttpServletRequest>() {

			@Override
			public HttpServletRequest get() {
				HttpServletRequest req = mock(HttpServletRequest.class);
				doReturn(Locale.getDefault()).when(req).getLocale();
				doReturn(Iterators.asEnumeration(Arrays.asList(Locale.getDefault()).iterator())).when(req).getLocales();

				doReturn(new User("example@example.com", "example.com", "example(at)example.com")).when(req).getAttribute(
						FuturimagesCommonModule.LOGGED_IN_USER_REQ_ATTR_NAME);

				return req;
			}
		}).in(RequestScoped.class);
	}

	@Provides
	@Named("FuturimagesObjectifyLocalServiceTestConfig")
	LocalServiceTestConfig provideObjectifyLocalServiceTestConfig() {
		return new ObjectifyLocalServiceTestConfig();
	}

	@Provides
	@Named("FuturimagesLocalDatastoreServiceTestConfig")
	LocalServiceTestConfig provideLocalDatastoreServiceTestConfig() {
		return new LocalDatastoreServiceTestConfig().setDefaultHighRepJobPolicyUnappliedJobPercentage(50)
				.setAutoIdAllocationPolicy(AutoIdAllocationPolicy.SCATTERED).setNoIndexAutoGen(true);
	}

	@Provides
	@Named("FuturimagesLocalBlobstoreServiceTestConfig")
	LocalServiceTestConfig provideLocalBlobstoreServiceTestConfig() {
		return new LocalBlobstoreServiceTestConfig().setNoStorage(true);
	}

	@Provides
	@Named("FuturimagesLocalFileServiceTestConfig")
	LocalServiceTestConfig provideLocalFileServiceTestConfig() {
		return new LocalFileServiceTestConfig();
	}

	@Provides
	@TestSingleton
	TaskCountDownLatch provideTaskCountDownLatch() {
		return new TaskCountDownLatch(1);
	}

	@Provides
	@Named("FuturimagesLocalTaskQueueTestConfig")
	LocalServiceTestConfig provideLocalTaskQueueTestConfig(TaskCountDownLatch latch) {
		return new LocalTaskQueueTestConfig().setDisableAutoTaskExecution(false)
				.setCallbackClass(ObjectifiedDeferredTaskCallback.class).setTaskExecutionLatch(latch);
	}

	@Provides
	@Named("FuturimagesLocalImagesServiceTestConfig")
	LocalServiceTestConfig provideLocalImagesServiceTestConfig() {
		return new LocalImagesServiceTestConfig();
	}
}
