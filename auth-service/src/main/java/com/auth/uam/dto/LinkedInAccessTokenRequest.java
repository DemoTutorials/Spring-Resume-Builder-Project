package com.auth.uam.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Data
@ToString
public class LinkedInAccessTokenRequest {
	private String grant_type;
    private String code;
    private String redirect_uri;
    private String client_id;
    private String client_secret;
}
