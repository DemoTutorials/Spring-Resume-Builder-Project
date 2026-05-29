package com.auth.uam.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auth.uam.dto.AuthRequest;
import com.auth.uam.dto.AuthToken;
import com.auth.uam.proxy.ProxyService;

@Service
public class TokenService {

	@Autowired
	ProxyService proxyService;
	
	public AuthToken getToken() {
		AuthRequest authRequest = new AuthRequest();
		authRequest.setUserName("Admin");
		authRequest.setPassword("Admin@12345");
		return proxyService.authonticatAndGetToken(authRequest);
		
	}
}
