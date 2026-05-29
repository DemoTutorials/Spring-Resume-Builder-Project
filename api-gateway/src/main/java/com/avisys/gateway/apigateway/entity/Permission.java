package com.avisys.gateway.apigateway.entity;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@Data
@ToString
@Table(name ="PERMISSION")
public class Permission {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PERMISSION_ID")
	private Long permissionId;

	@Column(name = "PERMISSION_NAME")
	private String permissionName;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "CREATION_DATE")
	private LocalDateTime creationDate;

	@JsonIgnoreProperties({ "permission" })
	@OneToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "id")
	private APIEndpoints apiEndpoints;
}
