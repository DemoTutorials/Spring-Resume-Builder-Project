package com.avisys.email.dto;

import java.util.Date;

public class EmailContent {
	private String emailTo;
	private String emailSubject;
	private String emailBody;
	private Date emailScheduledDate;

	public EmailContent() {
		super();
	}

	public String getEmailTo() {
		return emailTo;
	}

	public void setEmailTo(String emailTo) {
		this.emailTo = emailTo;
	}

	public String getEmailSubject() {
		return emailSubject;
	}

	public void setEmailSubject(String emailSubject) {
		this.emailSubject = emailSubject;
	}

	public String getEmailBody() {
		return emailBody;
	}

	public void setEmailBody(String emailBody) {
		this.emailBody = emailBody;
	}

	public Date getEmailScheduledDate() {
		return emailScheduledDate;
	}

	public void setEmailScheduledDate(Date emailScheduledDate) {
		this.emailScheduledDate = emailScheduledDate;
	}

	@Override
	public String toString() {
		return "EmailContent [emailTo=" + emailTo + ", emailSubject=" + emailSubject + ", emailBody=" + emailBody
				+ ", emailScheduledDate=" + emailScheduledDate + "]";
	}

}
