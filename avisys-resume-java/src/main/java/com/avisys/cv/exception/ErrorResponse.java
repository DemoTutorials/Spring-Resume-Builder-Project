package com.avisys.cv.exception;

import java.time.LocalDate;
import java.time.LocalTime;

public class ErrorResponse {
	private String message;
	private LocalDate date = LocalDate.now();
	private LocalTime time = LocalTime.now();

	public ErrorResponse(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public LocalDate getDate() {
		return date;
	}

	public LocalTime getTime() {
		return time;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
