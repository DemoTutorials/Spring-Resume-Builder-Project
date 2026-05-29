package com.auth.uam.entity;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@Data
@ToString
@Table(name ="conatct_us")
public class ContactUs {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "contact_name")
	private String contactName;

	@Column(name = "subject")
	private String subject;

	@Column(name = "contacted_email", nullable = false)
	private String contactedEmail;
	
	@Column(name = "email_sendto")
	private String emailSendTo;
	
	@Column(name = "describe")
	private String describe;
	
	@Column(name = "IS_DELETED")
	private Boolean isDeleted;

	@Column(name = "CREATED_BY")
	private String createdBy;

	@Column(name = "CREATION_DATE")
	private Instant creationDate;

	@Column(name = "LAST_UPDATE_BY")
	private String lastUpdateBy;

	@Column(name = "LAST_UPDATE_DATE")
	private Instant lastUpdateDate;
	
}
