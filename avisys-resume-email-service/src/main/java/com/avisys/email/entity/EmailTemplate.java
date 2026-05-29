package com.avisys.email.entity;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.avisys.email.util.ConditionCheck;
import com.avisys.email.views.Views;
import com.fasterxml.jackson.annotation.JsonView;

@Entity
@Table(name = "EMAIL_TEMPLATE")
@SequenceGenerator(name = "email_template_email_template_id_seq", sequenceName = "email_template_email_template_id_seq", allocationSize = 1)
public class EmailTemplate implements Cloneable {

	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	@Id
	@Column(name = "EMAIL_TEMPLATE_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "email_template_email_template_id_seq")
	@JsonView({ Views.EmailTemplate.class })
	private Long id;

	@Column(name = "EMAIL_TEMPLATE_NAME")
	@JsonView({ Views.EmailTemplate.class })
	private String name;

	@Column(name = "EMAIL_TEMPLATE_CONTENT")
	@JsonView({ Views.EmailTemplate.class })
	private String content;

	@Column(name = "EMAIL_TEMPLATE_VERSION")
	@JsonView({ Views.EmailTemplate.class })
	private Integer version;

	@Column(name = "EMAIL_TEMPLATE_SUBJECT")
	@JsonView({ Views.EmailTemplate.class })
	private String emailTemplateSubject;

	@Column(name = "IS_DELETED")
	@JsonView({ Views.noView.class })
	private boolean isDeleted;

	@Column(name = "CREATED_BY")
	@JsonView({ Views.EmailTemplatePost.class })
	private String createdBy;

	@Column(name = "CREATED_AT")
	@JsonView({ Views.EmailTemplatePost.class })
	private Instant createdAt;

	@Column(name = "UPDATED_BY")
	@JsonView({ Views.EmailTemplatePut.class })
	private String updatedBy;

	@Column(name = "UPDATED_AT")
	@JsonView({ Views.EmailTemplatePut.class })
	private Instant updatedAt;

	
	public EmailTemplate(EmailTemplate emailTemplate) {
		super();
		this.name = emailTemplate.name;
		this.content = emailTemplate.content;
		this.emailTemplateSubject = emailTemplate.emailTemplateSubject;
		this.isDeleted = emailTemplate.isDeleted;
		this.createdBy = emailTemplate.createdBy;
		this.updatedBy=emailTemplate.updatedBy;
	}

	@PrePersist
	private void prePersist() {
		this.createdAt = Instant.now();
	}

	@PreUpdate
	private void preUpdate() {
		this.updatedAt = Instant.now();
	}

	public EmailTemplate() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public String getEmailTemplateSubject() {
		return emailTemplateSubject;
	}

	public void setEmailTemplateSubject(String emailTemplateSubject) {
		this.emailTemplateSubject = emailTemplateSubject;
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

	public Instant getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Instant createdAt) {
		this.createdAt = createdAt;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Instant getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Instant updatedAt) {
		this.updatedAt = updatedAt;
	}

	@Override
	public String toString() {
		return "EmailTemplate [emailTemplateId=" + id + ", emailTemplateName=" + name + ", emailTemplateContent="
				+ content + ", emailTemplateVersion=" + version + ", emailTemplateSubject=" + emailTemplateSubject
				+ ", isDeleted=" + isDeleted + ", createdBy=" + createdBy + ", createdAt=" + createdAt + ", updatedBy="
				+ updatedBy + ", updatedAt=" + updatedAt + "]";
	}

	
	public String compareTo(EmailTemplate o, EmailTemplate n) {
		StringBuilder string = new StringBuilder();

		if (ConditionCheck.checkValues(n.id, o.id))
			string.append("id--->" + n.id + "--->" + o.id + "::::");

		if (ConditionCheck.checkValues(n.name, o.name))
			string.append("name--->" + n.name + "--->" + o.name + "::::");
		
		if (ConditionCheck.checkValues(n.content, o.content))
			string.append("content--->" + n.content + "--->" + o.content + "::::");
		
		if (ConditionCheck.checkValues(n.version, o.version))
			string.append("version--->" + n.version + "--->" + o.version + "::::");
		
		if (ConditionCheck.checkValues(n.emailTemplateSubject, o.emailTemplateSubject))
			string.append("emailTemplateSubject--->" + n.emailTemplateSubject + "--->" + o.emailTemplateSubject + "::::");
		
		if (ConditionCheck.checkValues(n.isDeleted, o.isDeleted))
			string.append("isDeleted--->" + n.isDeleted + "--->" + o.isDeleted + "::::");
		
		if (ConditionCheck.checkValues(n.createdBy, o.createdBy))
			string.append("createdBy--->" + n.createdBy + "--->" + o.createdBy + "::::");
		
		if (ConditionCheck.checkValues(n.createdAt, o.createdAt))
			string.append("createdAt--->" + n.createdAt + "--->" + o.createdAt + "::::");
		
		if (ConditionCheck.checkValues(n.updatedBy, o.updatedBy))
			string.append("updatedBy--->" + n.updatedBy + "--->" + o.updatedBy + "::::");
		
		if (ConditionCheck.checkValues(n.updatedAt, o.updatedAt))
			string.append("updatedAt--->" + n.updatedAt + "--->" + o.updatedAt + "::::");
		
		
		
		return string.toString();
	}
}
