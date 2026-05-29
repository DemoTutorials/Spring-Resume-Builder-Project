package com.avisys.cv.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
public class CertificationDto {
	private Long certId;

	@NotNull(message = ErrorMessages.NotNull)
	@NotBlank(message = ErrorMessages.NotBlank)
	private String certificationName;

	private String issuingOrganization;

	private String issueDate;

}
