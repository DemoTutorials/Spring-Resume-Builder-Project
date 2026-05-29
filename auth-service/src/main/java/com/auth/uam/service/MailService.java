package com.auth.uam.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.auth.uam.dto.MailDTO;
import com.auth.uam.dto.MailNotification;
import com.auth.uam.entity.ContactUs;
import com.auth.uam.proxy.ProxyService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class MailService {
	
	@Autowired
	ProxyService proxyService;

	@Value("${admin}")
	private String admin;
	
	public MailNotification sendMail(MailDTO maildto, List<MultipartFile> file) {
		String sendMail = "send-email";
		
		maildto.setEmailTemplate(1+"");
		
		maildto.setReferenceObjectType("AUTH-SERVICE");
		maildto.setJobName("mail-service-auth-service");
		Map<String, String> jobData = new HashMap<>();
		
		jobData.put("text", maildto.getEmailBody() );
		maildto.setJobData(jobData);
		maildto.setOwner(maildto.getCreatedBy());

		ObjectMapper newObject = new ObjectMapper();
		try {
			String writeValueAsString = newObject.writeValueAsString(maildto);
			return proxyService.create(writeValueAsString, file, sendMail);
		} catch (JsonProcessingException e) {

			e.printStackTrace();
			return null;
		}

	}
	
	public void saveMailForAdmin(ContactUs contactedUser) {
		List<MultipartFile> file = null;
		MailDTO mailDTO = new MailDTO();
		mailDTO.setSendTO(admin);
		mailDTO.setReferenceId(contactedUser.getId() + "");
		String senderName = "Build Resume Contact Form ";
		sendMailToAdmin(mailDTO, file, contactedUser.getContactName(), senderName, contactedUser.getSubject() , contactedUser.getContactedEmail(), contactedUser.getDescribe());
	}
	
	public MailNotification sendMailToAdmin(MailDTO maildto, List<MultipartFile> file, String name, String senderName, String subject, String contactEmail, String describe) {
		String sendMail = "send-email";
		
		maildto.setEmailTemplate(5+"");
		
		maildto.setReferenceObjectType("ADMIN-NOTIFICATION-SERVICE");
		maildto.setJobName("mail-service-admin-notify");
		Map<String, String> jobData = new HashMap<>();
		jobData.put("senderName", senderName );
		jobData.put("name", name );
		jobData.put("subject", subject );
		jobData.put("contactEmail", contactEmail);
		jobData.put("describe", describe);
		maildto.setJobData(jobData);
		maildto.setOwner(maildto.getCreatedBy());

		ObjectMapper newObject = new ObjectMapper();
		try {
			String writeValueAsString = newObject.writeValueAsString(maildto);
			return proxyService.create(writeValueAsString, file, sendMail);
		} catch (JsonProcessingException e) {

			e.printStackTrace();
			return null;
		}
	}
	
	public void saveMailForContactedUser(ContactUs contactedUser) {
		List<MultipartFile> file = null;
		MailDTO mailDTO = new MailDTO();
		mailDTO.setSendTO(contactedUser.getContactedEmail());
		mailDTO.setReferenceId(contactedUser.getId() + "");
		String senderName = "Build Resume Team";
		sendMailToContactedUser(mailDTO, file, contactedUser.getContactName(), senderName, contactedUser.getSubject() , contactedUser.getContactedEmail(), contactedUser.getDescribe());
	}
	
	public MailNotification sendMailToContactedUser(MailDTO maildto, List<MultipartFile> file, String name, String senderName, String subject, String contactEmail, String describe) {
		String sendMail = "send-email";
		
		maildto.setEmailTemplate(4+"");
		
		maildto.setReferenceObjectType("USER-CONTACT-SERVICE");
		maildto.setJobName("mail-service-user-contact");
		Map<String, String> jobData = new HashMap<>();
		jobData.put("senderName", senderName );
		jobData.put("name", name );
		jobData.put("subject", subject );
		jobData.put("contactEmail", contactEmail);
		jobData.put("describe", describe);
		maildto.setJobData(jobData);
		maildto.setOwner(maildto.getCreatedBy());

		ObjectMapper newObject = new ObjectMapper();
		try {
			String writeValueAsString = newObject.writeValueAsString(maildto);
			return proxyService.create(writeValueAsString, file, sendMail);
		} catch (JsonProcessingException e) {

			e.printStackTrace();
			return null;
		}
	}
}
