package com.codingbrothers.futurimages.apiv1;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;

import com.codingbrothers.futurimages.apiv1.util.ISODateTime;
import com.codingbrothers.futurimages.config.StoreUserEndpointsAuthenticator;
import com.google.api.server.spi.Constant;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiClass;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiMethod.HttpMethod;
import com.google.api.server.spi.config.AuthLevel;
import com.google.appengine.api.users.User;

@Api(name = "futurimages", version = "v1", scopes = { APIV1Constants.PLUS_LOGIN_SCOPE, APIV1Constants.EMAIL_SCOPE }, clientIds = {
		APIV1Constants.FUTURIMAGES_WEB_CLIENT_ID, Constant.API_EXPLORER_CLIENT_ID }, authenticators = { StoreUserEndpointsAuthenticator.class })
@ApiClass(authLevel = AuthLevel.OPTIONAL_CONTINUE)
public class ImagesV1 {

	@Inject
	private AuthenticatedUserV1 authUserApi;

	@ApiMethod(name="getAuthUserOrPublicImages", path = "images", httpMethod = HttpMethod.GET)
	public Response getAuthUserOrPublicImages(@Nullable @ISODateTime @Named("since") String sinceRaw,
			@Nullable @Named("sort_by_dir") SortByDir sortByDir, @Nullable User user) {
		if (user != null) {
			return authUserApi.getImages(user);
		} else {
			return getPublicImages(sinceRaw, sortByDir, user);
		}
	}

	@ApiMethod(name="getPublicImages", path = "images/public", httpMethod = HttpMethod.GET)
	public Response getPublicImages(@Nullable @ISODateTime @Named("since") String sinceRaw,
			@Nullable @Named("sort_by_dir") SortByDir sortByDir, @Nullable User user) {
		DateTime since = sinceRaw != null ? DateTime.parse(sinceRaw, ISODateTimeFormat.dateTimeParser()) : null;
		sortByDir = sortByDir != null ? sortByDir : SortByDir.DESC;

		return null;
	}

	@ApiMethod(name="uploadImage", path = "images", httpMethod = HttpMethod.POST, authLevel = AuthLevel.REQUIRED)
	public Response uploadImage(@NotNull @Valid ImageToUpload imageToUpload, @NotNull User user) {
		return null;
	}
}
