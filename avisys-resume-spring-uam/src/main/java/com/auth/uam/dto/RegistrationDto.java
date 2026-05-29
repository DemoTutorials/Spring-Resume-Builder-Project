package com.auth.uam.dto;


import java.time.LocalDateTime;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.auth.uam.constant.ErrorMessages;
import com.auth.uam.constant.Role;
import com.auth.uam.constant.UserType;
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

	private Long id;
	
	@NotNull(message = ErrorMessages.NotNull)
	@NotBlank(message = ErrorMessages.NotBlank)
	private String firstName;

	@NotNull(message = ErrorMessages.NotNull)
	@NotBlank(message = ErrorMessages.NotBlank)
	private String lastName;

	private UserType userType;
	
	private String userId;

	@NotNull(message = ErrorMessages.NotNull)
	@NotBlank(message = ErrorMessages.NotBlank)
	private String email;
	
	@NotNull(message = ErrorMessages.NotNull)
	@NotBlank(message = ErrorMessages.NotBlank)
	private String userName;
	
	private String password;
	
	private String jobPostCode;
	
	private List<Roles> role;
	
	private Boolean isDeleted;

	private String createdBy;

	private LocalDateTime creationDate;

	private String lastUpdateBy;

	private LocalDateTime lastUpdateDate;

}
