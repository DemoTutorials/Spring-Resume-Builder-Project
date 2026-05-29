package com.auth.uam.security.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth.uam.dto.AuthRequest;
import com.auth.uam.dto.AuthToken;
import com.auth.uam.dto.PersonDto;
import com.auth.uam.dto.RegistrationDto;
import com.auth.uam.dto.Status;
import com.auth.uam.entity.JobPost;
import com.auth.uam.entity.User;
import com.auth.uam.exception.ResourceNotFoundExceptionGeneric;
import com.auth.uam.proxy.RegistrationService;
import com.auth.uam.proxy.ResumeService;
import com.auth.uam.service.LogInService;

import feign.FeignException;

@Service
public class RegisterService {

	@Autowired
	private LogInService logInService;

	@Autowired
	RegistrationService registraion;
	
	@Autowired
	ResumeService resumeService;

	@Value("${adminPassord}")
	private String password;
		
	@Value("${admin}")
	private String admin;	
	
	public Status Registration(RegistrationDto register) {
		try {
		AuthToken token = logInService.getToken(new AuthRequest(admin, password, null));
		register.setUserName(register.getEmail());
		Status message = registraion.registration(register, "Bearer "+token.getAccessToken());
		return message;
		} catch (FeignException fe) {
	        String errorMessage = fe.contentUTF8();
	        throw new ResourceNotFoundExceptionGeneric(errorMessage);
		}catch (Exception e) {
			throw new ResourceNotFoundExceptionGeneric(e.getMessage());
	    }
	}
	
	public void saveResumeForApplicant(User user, JobPost jobPost) {	
		Set<JobPost> jobPosts = new HashSet<>();
		jobPosts.add(jobPost);
		PersonDto person = new PersonDto();
		person.setFirstName(user.getFirstName());
		person.setLastName(user.getLastName());
		person.setUser(user);
		person.setCompanyCode(user.getCompanyCode());
		person.setEmail(user.getEmail());
		person.setJobPosts(jobPosts);
		resumeService.save(person);
	}
}
