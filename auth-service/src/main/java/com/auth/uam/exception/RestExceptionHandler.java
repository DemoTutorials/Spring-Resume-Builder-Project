package com.auth.uam.exception;

import javax.persistence.PersistenceException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import feign.FeignException;

@ControllerAdvice
public class RestExceptionHandler {

	

	@ExceptionHandler(value = { ResourceNotFoundExceptionGeneric.class })
	public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundExceptionGeneric ex) {
		
		ErrorResponse errorResp = new ErrorResponse(ex.getMessage());
		return new ResponseEntity<>(errorResp, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(value = { PersistenceException.class })
	public ResponseEntity<ErrorResponse> handlePersistenceException(PersistenceException ex) {

		ErrorResponse errorResp = new ErrorResponse(ex.getMessage());
		return new ResponseEntity<>(errorResp, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	
	@ExceptionHandler(FeignException.class)
    public ResponseEntity<ErrorResponse> handleFeignStatusException(FeignException ex) {
        String errorMessage = ex.getMessage();
        
        // Extract the message you need. FeignException's message typically includes the status and reason phrase.
        // You might need to parse it if it's not in the format you expect.
        // Example: "status 404 reading ServiceClient#getService()"
        ErrorResponse errorResp = new ErrorResponse(errorMessage);
        return new ResponseEntity<>(errorResp, HttpStatus.valueOf(ex.status()));
    }
	
}
