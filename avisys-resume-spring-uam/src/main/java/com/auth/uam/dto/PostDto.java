package com.auth.uam.dto;

import java.time.LocalDateTime;

public interface PostDto {
	Long getJobId();
	
	Long getPersonId();

	String getJobPostCode();

	String getCompanyCode();

	String getJobProfile();

	String getDescription();

	boolean isActive();

	String getCompanyName();

	LocalDateTime getLastUpdateDate();
}