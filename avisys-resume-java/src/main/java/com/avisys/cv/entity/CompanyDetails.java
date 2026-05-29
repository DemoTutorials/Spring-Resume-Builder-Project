package com.avisys.cv.entity;


import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@Data
@ToString
@Table(name = "company_details")
public class CompanyDetails {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "company_id")
    private Long companyId;

    @Column(name = "company_code")
    private String companyCode;
    
    @Column(name = "company_name")
    private String companyName;

    @Column(name = "company_logo")
    private String companyLogo;

    @Column(name = "water_mark")
    private String waterMark;
    
    @Column(name = "show_partial_name")
    private boolean partialName;
    
    @Column(name = "show_contact_number")
    private boolean showContact;
    
    @Column(name = "show_education")
    private boolean showEducation;
    
    @Column(name = "footer")
    private String footer;
    
    @Column(name = "show_profile_pic")
    private boolean showProfilePicture;
    
    @Column(name = "show_company_name")
    private boolean showCompanyName;
    
	@Column(name = "is_deleted")
	private Boolean isDeleted;
    
    @Column(name = "created_by")
	private String createdBy;

	@Column(name = "creation_date")
	private LocalDateTime creationDate;

	@Column(name = "last_update_by")
	private String lastUpdateBy;

	@Column(name = "last_update_date")
	private LocalDateTime lastUpdateDate;
	
}
