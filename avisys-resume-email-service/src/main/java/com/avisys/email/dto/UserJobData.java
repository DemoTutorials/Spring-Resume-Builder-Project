package com.avisys.email.dto;

import java.util.Map;

public class UserJobData {

	private Map<String, String> jobData;
	private String recipientsList;
	public UserJobData(Map<String, String> jobData, String recipientsList) {
		super();
		this.jobData = jobData;
		this.recipientsList = recipientsList;
	}
	public UserJobData() {
		// TODO Auto-generated constructor stub
	}
	public Map<String, String> getJobData() {
		return jobData;
	}
	public void setJobData(Map<String, String> jobData) {
		this.jobData = jobData;
	}
	public String getRecipientsList() {
		return recipientsList;
	}
	public void setRecipientsList(String recipientsList) {
		this.recipientsList = recipientsList;
	}
	@Override
	public String toString() {
		return "UserJobData [jobData=" + jobData + ", recipientsList=" + recipientsList + "]";
	}
	
	
}
