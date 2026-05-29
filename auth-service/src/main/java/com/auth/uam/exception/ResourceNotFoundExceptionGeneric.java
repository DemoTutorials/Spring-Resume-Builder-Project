package com.auth.uam.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundExceptionGeneric extends RuntimeException {
	private static final long serialVersionUID = 261273894132807422L;

	public ResourceNotFoundExceptionGeneric() {
	}
	
	public ResourceNotFoundExceptionGeneric(String message) {
		super(message);
	}
}
