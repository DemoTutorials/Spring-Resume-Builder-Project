package com.avisys.microservice.exception;

public class UniqueConstraintVoilationException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UniqueConstraintVoilationException(String message) {
		super(message);
	}
}
