package com.auth.uam.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import com.auth.uam.dto.UserInfo;

@FeignClient(name = "userInfoService", url = "https://api.linkedin.com")
public interface LinkdinUserInfo {

	@GetMapping(value = "/v2/userinfo")
    public UserInfo getUserDetails(@RequestHeader("Authorization") String bearerToken);
}
