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
public class EducationDto {

	private Long eduId;

	@NotNull(message = ErrorMessages.NotNull)
	@NotBlank(message = ErrorMessages.NotBlank)
	private String qualification;

	private String stream;

	@NotNull(message = ErrorMessages.NotNull)
	@NotBlank(message = ErrorMessages.NotBlank)
	private String institution;

	private Integer completionYear;

	@NotNull(message = ErrorMessages.NotNull)
	@NotBlank(message = ErrorMessages.NotBlank)
	private String city;

	@NotNull(message = ErrorMessages.NotNull)
	@NotBlank(message = ErrorMessages.NotBlank)
	private String state;

	@NotNull(message = ErrorMessages.NotNull)
	@NotBlank(message = ErrorMessages.NotBlank)
	private String country;
	
	private String description;
	
	public String getLocation() {
		return city+", "+state;
	}
}
