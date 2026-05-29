package com.avisys.microservice.entity;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(schema = "crm",name = "ERROR_LOGS")
@SequenceGenerator(name = "error_logs_id_seq", sequenceName = "error_logs_id_seq", allocationSize = 1)
public class ErrorLogs {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "error_logs_id_seq")
	private Long id;
	
	@Column(name = "MODULE")
	private String module;
	
	@Column(name = "MESSAGE")
	private String message;

	@Column(name = "DETAILED_DESCRIPTION")
	private String detailedDescription;
	
	@Column(name = "DATE_AT")
	private Instant dateAt;
}
