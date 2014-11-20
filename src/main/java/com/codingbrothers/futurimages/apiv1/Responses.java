package com.codingbrothers.futurimages.apiv1;

import java.util.ResourceBundle;
import java.util.Set;

import javax.validation.ConstraintViolation;

public class Responses {

	public static ClientError createClientError(Set<ConstraintViolation<Object>> violations, ResourceBundle resourceBundle) {
		ClientError clientError = new ClientError();
		clientError.setMessage(resourceBundle.getString("ClientError.message.validationFailed"));
		for (ConstraintViolation<Object> violation : violations) {
			// TODO - fill in ClientError from Set<ConstraintViolation<Object>>
		}
		return clientError;
	}
}
