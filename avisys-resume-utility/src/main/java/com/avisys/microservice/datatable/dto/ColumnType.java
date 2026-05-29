package com.avisys.microservice.datatable.dto;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ColumnType {
	BUTTON("button"),
	ICON("icon"),
	RADIO("radio"),
	TEXT("text"),
	TEXTAREA("textarea");
	
	private String name;
	private ColumnType(String name) {
		this.name = name;
	}

	@JsonValue
	public String getName() {
		return this.name;
	}
}