package com.avisys.microservice.exception;

public class OwnerAlreadyAssignedException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public OwnerAlreadyAssignedException(String message) {
		super(message);
	}
}
