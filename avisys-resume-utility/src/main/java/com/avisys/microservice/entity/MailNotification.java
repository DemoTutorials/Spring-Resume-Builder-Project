package com.avisys.microservice.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "MAIL")
@SequenceGenerator(name = "mail_id_seq", sequenceName = "mail_id_seq", allocationSize = 1)
public class MailNotification {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mail_id_seq")
	@Column(name = "id")
	private Long mailId;

	@Column(name = "email_template")
	private String emailTemplate;

	@Column(name = "email_status")
	private String emailStatus;

	@Column(name = "email_body", columnDefinition = "TEXT")
	private String emailBody;

	@Column(name = "email_subject")
	private String emailSubject;

	@Column(name = "email_from")
	private String emailFrom;

	@Column(name = "email_to")
	private String sendTO;

	@Column(name = "email_cc")
	private String sendCc;

	@Column(name = "email_bcc")
	private String sendBcc;

	@Column(name = "is_deleted")
	private Boolean isDeleted;

	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "created_date")
	private LocalDateTime createdDate;

	@Column(name = "updated_by")
	private String updatedBy;

	@Column(name = "updated_date")
	private LocalDateTime updatedDate;

	@Column(name = "owner")
	private String owner;

	@Column(name = "reference_id")
	private String referenceId;

	@Column(name = "reference_object_type")
	private String referenceObjectType;

	@JsonIgnoreProperties({ "mailNotification" })
	@OneToMany(mappedBy = "mailNotification")
	private List<MailAttachment> mailAttachment;

	@Transient
	private LocalDate date;

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

	public List<MailAttachment> getMailAttachment() {
		return mailAttachment;
	}

	public void setMailAttachment(List<MailAttachment> mailAttachment) {
		this.mailAttachment = mailAttachment;
	}

	public LocalDate getDate() {

		return getCreatedDate().toLocalDate();
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "MailNotification [mailId=" + mailId + ", emailTemplate=" + emailTemplate + ", emailStatus="
				+ emailStatus + ", emailBody=" + emailBody + ", emailSubject=" + emailSubject + ", emailFrom="
				+ emailFrom + ", sendTO=" + sendTO + ", sendCc=" + sendCc + ", sendBcc=" + sendBcc + ", isDeleted="
				+ isDeleted + ", createdBy=" + createdBy + ", createdDate=" + createdDate + ", updatedBy=" + updatedBy
				+ ", updatedDate=" + updatedDate + ", owner=" + owner + ", referenceId=" + referenceId
				+ ", referenceObjectType=" + referenceObjectType + ", mailAttachment=" + mailAttachment + ", date="
				+ date + "]";
	}

}