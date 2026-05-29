package com.avisys.microservice.exception;

import javax.persistence.PersistenceException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.avisys.microservice.dto.ErrorResponse;

@ControllerAdvice
public class RestExceptionHandler {

	@ExceptionHandler(value = { ResourceNotFoundException.class })
	public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex) {
		ErrorResponse errorResp = new ErrorResponse(ex.getMessage());
		return new ResponseEntity<>(errorResp, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(value = { PersistenceException.class })
	public ResponseEntity<ErrorResponse> handlePersistenceException(PersistenceException ex) {
		ErrorResponse errorResp = new ErrorResponse(ex.getMessage());
		return new ResponseEntity<>(errorResp, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	

}
