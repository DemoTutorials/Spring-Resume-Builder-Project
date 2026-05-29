package com.auth.uam.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Data
@ToString
public class LinkdinTokenDto {

	private String access_token;
    private Long expires_in;
    private String scope;
    private String token_type;
    private String id_token;
    
}
