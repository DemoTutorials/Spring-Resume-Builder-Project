package com.auth.uam.service;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.auth.uam.dto.AcceptInvitationDTO;
import com.auth.uam.dto.MailDTO;
import com.auth.uam.dto.MailNotification;
import com.auth.uam.dto.Status;
import com.auth.uam.entity.ContactUs;
import com.auth.uam.entity.User;
import com.auth.uam.proxy.ProxyService;
import com.auth.uam.proxy.RegistrationService;
import com.auth.uam.repository.ContactUsRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class UserInvitationService {
	
	@Autowired
	RegistrationService registrationService;
	
	@Autowired
	ContactUsRepository contactUsRepository;
	
	@Autowired
	MailService mailService;
	
	@Value("${admin}")
	private String admin;
	
	public Status acceptUserInvitation(AcceptInvitationDTO acceptInvitationDTO) {
		Status acceptUserInvitation = registrationService.acceptUserInvitation(acceptInvitationDTO);
		 
		if (acceptUserInvitation.getStatusCode().equals("200")) {
			return acceptUserInvitation;
		} else {
			throw new PersistenceException(acceptUserInvitation.getMessage());
		}
	}
	
	public Status createContactUs(ContactUs contactUs) {
		contactUs.setCreatedBy("user");
		contactUs.setLastUpdateBy("user");
		contactUs.setCreationDate(Instant.now());
		contactUs.setLastUpdateDate(Instant.now());
		contactUs.setIsDeleted(false);
		contactUs.setEmailSendTo(admin);
		ContactUs contactedUser = contactUsRepository.save(contactUs);
		mailService.saveMailForContactedUser(contactedUser);
		mailService.saveMailForAdmin(contactedUser);
		return new Status("Contact Registred Successful");
	}
	


}