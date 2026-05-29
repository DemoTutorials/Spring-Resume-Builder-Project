package com.avisys.cv.proxy;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.avisys.cv.dto.APIEndpoints;
import com.avisys.cv.dto.AuthRequest;
import com.avisys.cv.dto.AuthToken;

@FeignClient(name = "AUTH-SERVICE" )
public interface EndpointConfigService {

	@PostMapping("/auth-service/auth/accessToken")
	public AuthToken authonticatAndGetToken(@RequestBody AuthRequest authRequest);
	
}
