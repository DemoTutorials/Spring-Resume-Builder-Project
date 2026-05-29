package com.auth.uam.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth.uam.dto.AuthRequest;
import com.auth.uam.dto.AuthToken;
import com.auth.uam.security.service.JwtService;
import com.auth.uam.service.LogInService;
import com.sun.istack.NotNull;

import lombok.NonNull;

@RestController
@RequestMapping("auth-token")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class LogInController {

	/* validate user for token */
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	LogInService logInService;

	@PostMapping("accessToken")
	public ResponseEntity<AuthToken> authonticatAndGetToken(@RequestBody AuthRequest authRequest) {

		/* validate user for token */
		Authentication authenticate = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(authRequest.getUserName(), authRequest.getPassword()));

		if (authenticate.isAuthenticated()) {
			/* just an entity that can be authenticated */
			SecurityContextHolder.getContext().setAuthentication(authenticate);
			AuthToken authToken = logInService.getToken(authRequest);
			return new ResponseEntity<>(authToken, HttpStatus.OK);
		} else {

			throw new UsernameNotFoundException("Invalid User Request!");
		}

	}
	
	@PostMapping("reset-password-token-generation")
	public ResponseEntity<String> getTokenForResetPassword(@NotNull String userName, @NotNull  String email) {
		
		String tokenGeneration = logInService.getTokenForResetPassword(userName,email);

		return new ResponseEntity<>(tokenGeneration, HttpStatus.OK);
	}
	
	@PostMapping("reset-password")
	public ResponseEntity<String> resetPassword( @NotNull String newPassword,@NotNull  String key) {
		
		String resetPassword = logInService.resetPassword(newPassword,key);

		return new ResponseEntity<>(resetPassword, HttpStatus.OK);
	}

}
