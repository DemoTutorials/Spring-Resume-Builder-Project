package com.auth.uam.dto;

import java.util.Set;

import com.auth.uam.entity.JobPost;
import com.auth.uam.entity.User;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
 
@Getter
@Setter
@Data
@ToString
public class PersonDto {
 
	private User user;
	 
	private String firstName;
 
	private String lastName;
 
	private String email;

	private String companyCode;
	
	private Set<JobPost> jobPosts;
}
