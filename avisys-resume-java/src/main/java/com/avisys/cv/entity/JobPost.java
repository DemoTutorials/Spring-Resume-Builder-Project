package com.avisys.cv.entity;

import java.time.LocalDateTime;

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
@Table(name = "job_post")
public class JobPost {

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		@Column(name = "job_id")
		private Long jobId;

		@Column(name = "job_post_code", unique=true)
		private String jobPostCode;

		@Column(name = "company_code")
		private String companyCode;
		
		@Column(name = "job_profile")
		private String jobProfile;
		
		@Column(name = "description")
		private String description;
		
		@Column(name = "active")
		private boolean active;

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

	}

