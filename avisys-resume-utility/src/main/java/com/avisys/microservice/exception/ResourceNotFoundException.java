package com.avisys.microservice.exception;

public class ResourceNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 261273894132807422L;

	public ResourceNotFoundException() {
	}

	public ResourceNotFoundException(String message) {
		super(message);
	}
}
