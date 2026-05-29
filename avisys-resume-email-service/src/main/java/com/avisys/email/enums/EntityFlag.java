package com.avisys.email.enums;

public enum EntityFlag {

	Notes("NOTES"), 
	MailNotification("MAIL_NOTIFICATION"),
	MailAttachment("MAIL_ATTACHMENT"),
	UserInfo("USER_INFO"),
	Designation("DESIGNATION"),
	EmailTemplate("EMAIL_TEMPLATE"),
	CommonMaster("COMMON_MASTER");

	// declaring private variable for getting values
	private String name;

	// getter method
	public String getName() {
		return this.name;
	}

	// enum constructor - cannot be public or protected
	private EntityFlag(String name) {
		this.name = name;
	}
}
