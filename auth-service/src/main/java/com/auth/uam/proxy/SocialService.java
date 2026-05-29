package com.auth.uam.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.auth.uam.dto.LinkdinTokenDto;
import com.auth.uam.dto.LinkedInAccessTokenRequest;
import com.auth.uam.dto.UserInfo;

@FeignClient(name = "socialService", url = "https://www.linkedin.com")
public interface SocialService {
	
	@PostMapping(value = "/oauth/v2/accessToken", consumes = "application/x-www-form-urlencoded")
    public LinkdinTokenDto getAccessToken(@RequestBody LinkedInAccessTokenRequest request);

}