package com.codingbrothers.futurimages.apiv1;

import javax.inject.Named;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.codingbrothers.futurimages.config.StoreUserEndpointsAuthenticator;
import com.google.api.server.spi.Constant;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiClass;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiMethod.HttpMethod;
import com.google.api.server.spi.config.AuthLevel;
import com.google.api.server.spi.config.Nullable;

@Api(name = "futurimages", version = "v1", scopes = { APIV1Constants.PLUS_LOGIN_SCOPE, APIV1Constants.EMAIL_SCOPE }, clientIds = {
		APIV1Constants.FUTURIMAGES_WEB_CLIENT_ID, Constant.API_EXPLORER_CLIENT_ID }, authenticators = { StoreUserEndpointsAuthenticator.class })
@ApiClass(authLevel = AuthLevel.OPTIONAL_CONTINUE)
public class ImageV1 {

	@ApiMethod(path = "images/{id}", httpMethod = HttpMethod.GET)
	public Response getImage(@NotNull @Named("id") String id, @Nullable @Valid @Named("view_type") ViewType viewType) {
		return null;
	}

	@ApiMethod(path = "images/{id}", httpMethod = HttpMethod.DELETE)
	public Response deleteImage(@NotNull @Named("id") String id) {
		return null;
	}
}
