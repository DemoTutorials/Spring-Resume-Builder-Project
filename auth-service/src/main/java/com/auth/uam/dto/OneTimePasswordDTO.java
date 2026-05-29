package com.auth.uam.dto;

import java.time.LocalDateTime;

import javax.persistence.Column;

public interface OneTimePasswordDTO {

	Long getId();

	String getUserId();

	String getOneTimePassword();

	LocalDateTime getCreationDate();

	LocalDateTime getExpiryDate();
}
