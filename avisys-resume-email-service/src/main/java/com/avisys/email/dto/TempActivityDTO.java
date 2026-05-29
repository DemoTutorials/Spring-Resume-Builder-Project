package com.avisys.email.dto;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface TempActivityDTO {

	Long getRevId();
	@JsonIgnore
	Instant getRevTstmp();
	@JsonIgnore
	String getActionBy();
	@JsonIgnore
	Long getReferenceId();
	@JsonIgnore
	String getEntityTag();
	@JsonIgnore
	String getRevType();
	@JsonIgnore
	String getActionFor();

	String getVariableName();

	String getOldValue();

	String getNewValue();
}
