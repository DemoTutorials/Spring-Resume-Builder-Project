package com.auth.uam.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class GenericFignException  extends RuntimeException {
	private static final long serialVersionUID = 261273894132807422L;

	public GenericFignException() {
	}
	
	public GenericFignException(String message) {
		super(message);
	}
}