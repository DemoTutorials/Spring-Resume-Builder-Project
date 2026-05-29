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
@Table(name = "PASSWORD_RESET_TOKEN")
public class PasswordResetToken {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	@Column(name = "USER_ID")
	private String userId;

	@Column(name = "PASSWORD_TOKEN")
	private String passwordToken;

	@Column(name = "CREATION_DATE_TIME")
	private LocalDateTime creationDateTime;

	@Column(name = "EXPIRY_DATE_TIME")
	private LocalDateTime expiryDateTime;

}
