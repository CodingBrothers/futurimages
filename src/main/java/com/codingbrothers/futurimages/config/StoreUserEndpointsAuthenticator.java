package com.codingbrothers.futurimages.config;

import javax.servlet.http.HttpServletRequest;

import com.google.api.server.spi.auth.EndpointsAuthenticator;
import com.google.api.server.spi.auth.UserContainer;
import com.google.api.server.spi.auth.common.User;
import com.google.api.server.spi.config.ApiMethodConfig;

public class StoreUserEndpointsAuthenticator extends EndpointsAuthenticator {

	@Override
	public UserContainer authenticate(HttpServletRequest request, ApiMethodConfig methodConfig) {
		UserContainer userContainer = super.authenticate(request, methodConfig);
		request.setAttribute(FuturimagesCommonModule.LOGGED_IN_USER_REQ_ATTR_NAME, userContainer != null ? userContainer.getEndpointsUser() : null);
		return userContainer;
	}

	@Override
	public User authenticate(HttpServletRequest request) {
		User user = super.authenticate(request);
		request.setAttribute(FuturimagesCommonModule.LOGGED_IN_USER_REQ_ATTR_NAME, user);
		return user;
	}

}
