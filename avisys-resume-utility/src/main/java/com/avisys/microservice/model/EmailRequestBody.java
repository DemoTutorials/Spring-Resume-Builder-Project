package com.avisys.microservice.model;

import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class EmailRequestBody {

	private String templateId;
	private Date scheduleTime;
	private String recipient;
	private String jobName;

	private Map<String, String> jobData;

	public EmailRequestBody() {
		super();
	}

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public Date getScheduleTime() {
		return scheduleTime;
	}

	public void setScheduleTime(Date scheduleTime) {
		ZoneId asiaSingapore = ZoneId.of("Asia/Singapore");

		System.out.println(" scheduleTime " + scheduleTime);
		this.scheduleTime = scheduleTime;
	}

	public String getRecipient() {
		return recipient;
	}

	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public Map<String, String> getJobData() {
		return jobData;
	}

	public void setJobData(Map<String, String> jobData) {
		this.jobData = jobData;
	}

	@Override
	public String toString() {
		return "EmailRequestBody [templateId=" + templateId + ", scheduleTime=" + scheduleTime + ", recipient="
				+ recipient + ", jobName=" + jobName + ", jobData=" + jobData + "]";
	}

}
