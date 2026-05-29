package com.auth.uam.exception;

public class ValidationException extends RuntimeException {

	private static final long serialVersionUID = -5186834997777277507L;

	public ValidationException() { }

	public ValidationException(String message) {
		super(message);
	}
}
