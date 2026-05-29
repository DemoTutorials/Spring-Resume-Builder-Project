package com.auth.uam.entity;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
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
@Table(name = "ROLE")
public class Roles {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ROLE_ID")
	private Long roleId;

	@Column(name = "ROLE_NAME", unique=true)
	private String roleName;

	@Column(name = "DESCRIPTION")
	private String description;

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

	@ManyToMany(fetch = FetchType.EAGER)
	private List<Permission> permission;

}
