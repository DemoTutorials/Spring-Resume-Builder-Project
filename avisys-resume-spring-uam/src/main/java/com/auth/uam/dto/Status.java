package com.auth.uam.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Data
public class Status {

	public Status() {

	}

	public Status(String message) {
		super();
		this.message = message;
	}

	public Status(String message, String statusCode) {
		super();
		this.message = message;
		this.statusCode = statusCode;
	}

	private String message;
	private String statusCode;
}
