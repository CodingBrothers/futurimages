package com.codingbrothers.futurimages.apiv1;

public class ResponseWithHttpStatusCode implements Response {

	private Integer httpStatusCode;

	public Integer getHttpStatusCode() {
		return httpStatusCode;
	}

	public ResponseWithHttpStatusCode setHttpStatusCode(Integer httpStatusCode) {
		this.httpStatusCode = httpStatusCode;
		return this;
	}
}
