package com.codingbrothers.futurimages.apiv1;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;

import com.codingbrothers.futurimages.apiv1.util.APIV1Utils;
import com.codingbrothers.futurimages.config.StoreUserEndpointsAuthenticator;
import com.codingbrothers.futurimages.service.Futurimages;
import com.google.api.server.spi.Constant;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiClass;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiMethod.HttpMethod;
import com.google.api.server.spi.config.AuthLevel;
import com.google.appengine.api.users.User;
import com.google.common.collect.Iterables;
import com.googlecode.objectify.Key;

@Api(name = "futurimages", version = "v1", scopes = { APIV1Constants.PLUS_LOGIN_SCOPE, APIV1Constants.EMAIL_SCOPE }, clientIds = {
		APIV1Constants.FUTURIMAGES_WEB_CLIENT_ID, Constant.API_EXPLORER_CLIENT_ID }, authenticators = { StoreUserEndpointsAuthenticator.class })
@ApiClass(authLevel = AuthLevel.REQUIRED)
public class AuthenticatedUserV1 {

	@Inject
	private Futurimages service;

	// TODO - add page, page_size parameters
	
	@ApiMethod(name = "getAuthUserImages", path = "user", httpMethod = HttpMethod.GET)
	public Response getUser(@NotNull User user) {
		com.codingbrothers.futurimages.apiv1.User apiV1User = new com.codingbrothers.futurimages.apiv1.User();
		apiV1User.setLogin(user.getEmail());
		// TODO - fill in authenticated user's first, last name
		// this to be retrieved via G+ by means of using 'Google APIs Client Library for Java'
		return apiV1User;
	}

	@ApiMethod(name = "getAuthUserImages", path = "user/images", httpMethod = HttpMethod.GET)
	public Iterable<? extends Response> getImages(@NotNull User user) {
		return Iterables.transform(service.getUserImages(
				Key.create(com.codingbrothers.futurimages.domain.User.class, user.getUserId()), null, null, false, 0,
				APIV1Constants.DEFAULT_PAGE_SIZE), APIV1Utils.convertToAPIImageFunction(user, null));
	}
}
