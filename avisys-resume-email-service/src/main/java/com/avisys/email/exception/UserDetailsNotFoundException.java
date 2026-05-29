package com.avisys.email.exception;

public class UserDetailsNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserDetailsNotFoundException(String message) {
		super(message);
	}
}
