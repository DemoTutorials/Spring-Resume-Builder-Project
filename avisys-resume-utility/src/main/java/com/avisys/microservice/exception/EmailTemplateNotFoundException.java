package com.avisys.microservice.exception;

public class EmailTemplateNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EmailTemplateNotFoundException(String message) {
		super(message);
	}
}
