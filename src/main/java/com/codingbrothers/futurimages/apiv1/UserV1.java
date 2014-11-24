package com.codingbrothers.futurimages.apiv1;

import javax.validation.constraints.NotNull;

import com.codingbrothers.futurimages.config.StoreUserEndpointsAuthenticator;
import com.google.api.server.spi.Constant;
import com.google.api.server.spi.ServiceException;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiClass;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiMethod.HttpMethod;
import com.google.api.server.spi.config.AuthLevel;
import com.google.api.server.spi.response.ServiceUnavailableException;
import com.google.appengine.api.users.User;

@Api(name = "futurimages", version = "v1", scopes = { APIV1Constants.PLUS_LOGIN_SCOPE, APIV1Constants.EMAIL_SCOPE }, clientIds = {
		APIV1Constants.FUTURIMAGES_WEB_CLIENT_ID, Constant.API_EXPLORER_CLIENT_ID }, authenticators = { StoreUserEndpointsAuthenticator.class })
@ApiClass(authLevel = AuthLevel.REQUIRED)
public class UserV1 {

	@ApiMethod(path = "user/images", httpMethod = HttpMethod.GET)
	public Response getImages(@NotNull User user) throws ServiceException {
		throw new ServiceUnavailableException("");
	}
}
