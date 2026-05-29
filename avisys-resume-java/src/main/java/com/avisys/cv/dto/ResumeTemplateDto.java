package com.avisys.cv.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Data
@ToString
public class ResumeTemplateDto {

	private PersonDto person;

	private List<EducationDto> educationList = new ArrayList<>();

	private List<CertificationDto> certificationList = new ArrayList<>();

	private List<LanguageDto> languageList = new ArrayList<>();

	private List<SkillDto> skillsList = new ArrayList<>();

	private List<WorkExperienceDto> experienceList = new ArrayList<>();
	
	private OtherSkillDto otherSkillsList;
	
	private CompanyDetailsDto company;
}
