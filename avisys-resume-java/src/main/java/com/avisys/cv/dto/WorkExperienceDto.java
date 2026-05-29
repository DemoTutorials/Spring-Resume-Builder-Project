package com.avisys.cv.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.avisys.cv.exception.ErrorMessages;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Data
@ToString
public class WorkExperienceDto {
	private Long expId;

	private String company;
	
	private String clientName;

	@NotNull(message = ErrorMessages.NotNull)
	@NotBlank(message = ErrorMessages.NotBlank)
	private String position;

	@NotNull(message = ErrorMessages.NotNull)
	@NotBlank(message = ErrorMessages.NotBlank)
	private String startDate;

	private String endDate;

	private String description;

	@NotNull(message = ErrorMessages.NotNull)
	@NotBlank(message = ErrorMessages.NotBlank)
	private String city;

	@NotNull(message = ErrorMessages.NotNull)
	@NotBlank(message = ErrorMessages.NotBlank)
	private String state;

	@NotNull(message = ErrorMessages.NotNull)
	@NotBlank(message = ErrorMessages.NotBlank)
	private String country;
	
	@NotNull(message = ErrorMessages.NotNull)
	@NotBlank(message = ErrorMessages.NotBlank)
	private String domain;
	
	private Double yearsOfExperience;

	public String getLocation() {
		return city + ", " + state;
	}

	public String getDuration() {
		return startDate + " - " + endDate;
	}
}
