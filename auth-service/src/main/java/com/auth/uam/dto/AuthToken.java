package com.auth.uam.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Data
@NoArgsConstructor
public class AuthToken {

	private Long id;
	private String userId;
	private String userName;
	private String name;
	private String email;
	private String companyName;
	private String companyCode;
	private String role;
	private String accessToken;
	private String refreshToken;
	private boolean isJobApplicant;
	private boolean isNewApplicant;

}