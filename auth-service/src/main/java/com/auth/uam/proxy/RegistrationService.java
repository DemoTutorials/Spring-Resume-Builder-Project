package com.auth.uam.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.auth.uam.dto.AcceptInvitationDTO;
import com.auth.uam.dto.RegistrationDto;
import com.auth.uam.dto.Status;


@FeignClient(name = "UAM-SERVICE" )
public interface RegistrationService {

	
	@PostMapping(value = "/uam-service/user/register")
	public Status registration(@RequestBody RegistrationDto register, @RequestHeader("Authorization") String token);
	
	@PostMapping(value = "/uam-service/user-invitation/accept-user-invitation")
	public Status acceptUserInvitation(@RequestBody AcceptInvitationDTO acceptInvitationDTO);
}
