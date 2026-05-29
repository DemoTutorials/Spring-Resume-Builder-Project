package com.auth.uam.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@Data
@ToString
@Table(name = "APIENDPOINTS")
@AllArgsConstructor
@NoArgsConstructor
public class APIEndpoints {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@NotNull
	@Column(name = "method_name")
	private String methodName;

	@NotNull
	@Column(name = "path", unique = true)
	private String path;

	@NotNull
	@Column(name = "controller_name")
	private String controllerName;

	@NotNull
	@Column(name = "service_name")
	private String serviceName;

	@NotNull
	@Column(name = "creation_date")
	private LocalDateTime creationDate;

	@JsonIgnoreProperties({ "apiEndpoints" })
	@OneToOne(mappedBy = "apiEndpoints")
	private Permission permission;
}
