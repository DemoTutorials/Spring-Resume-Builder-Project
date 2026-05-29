package com.avisys.cv.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NotFoundGenericException extends RuntimeException {
	private static final long serialVersionUID = 261273894132807422L;

	public NotFoundGenericException() {
	}
	
	public NotFoundGenericException(String message) {
		super(message);
	}
}
