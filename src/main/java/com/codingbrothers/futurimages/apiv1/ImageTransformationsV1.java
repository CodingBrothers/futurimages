package com.codingbrothers.futurimages.apiv1;

import javax.annotation.Nullable;
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
public class ImageTransformationsV1 {

	@ApiMethod(name="getImageTransformations", path = "images/{id}/transformations", httpMethod = HttpMethod.GET)
	public Response getImageTransformations(@NotNull @Named("id") String id,
			@Nullable @ISODateTime @Named("since") String sinceRaw,
			@Nullable @Named("sort_by_dir") SortByDir sortByDir, @Nullable @Valid @Named("view_type") ViewType viewType) {
		DateTime since = sinceRaw != null ? DateTime.parse(sinceRaw, ISODateTimeFormat.dateTimeParser()) : null;
		sortByDir = sortByDir != null ? sortByDir : SortByDir.DESC;
		viewType = viewType != null ? viewType : new ViewType()
				.setImageSize(APIV1Constants.DEFAULT_IMAGE_TRANSFORMATION_SIZE_TO_RETURN);

		return null;
	}

	@ApiMethod(name="createImageTransformation", path = "images/{id}/transformations", httpMethod = HttpMethod.POST, authLevel = AuthLevel.REQUIRED)
	public Response createImageTransformation(@NotNull @Named("id") String id,
			@NotNull @Valid ImageTransformationToCreate transformation, @NotNull User user) {
		return null;
	}
}
