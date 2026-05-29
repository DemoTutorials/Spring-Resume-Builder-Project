package com.auth.uam.dto;


import java.util.List;

import com.auth.uam.entity.Roles;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Data
@ToString
public class RegistrationDto {

	private String firstName;

	private String lastName;

	private String email;

	private String password;
	
	private String userName;
	
	private String userId;
	
	private String jobPostCode;
	
	private List<Roles> role;

}
