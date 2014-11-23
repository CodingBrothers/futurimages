package com.codingbrothers.futurimages.apiv1;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.google.api.server.spi.Constant;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiMethod.HttpMethod;
import com.google.api.server.spi.config.AuthLevel;
import com.google.appengine.api.users.User;

@Api(name = "futurimages", version = "v1", scopes = { APIV1Constants.PLUS_LOGIN_SCOPE, APIV1Constants.EMAIL_SCOPE }, clientIds = {
		APIV1Constants.FUTURIMAGES_WEB_CLIENT_ID, Constant.API_EXPLORER_CLIENT_ID }, authLevel = AuthLevel.OPTIONAL_CONTINUE)
public class ImagesV1 {

	@ApiMethod(path = "images", httpMethod = HttpMethod.POST, authLevel = AuthLevel.REQUIRED)
	public Response uploadImage(@NotNull @Valid ImageToUpload imageToUpload, @NotNull User user) {
		return null;
	}
}
