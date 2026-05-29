package com.avisys.cv.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Data
@NoArgsConstructor
public class AuthToken {

	private String userId;
	private String userName;
	private String name;
	private String email;
	private String role;
	private String accessToken;
	private String refreshToken;

}