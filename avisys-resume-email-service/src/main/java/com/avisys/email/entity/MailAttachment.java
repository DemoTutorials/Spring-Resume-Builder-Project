package com.avisys.email.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.avisys.email.util.ConditionCheck;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "MAIL_ATTACHMENT")
@SequenceGenerator(name = "mail_attachment_id_seq", sequenceName = "mail_attachment_id_seq", allocationSize = 1)
public class MailAttachment implements Cloneable {

	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mail_attachment_id_seq")
	@Column(name = "id")
	private Long mailAttachmentId;
	
	@Column(name = "name",columnDefinition="TEXT")
	private String attachmentName;
	
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
	
	@JsonIgnoreProperties({ "mailAttachment"})
	@ManyToOne
	@JoinColumn(name = "mail_id")
	private MailNotification mailNotification;

	public Long getMailAttachmentId() {
		return mailAttachmentId;
	}

	public void setMailAttachmentId(Long mailAttachmentId) {
		this.mailAttachmentId = mailAttachmentId;
	}

	public String getAttachmentName() {
		return attachmentName;
	}

	public void setAttachmentName(String attachmentName) {
		this.attachmentName = attachmentName;
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

	public MailNotification getMailNotification() {
		return mailNotification;
	}

	public void setMailNotification(MailNotification mailNotification) {
		this.mailNotification = mailNotification;
	}

	@Override
	public String toString() {
		return "MailAttachment [mailAttachmentId=" + mailAttachmentId + ", attachmentName=" + attachmentName
				+ ", isDeleted=" + isDeleted + ", createdBy=" + createdBy + ", createdDate=" + createdDate
				+ ", updatedBy=" + updatedBy + ", updatedDate=" + updatedDate + ", owner=" + owner
				+ ", mailNotification=" + mailNotification + "]";
	}
	
	
	public String compareTo(MailAttachment o, MailAttachment n) {
		StringBuilder string = new StringBuilder();

		if (ConditionCheck.checkValues(n.mailAttachmentId, o.mailAttachmentId))
			string.append("mailAttachmentId--->" + n.mailAttachmentId + "--->" + o.mailAttachmentId + "::::");

		if (ConditionCheck.checkValues(n.attachmentName, o.attachmentName))
			string.append("attachmentName--->" + n.attachmentName + "--->" + o.attachmentName + "::::");
		
		if (ConditionCheck.checkValues(n.isDeleted, o.isDeleted))
			string.append("isDeleted--->" + n.isDeleted + "--->" + o.isDeleted + "::::");
		
		if (ConditionCheck.checkValues(n.createdBy, o.createdBy))
			string.append("createdBy--->" + n.createdBy + "--->" + o.createdBy + "::::");
		
		if (ConditionCheck.checkValues(n.createdDate, o.createdDate))
			string.append("createdDate--->" + n.createdDate + "--->" + o.createdDate + "::::");
		
		if (ConditionCheck.checkValues(n.updatedBy, o.updatedBy))
			string.append("updatedBy--->" + n.updatedBy + "--->" + o.updatedBy + "::::");
		
		if (ConditionCheck.checkValues(n.updatedDate, o.updatedDate))
			string.append("updatedDate--->" + n.updatedDate + "--->" + o.updatedDate + "::::");
		
		if (ConditionCheck.checkValues(n.owner, o.owner))
			string.append("owner--->" + n.owner + "--->" + o.owner + "::::");
		
		if (o.mailNotification == null && n.mailNotification != null) {

			string.append("mailNotification--->" + n.mailNotification.getMailId() + "--->" + "null" + "::::");

		} else if (o.mailNotification != null && n.mailNotification == null) {

			string.append("mailNotification--->" + "null" + "--->" + o.mailNotification.getMailId() + "::::");

		} else if (o.mailNotification != null && n.mailNotification != null) {

			if (o.mailNotification.getMailId() != n.mailNotification.getMailId()) {
				string.append(
						"mailNotification--->" + n.mailNotification.getMailId() + "--->" + o.mailNotification.getMailId() + "::::");
			}
		}
		
		return string.toString();
	}
	
}
