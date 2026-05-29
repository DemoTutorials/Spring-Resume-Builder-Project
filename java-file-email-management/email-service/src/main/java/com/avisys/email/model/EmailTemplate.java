package com.avisys.email.model;

import java.util.Date;

//CREATE TABLE crm.email_template (
//		email_template_id serial4 NOT NULL,
//		email_template_name varchar NULL,
//		email_template_content varchar(1000) NULL,
//		email_template_version int4 NOT NULL,
//		is_deleted bool NULL,
//		created_at time NULL DEFAULT now(),
//		created_by varchar NULL DEFAULT '-1'::integer,
//		updated_at time NULL,
//		updated_by varchar NULL
//	);

public class EmailTemplate {

	private int emailTemplateId;
	private String emailTemplateName;
	private String emailTemplateContent;
	private int emailTemplateVersion;
	private boolean isDeleted;
	private String createdBy;
	private Date createdAt;
	private String updatedBy;
	private Date updatedAt;

	public EmailTemplate() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getEmailTemplateId() {
		return emailTemplateId;
	}

	public void setEmailTemplateId(int emailTemplateId) {
		this.emailTemplateId = emailTemplateId;
	}

	public String getEmailTemplateName() {
		return emailTemplateName;
	}

	public void setEmailTemplateName(String emailTemplateName) {
		this.emailTemplateName = emailTemplateName;
	}

	public String getEmailTemplateContent() {
		return emailTemplateContent;
	}

	public void setEmailTemplateContent(String emailTemplateContent) {
		this.emailTemplateContent = emailTemplateContent;
	}

	public int getEmailTemplateVersion() {
		return emailTemplateVersion;
	}

	public void setEmailTemplateVersion(int emailTemplateVersion) {
		this.emailTemplateVersion = emailTemplateVersion;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	@Override
	public String toString() {
		return "EmailTemplate [emailTemplateId=" + emailTemplateId + ", emailTemplateName=" + emailTemplateName
				+ ", emailTemplateContent=" + emailTemplateContent + ", emailTemplateVersion=" + emailTemplateVersion
				+ ", isDeleted=" + isDeleted + ", createdBy=" + createdBy + ", createdAt=" + createdAt + ", updatedBy="
				+ updatedBy + ", updatedAt=" + updatedAt + "]";
	}

}
