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

	private String userId;
	private String userName;
	private String name;
	private String email;
    private String accessToken;

   

}