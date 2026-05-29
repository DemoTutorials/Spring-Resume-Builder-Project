package com.auth.uam.dto;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.auth.uam.constant.ErrorMessages;
import com.auth.uam.entity.JobPost;
import com.auth.uam.entity.Roles;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Data
@ToString
public class UserDTO {

	private Long id;

	private String userId;
	
	@NotNull(message = ErrorMessages.NotNull)
	@NotBlank(message = ErrorMessages.NotBlank)
	private String userName;

	@NotNull(message = ErrorMessages.NotNull)
	@NotBlank(message = ErrorMessages.NotBlank)
	private String firstName;

	private String middleName;

	@NotNull(message = ErrorMessages.NotNull)
	@NotBlank(message = ErrorMessages.NotBlank)
	private String lastName;

	@NotNull(message = ErrorMessages.NotNull)
	@NotBlank(message = ErrorMessages.NotBlank)
	private String userType;

	@NotNull(message = ErrorMessages.NotNull)
	@NotBlank(message = ErrorMessages.NotBlank)
	private String email;

	private String mobileNumber;

	private Boolean isDeleted;

	private String createdBy;

	private LocalDateTime creationDate;

	private String lastUpdateBy;

	private LocalDateTime lastUpdateDate;
	
	private String password;
	
	private String companyName;
	
	private String companyCode;
	
	private List<Roles> role;
	
	private boolean isJobApplicant;
}
