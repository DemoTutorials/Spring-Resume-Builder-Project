package com.auth.uam.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.auth.uam.entity.Permission;

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
