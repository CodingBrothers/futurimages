package com.codingbrothers.futurimages.apiv1;

import javax.inject.Named;
import javax.validation.constraints.NotNull;

import com.google.api.server.spi.Constant;
import com.google.api.server.spi.config.Api;

@Api(name = "futurimages", version = "v1", scopes = { APIV1Constants.PLUS_LOGIN_SCOPE, APIV1Constants.EMAIL_SCOPE }, clientIds = {
		APIV1Constants.FUTURIMAGES_WEB_CLIENT_ID, Constant.API_EXPLORER_CLIENT_ID })
public class ImagesV1 {

	public Response getImage(@Named String id, @NotNull ClientError clientError) {
		System.out.println("getImage: " + id + ", clientError=" + clientError);
		return null;
	}

	public void removeImage(@Named String id) {
		System.out.println("removeImage: " + id);
	}
}
