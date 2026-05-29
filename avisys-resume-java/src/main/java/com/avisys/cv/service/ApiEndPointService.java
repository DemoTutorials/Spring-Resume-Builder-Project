package com.avisys.cv.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.avisys.cv.dto.APIEndpoints;
import com.avisys.cv.dto.AuthRequest;
import com.avisys.cv.dto.AuthToken;
import com.avisys.cv.proxy.CompanyConfig;
import com.avisys.cv.proxy.EndpointConfigService;

@Service
public class ApiEndPointService {

	@Value("${adminPassord}")
	private String password;

	@Value("${admin}")
	private String admin;
	
		@Autowired
		EndpointConfigService configuration;
		
		@Autowired
		CompanyConfig configEndpoints;
		
		public void configuration( @RequestBody List<APIEndpoints> endpoints) {
			AuthToken token = configuration.authonticatAndGetToken(new AuthRequest(admin, password));
			configEndpoints.saveEndpoints(endpoints, "Bearer "+token.getAccessToken());
	}
}
