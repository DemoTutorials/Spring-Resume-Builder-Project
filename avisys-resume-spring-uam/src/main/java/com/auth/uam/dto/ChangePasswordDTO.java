package com.auth.uam.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class ChangePasswordDTO {

	private String userName;
	private String oldPassword;
	private String newPassword;
}
