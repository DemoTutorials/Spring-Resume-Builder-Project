package com.avisys.microservice.model;

import org.springframework.stereotype.Component;

@Component
public class ResponseStatus {

	private int statusCode;
	private Object message;

	public ResponseStatus() {
		super();
	}

	public ResponseStatus(int statusCode, String message) {
		super();
		this.statusCode = statusCode;
		this.message = message;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public Object getMessage() {
		return message;
	}

	public void setMessage(Object message) {
		this.message = message;
	}

}
