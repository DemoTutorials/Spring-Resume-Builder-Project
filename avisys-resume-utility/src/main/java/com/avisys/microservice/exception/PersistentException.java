package com.avisys.microservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class PersistentException extends RuntimeException {
	private static final long serialVersionUID = 261273894132807422L;

	public PersistentException() {
	}
	
	public PersistentException(String message) {
		super(message);
	}
}
