package com.avisys.cv.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Data
@ToString
public class DataTableDto {

	private Long personId;

	private String firstName;

	private String lastName;

	private String email;
	
	private String companyName;
	
	private String jobPostCode;
	
	private String totalExperience;

	private List<SkillDto> skillsList = new ArrayList<>();

	private OtherSkillDto otherSkillsList;
	
	private LocalDateTime lastUpdateDate;
	
	private List<WorkExperienceDto> experienceList = new ArrayList<>();
	
	public String getName() {
		return firstName + " " + lastName;
	}

	public String getSkills() {
		StringBuilder skillsAsString = new StringBuilder();
		for (SkillDto skill : skillsList) {
			skillsAsString.append(skill.getSkillName()).append(", ");
		}
		if (otherSkillsList != null) {
			skillsAsString.append(otherSkillsList.getOtherSkillName()).append(", ");
		}
		if (!skillsList.isEmpty() || (otherSkillsList != null)) {
			skillsAsString.setLength(skillsAsString.length() - 2);
		}
		return skillsAsString.toString();
	}

	public String getDomain() {
	    Set<String> uniqueDomains = new HashSet<>();
	    for (WorkExperienceDto experienceDto : experienceList) {
	        uniqueDomains.add(experienceDto.getDomain());
	    }
	    StringBuilder domains = new StringBuilder();
	    for (String domain : uniqueDomains) {
	        domains.append(domain).append(", ");
	    }
	    if (domains.length() > 0) {
	        domains.setLength(domains.length() - 2);
	    }
	    return domains.toString();
	}
	
	public ButtonForDataTable[] getAction() {
		ButtonForDataTable btn = new ButtonForDataTable();
		btn.setColor("#3c8dbc");
		btn.setLabel("Download");
 
		ButtonForDataTable[] btnList = new ButtonForDataTable[1];
		btnList[0] = btn;
		return btnList;
	}
}
