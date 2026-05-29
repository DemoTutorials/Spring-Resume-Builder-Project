package com.auth.uam.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.auth.uam.dto.MailDTO;
import com.auth.uam.dto.MailNotification;
import com.auth.uam.proxy.EmailProxyService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class MailService {

	@Autowired
	EmailProxyService emailProxyService;
	
	public MailNotification sendMail(MailDTO maildto, List<MultipartFile> file, String name, String senderName) {
		String sendMail = "send-email";
		
		maildto.setEmailTemplate(2+"");
		
		maildto.setReferenceObjectType("USER-INVITATION-SERVICE");
		maildto.setJobName("mail-service-user-service");
		Map<String, String> jobData = new HashMap<>();
		jobData.put("senderName", senderName );
		jobData.put("name", name );
		jobData.put("url", maildto.getEmailBody() );
		maildto.setJobData(jobData);
		maildto.setOwner(maildto.getCreatedBy());

		ObjectMapper newObject = new ObjectMapper();
		try {
			String writeValueAsString = newObject.writeValueAsString(maildto);
			return emailProxyService.create(writeValueAsString, file, sendMail);
		} catch (JsonProcessingException e) {

			e.printStackTrace();
			return null;
		}
	}
	
	public MailNotification sendMailToUser(MailDTO maildto, List<MultipartFile> file, String name, String senderName, String password) {
		String sendMail = "send-email";
		
		maildto.setEmailTemplate(3+"");
		
		maildto.setReferenceObjectType("USER-CREATION-SERVICE");
		maildto.setJobName("mail-service-user-create");
		Map<String, String> jobData = new HashMap<>();
		jobData.put("senderName", senderName );
		jobData.put("name", name );
		jobData.put("url", maildto.getEmailBody());
		jobData.put("password", password);
		jobData.put("userName", maildto.getSendTO());
		maildto.setJobData(jobData);
		maildto.setOwner(maildto.getCreatedBy());

		ObjectMapper newObject = new ObjectMapper();
		try {
			String writeValueAsString = newObject.writeValueAsString(maildto);
			return emailProxyService.create(writeValueAsString, file, sendMail);
		} catch (JsonProcessingException e) {

			e.printStackTrace();
			return null;
		}
	}
	
	public MailNotification sendMailToHR(MailDTO maildto, List<MultipartFile> file, String name, String senderName) {
		String sendMail = "send-email";
		
		maildto.setEmailTemplate(6+"");
		
		maildto.setReferenceObjectType("JOB-APPLICATION-LINK-SERVICE");
		maildto.setJobName("mail-service-job-post-code-service");
		Map<String, String> jobData = new HashMap<>();
		jobData.put("senderName", senderName );
		jobData.put("name", name );
		jobData.put("url", maildto.getEmailBody() );
		maildto.setJobData(jobData);
		maildto.setOwner(maildto.getCreatedBy());

		ObjectMapper newObject = new ObjectMapper();
		try {
			String writeValueAsString = newObject.writeValueAsString(maildto);
			return emailProxyService.create(writeValueAsString, file, sendMail);
		} catch (JsonProcessingException e) {

			e.printStackTrace();
			return null;
		}
	}
}
