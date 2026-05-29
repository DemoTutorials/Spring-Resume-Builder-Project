package com.auth.uam.exception;

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

import com.fasterxml.jackson.databind.exc.InvalidFormatException;

@ControllerAdvice
public class RestExceptionHandler {

	@ExceptionHandler(value = { NotFoundGenericException.class })
	public ResponseEntity<ErrorResponse> handleResourceNotFoundException(NotFoundGenericException ex) {

		ErrorResponse errorResp = new ErrorResponse(ex.getMessage());
		return new ResponseEntity<>(errorResp, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(value = { GenericFignException.class })
	public ResponseEntity<String> handleGenericFignException(GenericFignException ex) {

		return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(value = { PersistenceException.class })
	public ResponseEntity<ErrorResponse> handlePersistenceException(PersistenceException ex) {

		ErrorResponse errorResp = new ErrorResponse(ex.getMessage());
		return new ResponseEntity<>(errorResp, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(value = { ValidationException.class })
	public ResponseEntity<ErrorResponse> handleValidationException(ValidationException ex) {
		ErrorResponse errorResp = new ErrorResponse(ex.getMessage());
		return new ResponseEntity<>(errorResp, HttpStatus.BAD_REQUEST);
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

//	@ExceptionHandler(value = { MethodArgumentNotValidException.class })
//	public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
//
//
//		Map<String, List<String>> fieldErrors = new HashMap<>();
//		for (FieldError error : ex.getBindingResult().getFieldErrors()) {
//			String fieldName = error.getField();
//
//			if (fieldErrors.containsKey(fieldName)) {
//				List<String> existingList = fieldErrors.get(fieldName);
//				String defaultMessage = error.getDefaultMessage();
//				existingList.add(getErrorMessage(defaultMessage));
//				fieldErrors.put(fieldName, existingList);
//			} else {
//				String defaultMessage = error.getDefaultMessage();
//				List<String> messages = new ArrayList<>();
//				messages.add(getErrorMessage(defaultMessage));
//				fieldErrors.put(fieldName, messages);
//
//			}
//
//		}
//		return new ResponseEntity<>(fieldErrors, HttpStatus.BAD_REQUEST);
//	}

//	@ExceptionHandler(value = { UnexpectedTypeException.class })
//	public ResponseEntity<ErrorResponse> handleUnexpectedTypeException(UnexpectedTypeException ex) {
//		
//
//		ErrorResponse errorResp = new ErrorResponse(ex.getMessage());
//		return new ResponseEntity<>(errorResp, HttpStatus.BAD_REQUEST);
//	}
//
////	@ExceptionHandler(value = { InvalidFormatException.class })
////	public ResponseEntity<ErrorResponse> handleJSONParseError(InvalidFormatException ex) {
////		utilityProxy.createLog(createLogData(ex));
////		ErrorResponse errorResp = new ErrorResponse(ex.getMessage());
////		return new ResponseEntity<>(errorResp, HttpStatus.INTERNAL_SERVER_ERROR);
////	}
//
//	public ErrorLogs createLogData(Exception e) {
//		ErrorLogs error = new ErrorLogs();
//		error.setModule("ORDER-SERVICE");
//		error.setMessage(e.getMessage());
//
//		StringWriter sw = new StringWriter();
//		PrintWriter pw = new PrintWriter(sw);
//		e.printStackTrace(pw);
//
//		error.setDetailedDescription(sw.toString());
//
//		pw.close();
//
//		error.setDateAt(Instant.now());
//
//		return error;
//
//	}
//
//	private String getFieldName(JsonParseException jsonParseException) {
//		// You may need to customize this based on the structure of your JSON exception
//		// For example, you might parse the exception message or inspect the location
//		// information
//		// in the JsonParseException to get the field name.
//		// Here, I'm assuming that you can get the field name from the location
//		// information.
//
//		JsonLocation location = jsonParseException.getLocation();
//		if (location != null) {
//			// Assuming the field name is present in the column number
//			return "Field at column: " + location.getColumnNr();
//		} else {
//			return "Unknown field name";
//		}
//	}
//	
//	private String getErrorMessage(String message) {
//		/* Regular expression to match the desired pattern for Dropdown field error message */
//		String patternString = "\\%\\$+([^\\$]+)\\$+%"; 
//
//        Pattern pattern = Pattern.compile(patternString);
//        Matcher matcher = pattern.matcher(message);
//        
//        if (matcher.find()) {
//            String extractedString = matcher.group(1);
//            
//            return	ErrorMessages.Dropdown+loadCommonMaster.getDropdown(extractedString);
//        } else {
//        	return	message;
//        }
//		 
//	}
}
