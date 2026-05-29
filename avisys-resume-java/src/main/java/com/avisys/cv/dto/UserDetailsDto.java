package com.avisys.cv.dto;

import java.util.Set;

import com.avisys.cv.entity.JobPost;
import com.avisys.cv.entity.User;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Data
@ToString
public class UserDetailsDto {

	private String userId;

	private String userName;

	private String firstName;

	private String lastName;

	private String userType;

	private String email;

	private String companyName;

	private String companyCode;

	private String mobileNumber;
	
	private User user;
	
	private Set<JobPost> jobPosts;
	
}
