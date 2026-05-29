package com.avisys.microservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.avisys.microservice.entity.EmailTemplate;
import com.avisys.microservice.entity.UserInfo;
import com.avisys.microservice.exception.UserDetailsNotFoundException;
import com.avisys.microservice.model.EmailTemplateVariable;
import com.avisys.microservice.model.UserDetails;
import com.avisys.microservice.repository.EmailTemplateRepository;

@Service
public class EmailTemplateService {

	@Autowired
	private EmailTemplateRepository emailTemplateRepository;
	@Autowired
	UserDetailsService userService;

	public EmailTemplate save(EmailTemplate emailTemplate) throws Exception {
		emailTemplate.setVersion(0);
		if(emailTemplateRepository.countByNameAndVersion(emailTemplate.getName(),emailTemplate.getVersion())>=1) {
			throw new Exception("Conflict in creating template. Template already exists with same Name");
		}
		
		return emailTemplateRepository.save(emailTemplate);
	}

	public EmailTemplate update(EmailTemplate emailTemplate) {
		EmailTemplate existingEmailTemplate = emailTemplateRepository.findById(emailTemplate.getId()).get();
		
		EmailTemplate version = emailTemplateRepository.findTopByNameOrderByVersionDesc(emailTemplate.getName());
		
		EmailTemplate newET=new EmailTemplate(existingEmailTemplate);
		newET.setVersion(version.getVersion()+1);
		return emailTemplateRepository.save(newET);

	}

	public List<EmailTemplate> getAllList() {
		return emailTemplateRepository.findByIsDeleted(false);
	}

	public EmailTemplate getById(Long id) {
		Optional<EmailTemplate> findById = emailTemplateRepository.findById(id);
		if (findById.isPresent()) {
			return findById.get();
		}
		return null;
	}

	public EmailTemplate delete(Long id, String updatedBy) {

		Optional<EmailTemplate> existingEmailTemplate = emailTemplateRepository.findById(id);
		if (existingEmailTemplate.isPresent()) {
			EmailTemplate emailTemplate = existingEmailTemplate.get();
			emailTemplate.setUpdatedBy(updatedBy);
			emailTemplate.setDeleted(true);
			return emailTemplateRepository.save(emailTemplate);
		}
		return null;
	}

	public Page<EmailTemplate> pagingWithSearch(String search, Pageable page) {
		if (ObjectUtils.isEmpty(search)) {
			return emailTemplateRepository.findByIsDeleted(false, page);
		}
		return emailTemplateRepository.findByNameContainingIgnoreCaseAndIsDeleted(search, false, page);
	}
	
	public List<EmailTemplateVariable> getUsersByEmail(List<String> recipients) {

		List<UserDetails> body2 = new ArrayList<>();
		String emailList = recipients.stream().collect(Collectors.joining(","));
		List<UserInfo> listOfuserInfo = userService.getUserByEmail(emailList);

		for (UserInfo userInfoObject : listOfuserInfo) {
			UserDetails userDetailsObj = new UserDetails();
			userDetailsObj.setId(userInfoObject.getId());
			userDetailsObj.setUserId(userInfoObject.getUserId());
			userDetailsObj.setFirstName(userInfoObject.getFirstName());
			userDetailsObj.setLastName(userInfoObject.getLastName());
			userDetailsObj.setEmail(userInfoObject.getEmail());
			userDetailsObj.setContact(userInfoObject.getContact());
			userDetailsObj.setReportsTo(userInfoObject.getReportsTo());
			userDetailsObj.setCreatedBy(userInfoObject.getCreatedBy());
			userDetailsObj.setCreateDate(userInfoObject.getCreateDate());
			userDetailsObj.setUpdatedBy(userInfoObject.getUpdatedBy());
			userDetailsObj.setLastUpdateDate(userInfoObject.getLastUpdateDate());
			userDetailsObj.setDeleted(userInfoObject.getDeleted());
			body2.add(userDetailsObj);
		}

		if (body2.isEmpty())
			throw new UserDetailsNotFoundException(
					"No User details found with recipients as :" + recipients.toString());

		List<EmailTemplateVariable> listofEmailTemplate = new ArrayList<>();
		for (int i = 0; i < body2.size(); i++) {
			UserDetails user = body2.get(i);

			EmailTemplateVariable emailTemplateVariable = new EmailTemplateVariable();
			emailTemplateVariable.setTo(user.getEmail());
			emailTemplateVariable.setCustomerName(user.getFirstName() + " " + user.getLastName());
			emailTemplateVariable.setCustomerContactNumber(user.getContact());
			emailTemplateVariable.setCustomerEmail(user.getEmail());
			listofEmailTemplate.add(emailTemplateVariable);
		}

		return listofEmailTemplate;
	}

}
