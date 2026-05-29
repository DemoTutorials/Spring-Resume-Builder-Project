package com.auth.uam.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@Data
@ToString
@Table(name = "USERS_INVITATION")
public class UserInvitation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "FIRST_NAME", nullable = false)
	private String firstName;
	
	@Column(name = "MIDDLE_NAME")
	private String middleName;

	@Column(name = "LAST_NAME", nullable = false)
	private String lastName;

	@Column(name = "EMAIL", nullable = false)
	private String email;
	
	@Column(name = "MOBILE_NUMBER")
	private String mobileNumber;

	@Column(name = "company_name")
	private String companyName;
	
	@Column(name = "company_code")
	private String companyCode;
	
	@Column(name = "INVITATION_CODE")
	private String invitationCode;
	
	@Column(name = "INVITATION_STATUS")
	private String invitationStatus;
	
	@Column(name = "IS_DELETED")
	private Boolean isDeleted;

	@Column(name = "CREATED_BY")
	private String createdBy;

	@Column(name = "CREATION_DATE")
	private LocalDateTime creationDate;
	
	@Column(name = "updated_DATE")
	private LocalDateTime updatedDate;
	
	
}
