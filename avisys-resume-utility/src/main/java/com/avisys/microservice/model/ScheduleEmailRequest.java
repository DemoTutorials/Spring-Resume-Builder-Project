package com.avisys.microservice.model;

import java.time.LocalDateTime;
import java.time.ZoneId;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class ScheduleEmailRequest {

//	"email":"ajayshinde0110@gmail.com",
//    "subject":"Attachement code pratice 25 Jan 2022",
//    "body":"Hi Ajay, <br><br>Hope you are doing well this is from future<br> Please find attached document for your reference.</br><br><br>Thanks, <br>Ajay Shinde",
//    "dateTime":"2022-01-25T14:51:30",
//    "timeZone":"Asia/Kolkata"

	@NotEmpty
	private String jobName;
	
	@NotEmpty
	private String groupName;

	@NotEmpty
	private String email;

	@NotEmpty
	private String subject;

	@NotEmpty
	private String body;

	@NotNull
	private LocalDateTime dateTime;

	@NotNull
	private ZoneId timeZone;

	public String getEmail() {
		return email;
	}

	public ScheduleEmailRequest() {
		super();
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public LocalDateTime getDateTime() {
		return dateTime;
	}

	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}

	public ZoneId getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(ZoneId timeZone) {
		this.timeZone = timeZone;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	@Override
	public String toString() {
		return "ScheduleEmailRequest [jobName=" + jobName + ", groupName=" + groupName + ", email=" + email
				+ ", subject=" + subject + ", body=" + body + ", dateTime=" + dateTime + ", timeZone=" + timeZone + "]";
	}

}
