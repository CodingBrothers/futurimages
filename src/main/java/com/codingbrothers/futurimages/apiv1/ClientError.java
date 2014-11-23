package com.codingbrothers.futurimages.apiv1;

import java.util.ArrayList;
import java.util.List;

public class ClientError extends ResponseWithHttpStatusCode {

	public static class Error {

		public static final String MISSING_CODE = "missing";
		public static final String MISSING_FIELD_CODE = "missing_field";
		public static final String INVALID_CODE = "invalid";
		public static final String ALREADY_EXISTS_CODE = "already_exists";
		
		public static final String RESOURCE_PATH_SEPARATOR = ".";

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

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((code == null) ? 0 : code.hashCode());
			result = prime * result + ((field == null) ? 0 : field.hashCode());
			result = prime * result + ((resource == null) ? 0 : resource.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Error other = (Error) obj;
			if (code == null) {
				if (other.code != null)
					return false;
			} else if (!code.equals(other.code))
				return false;
			if (field == null) {
				if (other.field != null)
					return false;
			} else if (!field.equals(other.field))
				return false;
			if (resource == null) {
				if (other.resource != null)
					return false;
			} else if (!resource.equals(other.resource))
				return false;
			return true;
		}

		@Override
		public String toString() {
			return "ClientError.Error [resource=" + resource + ", field=" + field + ", code=" + code + "]";
		}

	}

	private String message;
	private List<Error> errors;

	public ClientError() {
	}
	
	public ClientError(String message, List<Error> errors) {
		this.message = message;
		this.errors = errors;
	}

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
