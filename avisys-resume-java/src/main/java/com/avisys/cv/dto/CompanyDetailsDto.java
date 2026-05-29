package com.avisys.cv.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@Data
@ToString
public class CompanyDetailsDto {

	private Long companyId;

    private String companyCode;

    private String companyLogo;

    private String waterMark;

    private String companyName;

    private boolean partialName;
    
    private boolean showEducation;
    
    private String footer;

    private boolean showContact;
    
    private boolean showProfilePicture;
    
    private boolean showCompanyName;
}
