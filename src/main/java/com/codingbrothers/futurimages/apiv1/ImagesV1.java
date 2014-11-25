package com.codingbrothers.futurimages.apiv1;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.joda.time.DateTime;

import com.codingbrothers.futurimages.apiv1.util.APIV1Utils;
import com.codingbrothers.futurimages.apiv1.util.ISODateTime;
import com.codingbrothers.futurimages.config.StoreUserEndpointsAuthenticator;
import com.codingbrothers.futurimages.domain.Image;
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
@ApiClass(authLevel = AuthLevel.OPTIONAL_CONTINUE)
public class ImagesV1 {

	@Inject
	private AuthenticatedUserV1 authUserApi;

	@Inject
	private Futurimages service;

	// TODO - add page, page_size parameters
	
	@ApiMethod(name = "getAuthUserOrPublicImages", path = "images", httpMethod = HttpMethod.GET)
	public Iterable<? extends Response> getAuthUserOrPublicImages(
			@Nullable @ISODateTime @Named("since") String sinceRaw,
			@Nullable @Named("sort_by_dir") SortByDir sortByDir, @Nullable User user) {
		if (user != null) {
			return authUserApi.getImages(user);
		} else {
			return getPublicImages(sinceRaw, sortByDir, user);
		}
	}

	@ApiMethod(name = "getPublicImages", path = "images/public", httpMethod = HttpMethod.GET)
	public Iterable<? extends Response> getPublicImages(@Nullable @ISODateTime @Named("since") String sinceRaw,
			@Nullable @Named("sort_by_dir") SortByDir sortByDir, @Nullable User user) {
		DateTime since = sinceRaw != null ? APIV1Utils.parseISODateTime(sinceRaw) : null;
		sortByDir = sortByDir != null ? sortByDir : SortByDir.DESC;
		return Iterables.transform(service.getPublicImages(since.toDate(), null, sortByDir == SortByDir.ASC, 0,
				APIV1Constants.DEFAULT_PAGE_SIZE), APIV1Utils.convertToAPIImageFunction(user, null));
	}

	@ApiMethod(name = "uploadImage", path = "images", httpMethod = HttpMethod.POST, authLevel = AuthLevel.REQUIRED)
	public Response uploadImage(@NotNull @Valid ImageToUpload imageToUpload, @NotNull User user) {
		// note: imageToUpload.getImage() is never null - ensured by validation

		// create new image
		Image domainImage = Image.Builder()
				.fromUser(Key.create(com.codingbrothers.futurimages.domain.User.class, user.getUserId()))
				.setName(imageToUpload.getName()).setDescription(imageToUpload.getDescription())
				.setPublic(imageToUpload.isPublic())
				.setCreatedAt(DateTime.now().toDate())
				.build();

		// save it
		domainImage = service.createImage(domainImage, imageToUpload.getImage());

		// note: createImage ensures the domainImage has been assigned an id
		return APIV1Utils.convertToAPIImage(domainImage, user, null);
	}
}
