package com.avisys.email.exception;

public class EmailTemplateNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EmailTemplateNotFoundException(String message) {
		super(message);
	}
}
