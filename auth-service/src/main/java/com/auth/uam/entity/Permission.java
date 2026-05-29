package com.auth.uam.entity;

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

}
