package com.avisys.microservice.proxy;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import com.avisys.microservice.model.UserDetails;

@FeignClient(name = "KEYCLOAK")
public interface UserAccessTokenProxy {

	@GetMapping("/auth/user/user-by-email/{email}")
	public List<UserDetails> listOfUserAndReportee(@RequestHeader("Authorization") String token,
			@PathVariable(name = "email") List<String> email);

//	@PostMapping("/auth/token/accessToken")
//	public AccessTokenResponse accessToken(@RequestBody Credentials cred);

}
