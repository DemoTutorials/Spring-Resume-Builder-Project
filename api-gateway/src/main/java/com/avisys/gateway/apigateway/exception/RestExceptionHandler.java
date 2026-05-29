package com.avisys.gateway.apigateway.exception;

import org.springframework.http.HttpHeaders;

//import javax.persistence.PersistenceException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Header;



@ControllerAdvice
public class RestExceptionHandler {

	@ExceptionHandler(value = { ResourceNotFoundExceptionGeneric.class })
	public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundExceptionGeneric ex) {
		ErrorResponse errorResp = new ErrorResponse(ex.getMessage());
		return new ResponseEntity<>(errorResp, HttpStatus.NOT_FOUND);
	}
	

//    @ExceptionHandler(ExpiredJwtException.class)
//    public ResponseEntity<String> handleExpiredJwtException(ExpiredJwtException ex) {
//        // Log the exception or perform any necessary actions
//        // Return a ResponseEntity with the exception message
//        String errorMessage = "The JWT token has expired. Please login again.";
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
//                .body(errorMessage);
//    }
    

    @ExceptionHandler(value = {ExpiredJwtException.class})
    public ResponseEntity<Object> handleExpiredJwtException(Header header, Claims claims, String msg) {
    
//     ExceptionMessage exceptionMessage = new ExceptionMessage(ex.getMessage(), requestUri);
     return new ResponseEntity<>("The JWT token has expired. Please login again.", new HttpHeaders(), HttpStatus.FORBIDDEN);
    }
	

//	@ExceptionHandler(value = { PersistenceException.class })
//	public ResponseEntity<ErrorResponse> handlePersistenceException(PersistenceException ex) {
//		ErrorResponse errorResp = new ErrorResponse(ex.getMessage());
//		return new ResponseEntity<>(errorResp, HttpStatus.INTERNAL_SERVER_ERROR);
//	}

}
