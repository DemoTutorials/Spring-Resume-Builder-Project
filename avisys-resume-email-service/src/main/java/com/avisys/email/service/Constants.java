package com.avisys.email.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class Constants {

	@Value("${spring-email-url}")
	public String emailUrl;

	@Value("${spring-email-and-file-upload}")
	public String emailAndFileUpload;

	@Value("${email-template-variable}")
	private String emailTemplateVariables;

	public String getEmailTemplateVariables() {
		return emailTemplateVariables;
	}

	public String getEmailUrl() {
		return emailUrl;
	}

	public String getEmailAndFileUpload() {
		return emailAndFileUpload;
	}


}
