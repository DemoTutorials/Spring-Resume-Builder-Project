package com.auth.uam.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Data
@ToString
public class AcceptInvitationDTO {

	private String token;
	private String password;
}