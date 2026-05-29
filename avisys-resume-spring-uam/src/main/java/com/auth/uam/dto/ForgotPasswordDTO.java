package com.auth.uam.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class ForgotPasswordDTO {

	private String userName;
	private String newPassword;
}
