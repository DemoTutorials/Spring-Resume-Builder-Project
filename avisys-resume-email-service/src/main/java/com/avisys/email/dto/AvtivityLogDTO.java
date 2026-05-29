package com.avisys.email.dto;

import java.time.Instant;
import java.util.List;

public class AvtivityLogDTO {

	private Instant date;
	private String message;
	List<TempActivityDTO> changedLog;
	
	
	public Instant getDate() {
		return date;
	}
	public void setDate(Instant date) {
		this.date = date;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public List<TempActivityDTO> getChangedLog() {
		return changedLog;
	}
	public void setChangedLog(List<TempActivityDTO> changedLog) {
		this.changedLog = changedLog;
	}
	
	

}
