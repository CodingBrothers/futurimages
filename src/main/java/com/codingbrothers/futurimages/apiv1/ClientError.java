package com.codingbrothers.futurimages.apiv1;

import java.util.ArrayList;
import java.util.List;

public class ClientError extends ResponseWithHttpStatusCode {

	public static class Error {

		public static final String MISSING_CODE = "missing";
		public static final String MISSING_FIELD_CODE = "missing_field";
		public static final String INVALID_CODE = "invalid";
		public static final String ALREADY_EXISTS_CODE = "already_exists";

		private final String resource;
		private final String field;
		private final String code;

		public Error(String resource, String field, String code) {
			this.resource = resource;
			this.field = field;
			this.code = code;
		}

		public static Error missingError(String resource) {
			return new Error(resource, null, MISSING_CODE);
		}

		public static Error missingFieldError(String resource, String field) {
			return new Error(resource, field, MISSING_FIELD_CODE);
		}

		public static Error invalidError(String resource, String field) {
			return new Error(resource, field, INVALID_CODE);
		}

		public static Error alreadyExistsError(String resource, String field) {
			return new Error(resource, field, ALREADY_EXISTS_CODE);
		}

		public String getResource() {
			return resource;
		}

		public String getField() {
			return field;
		}

		public String getCode() {
			return code;
		}

	}

	private String message;
	private List<Error> errors;

	public String getMessage() {
		return message;
	}

	public ClientError setMessage(String message) {
		this.message = message;
		return this;
	}

	public List<Error> getErrors() {
		if (errors == null) {
			errors = new ArrayList<Error>();
		}
		return errors;
	}

	public ClientError setErrors(List<Error> errors) {
		this.errors = errors;
		return this;
	}

	public ClientError addError(Error error) {
		getErrors().add(error);
		return this;
	}

}
