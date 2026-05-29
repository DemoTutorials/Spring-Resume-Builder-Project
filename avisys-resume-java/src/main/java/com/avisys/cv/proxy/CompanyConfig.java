package com.avisys.cv.proxy;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.avisys.cv.dto.APIEndpoints;
import com.avisys.cv.dto.UserDetailsDto;
import com.avisys.cv.dto.UserPersonDto;
import com.avisys.cv.entity.User;

@FeignClient(name = "UAM-SERVICE" )
public interface CompanyConfig {

	@GetMapping(value = "/uam-service/user/user-details/{userId}")
	public UserDetailsDto getCompanyDetails( @PathVariable("userId") String userId);
	
	@PostMapping(value = "/uam-service/endpoints/save")
	public ResponseEntity<String> saveEndpoints( @RequestBody List<APIEndpoints> endpoints, @RequestHeader("Authorization") String token);
	
	@PostMapping(value = "/uam-service/user/createUserByHr")
	public User createUserByHR(@RequestBody UserPersonDto userDetails, @RequestHeader("Authorization") String token, @RequestHeader("userId") String userId);
}
