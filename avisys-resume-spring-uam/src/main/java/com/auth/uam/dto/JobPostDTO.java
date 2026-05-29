package com.auth.uam.dto;

import java.time.LocalDateTime;

import com.auth.uam.entity.JobPost;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JobPostDTO {

    public JobPostDTO(Long jobId, String jobPostCode, String companyCode, String jobProfile, String description,
			boolean active, String companyName, LocalDateTime lastUpdateDate) {
		this.jobId = jobId;
		this.jobPostCode = jobPostCode;
		this.companyCode = companyCode;
		this.jobProfile = jobProfile;
		this.description = description;
		this.active = active;
		this.companyName = companyName;
		this.lastUpdateDate = lastUpdateDate;
	}
	private Long jobId;
    private String jobPostCode;
    private String companyCode;
    private String jobProfile;
    private String description;
    private boolean active;
    private String companyName;
    private LocalDateTime lastUpdateDate;

    
}
