package com.codingbrothers.futurimages.apiv1;

import java.util.Locale;

public class APIV1Constants {

	public static final Locale API_MESSAGES_DEFAULT_LOCALE = Locale.US;
	public static final String API_MESSAGES_RESOURCE_BUNDLE_NAME = "messages-apiv1";
	
	public static final String FUTURIMAGES_WEB_CLIENT_ID = "881176597931-pi8v9l72t68c6s9lpm3mnhmv1227hog1.apps.googleusercontent.com";
	// implicitly includes the profile scope (see https://developers.google.com/+/api/oauth)
	public static final String PLUS_LOGIN_SCOPE = "https://www.googleapis.com/auth/plus.login";
	public static final String EMAIL_SCOPE = "email";
	
	public static final int DEFAULT_IMAGE_TRANSFORMATION_SIZE_TO_RETURN = 200;

}
