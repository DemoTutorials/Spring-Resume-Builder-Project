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
public class SkillDto {

	private Long skillId;

	@NotNull(message = ErrorMessages.NotNull)
	@NotBlank(message = ErrorMessages.NotBlank)
	private String skillName;

}
