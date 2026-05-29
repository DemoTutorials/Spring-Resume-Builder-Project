package com.auth.uam.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth.uam.dto.LinkdinTokenDto;
import com.auth.uam.dto.LinkedInAccessTokenRequest;
import com.auth.uam.dto.UserInfo;
import com.auth.uam.proxy.LinkdinUserInfo;
import com.auth.uam.proxy.SocialService;

@Service
public class LinkedInAuthService {

	@Value("${linkedin.client.id}")
	private String clientId;

	@Value("${linkedin.client.secret}")
	private String clientSecret;

	@Value("${linkedin.redirect.uri}")
	private String redirectUri;
	
	@Value("${linkedin.authorization.grand_type}")
	private String grandType;
	
    @Autowired
    private SocialService socialService;
    
    @Autowired
    private LinkdinUserInfo userInfo;

    public LinkdinTokenDto fetchAccessToken(String code) {
        LinkedInAccessTokenRequest request = new LinkedInAccessTokenRequest();
        request.setCode(code);
        request.setGrant_type(grandType);
        request.setRedirect_uri(redirectUri);
        request.setClient_id(clientId);
        request.setClient_secret(clientSecret);

        LinkdinTokenDto tokenDto = socialService.getAccessToken(request);
        return tokenDto;
    }
    
    public UserInfo getUserInfo(String accessToken) {
        String bearerToken = "Bearer " + accessToken;
        return userInfo.getUserDetails(bearerToken);
	}
    
}
