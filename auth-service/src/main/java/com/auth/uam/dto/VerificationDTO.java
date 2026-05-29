package com.auth.uam.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Data
public class VerificationDTO {

	private String userName;

	private String otp;
}
