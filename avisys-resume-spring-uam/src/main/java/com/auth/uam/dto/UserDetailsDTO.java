package com.auth.uam.dto;

import java.time.LocalDateTime;
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
public class UserDetailsDTO {

	private Long id;

	private String userId;

	private String userName;

	private String firstName;

	private String middleName;

	private String lastName;

	private String userType;

	private String email;

	private String mobileNumber;

	private String companyName;

	private String companyCode;

	private Boolean isDeleted;

}
