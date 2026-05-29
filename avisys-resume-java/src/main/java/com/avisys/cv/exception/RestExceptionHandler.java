package com.avisys.cv.exception;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.PersistenceException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;

@ControllerAdvice
public class RestExceptionHandler {

	@ExceptionHandler(value = { NotFoundGenericException.class })
	public ResponseEntity<ErrorResponse> handleResourceNotFoundException(NotFoundGenericException ex) {

		ErrorResponse errorResp = new ErrorResponse(ex.getMessage());
		return new ResponseEntity<>(errorResp, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(value = { PersistenceException.class })
	public ResponseEntity<ErrorResponse> handlePersistenceException(PersistenceException ex) {

		ErrorResponse errorResp = new ErrorResponse(ex.getMessage());
		return new ResponseEntity<>(errorResp, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(value = { HttpMessageNotReadableException.class })
	public ResponseEntity<ErrorResponse> handleJSONParseError(HttpMessageNotReadableException ex) {

		Throwable rootCause = ex.getRootCause();
		if (rootCause instanceof InvalidFormatException) {
			InvalidFormatException jsonParseException = (InvalidFormatException) rootCause;
			String fieldName = jsonParseException.getOriginalMessage();
			ErrorResponse errorResp = new ErrorResponse(fieldName);
			return new ResponseEntity<>(errorResp, HttpStatus.INTERNAL_SERVER_ERROR);
		} else {
			ErrorResponse errorResp = new ErrorResponse(rootCause.getMessage());
			return new ResponseEntity<>(errorResp, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@ExceptionHandler(value = { NullPointerException.class })
	public ResponseEntity<?> handleNullPointerException(NullPointerException ex) {

		StackTraceElement[] stackTrace = ex.getStackTrace();
		StackTraceElement element = stackTrace.length > 0 ? stackTrace[0] : null;

		String errorMessage = "Exception: " + ex.getClass().getSimpleName() + "  ||  "
				+ (element != null
						? "Class: " + element.getClassName() + "  ||  " + "Method Name: " + element.getMethodName()
								+ "  ||  " + "Line No.: " + element.getLineNumber()
						: "")
				+ "  ||  " + "Message: " + ex.getMessage();

		ErrorResponse errorResp = new ErrorResponse(errorMessage);
//		 ErrorResponse errorResp = new ErrorResponse(ex.getClass().getSimpleName()+" "+ex.getMessage());
		return new ResponseEntity<>(errorResp, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
		Map<String, List<String>> fieldErrors = new HashMap<>();
		for (FieldError error : ex.getBindingResult().getFieldErrors()) {
			String fieldName = error.getField();
			String errorMessage = error.getDefaultMessage();
			fieldErrors.computeIfAbsent(fieldName, k -> new ArrayList<>()).add(errorMessage);
		}
		return new ResponseEntity<>(fieldErrors, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(MaxUploadSizeExceededException.class)
	public ResponseEntity<ErrorResponse> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException ex) {

		ErrorResponse errorResp = new ErrorResponse(ex.getMessage());
		return new ResponseEntity<>(errorResp, HttpStatus.NOT_FOUND);
	}

}
