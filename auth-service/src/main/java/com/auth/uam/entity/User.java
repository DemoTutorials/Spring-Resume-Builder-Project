package com.auth.uam.entity;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
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
@Table(name = "USERS")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "USER_ID")
	private String userId;

	@Column(name = "USER_NAME", nullable = false, unique = true)
	private String userName;

	@Column(name = "PASSWORD", nullable = false)
	private String password;

	@Column(name = "FIRST_NAME", nullable = false)
	private String firstName;

	@Column(name = "MIDDLE_NAME")
	private String middleName;

	@Column(name = "LAST_NAME", nullable = false)
	private String lastName;

	@Column(name = "USER_TYPE", nullable = false)
	private String userType;

	@Column(name = "EMAIL", nullable = false, unique = true)
	private String email;

	@Column(name = "MOBILE_NUMBER", nullable = false, unique = true)
	private String mobileNumber;

	@Column(name = "authentication_attempt_failed")
	private Long authenticationAttemptFailed;

	@Column(name = "authentication_attempt_time")
	private LocalDateTime authenticationAttemptTime;

	@Column(name = "company_name")
	private String companyName;

	@Column(name = "company_code")
	private String companyCode;

	@Column(name = "IS_DELETED")
	private Boolean isDeleted;

	@Column(name = "CREATED_BY")
	private String createdBy;

	@Column(name = "CREATION_DATE")
	private LocalDateTime creationDate;

	@Column(name = "LAST_UPDATE_BY")
	private String lastUpdateBy;

	@Column(name = "LAST_UPDATE_DATE")
	private LocalDateTime lastUpdateDate;

	@Column(name = "LAST_UPDATE_PASSWORD_DATE")
	private LocalDateTime lastUpdatePasswordDate;

	@ManyToMany(fetch = FetchType.EAGER)
	private List<Roles> role;
	
	@Column(name = "is_job_applicant")
	private boolean isJobApplicant = false;

}
