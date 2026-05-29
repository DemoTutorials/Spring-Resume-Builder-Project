package com.auth.uam.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.auth.uam.constant.ErrorMessages;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Data
@ToString
public class UserInvitationDTO {

	private Long id;

	@NotNull(message = ErrorMessages.NotNull)
	@NotBlank(message = ErrorMessages.NotBlank)
	private String firstName;

	private String middleName;

	@NotNull(message = ErrorMessages.NotNull)
	@NotBlank(message = ErrorMessages.NotBlank)
	private String lastName;

	@NotNull(message = ErrorMessages.NotNull)
	@NotBlank(message = ErrorMessages.NotBlank)
	private String email;
	
	private String invitationStatus;

}
