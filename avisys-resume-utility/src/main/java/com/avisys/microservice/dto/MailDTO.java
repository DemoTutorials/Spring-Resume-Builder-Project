package com.avisys.microservice.dto;

import java.time.LocalDateTime;
import java.util.Map;

import javax.validation.constraints.NotEmpty;

import org.springframework.lang.Nullable;

public class MailDTO {

	@Nullable
	Long mailId;
	@NotEmpty
	String emailTemplate;
	@NotEmpty
	String emailStatus;
	@NotEmpty
	String emailBody;
	@NotEmpty
	String emailSubject;
	@NotEmpty
	String emailFrom;
	@NotEmpty
	String sendTO;
	@NotEmpty
	String sendCc;
	@NotEmpty
	String sendBcc;
	@NotEmpty
	Boolean isDeleted;
	@NotEmpty
	String createdBy;
	@NotEmpty
	LocalDateTime createdDate;
	@NotEmpty
	String updatedBy;
	@NotEmpty
	LocalDateTime updatedDate;
	@NotEmpty
	String owner;
	@NotEmpty
	String referenceId;
	@NotEmpty
	String referenceObjectType;
	@NotEmpty
	Map<String, String> jobData;
	@NotEmpty
	String jobName;

	public Long getMailId() {
		return mailId;
	}

	public void setMailId(Long mailId) {
		this.mailId = mailId;
	}

	public String getEmailTemplate() {
		return emailTemplate;
	}

	public void setEmailTemplate(String emailTemplate) {
		this.emailTemplate = emailTemplate;
	}

	public String getEmailStatus() {
		return emailStatus;
	}

	public void setEmailStatus(String emailStatus) {
		this.emailStatus = emailStatus;
	}

	public String getEmailBody() {
		return emailBody;
	}

	public void setEmailBody(String emailBody) {
		this.emailBody = emailBody;
	}

	public String getEmailSubject() {
		return emailSubject;
	}

	public void setEmailSubject(String emailSubject) {
		this.emailSubject = emailSubject;
	}

	public String getEmailFrom() {
		return emailFrom;
	}

	public void setEmailFrom(String emailFrom) {
		this.emailFrom = emailFrom;
	}

	public String getSendTO() {
		return sendTO;
	}

	public void setSendTO(String sendTO) {
		this.sendTO = sendTO;
	}

	public String getSendCc() {
		return sendCc;
	}

	public void setSendCc(String sendCc) {
		this.sendCc = sendCc;
	}

	public String getSendBcc() {
		return sendBcc;
	}

	public void setSendBcc(String sendBcc) {
		this.sendBcc = sendBcc;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public LocalDateTime getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(LocalDateTime updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getReferenceId() {
		return referenceId;
	}

	public void setReferenceId(String referenceId) {
		this.referenceId = referenceId;
	}

	public String getReferenceObjectType() {
		return referenceObjectType;
	}

	public void setReferenceObjectType(String referenceObjectType) {
		this.referenceObjectType = referenceObjectType;
	}

	public Map<String, String> getJobData() {
		return jobData;
	}

	public void setJobData(Map<String, String> jobData) {
		this.jobData = jobData;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	@Override
	public String toString() {
		return "MailDTO [mailId=" + mailId + ", emailTemplate=" + emailTemplate + ", emailStatus=" + emailStatus
				+ ", emailBody=" + emailBody + ", emailSubject=" + emailSubject + ", emailFrom=" + emailFrom
				+ ", sendTO=" + sendTO + ", sendCc=" + sendCc + ", sendBcc=" + sendBcc + ", isDeleted=" + isDeleted
				+ ", createdBy=" + createdBy + ", createdDate=" + createdDate + ", updatedBy=" + updatedBy
				+ ", updatedDate=" + updatedDate + ", owner=" + owner + ", referenceId=" + referenceId
				+ ", referenceObjectType=" + referenceObjectType + ", jobData=" + jobData + ", jobName=" + jobName
				+ "]";
	}

}
