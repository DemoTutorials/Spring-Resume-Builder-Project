package com.avisys.cv.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.avisys.cv.entity.Certification;
import com.avisys.cv.entity.Education;
import com.avisys.cv.entity.Language;
import com.avisys.cv.entity.OtherSkills;
import com.avisys.cv.entity.Skill;
import com.avisys.cv.entity.WorkExperience;

public interface PersonProjection {
    Long getPersonId();
    String getProfilePic();
    String getFirstName();
    String getLastName();
    String getSummary();
    String getCity();
    String getState();
    String getCountry();
    String getEmail();
    String getPhone();
    String getLinkedin();
    LocalDateTime getLastUpdateDate();
    String getCompanyCode();
    String getCompanyName();
    String getJobPostCode();
    Float getTotalExperience();
    String getUploadFile();
    List<Education> getEducationList();
    List<Certification> getCertificationList();
    List<Language> getLanguageList();
    List<Skill> getSkillsList();
    OtherSkills getOtherSkillsList();
    List<WorkExperience> getExperienceList();
}

