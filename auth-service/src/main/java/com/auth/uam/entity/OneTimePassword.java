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
@Table(name = "OTP")
public class OneTimePassword {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "USER_ID")
	private String userId;
	
	@Column(name = "ONE_TIME_PASSWORD")
	private String oneTimePassword;
	
	@Column(name = "CREATION_DATE")
	private LocalDateTime creationDate;
	
	@Column(name = "EXPIRY_DATE")
	private LocalDateTime expiryDate;
}
