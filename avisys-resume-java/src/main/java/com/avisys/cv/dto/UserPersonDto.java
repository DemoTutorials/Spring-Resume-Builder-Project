package com.avisys.cv.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
 
@Getter
@Setter
@Data
@ToString
public class UserPersonDto {
 
	private String userName;
 
	private String firstName;
 
	private String lastName;
 
	private String userType;
 
	private String email;
 
	private String companyName;
 
	private String companyCode;
 
	private String mobileNumber;
	
	private String password;
	
	private String userid;
}
