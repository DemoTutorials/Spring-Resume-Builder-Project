package com.avisys.email.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.history.RevisionMetadata.RevisionType;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.avisys.email.Repository.EmailTemplateRepository;
import com.avisys.email.entity.EmailTemplate;
import com.avisys.email.enums.EntityFlag;

@Service
public class EmailTemplateService {

	@Autowired
	private EmailTemplateRepository emailTemplateRepository;
	@Autowired
	AuditService auditService;

	public EmailTemplate save(EmailTemplate emailTemplate) throws Exception {
		emailTemplate.setVersion(0);
		if(emailTemplateRepository.countByNameAndVersion(emailTemplate.getName(),emailTemplate.getVersion())>=1) {
			throw new Exception("Conflict in creating template. Template already exists with same Name");
		}
		
		EmailTemplate saveEmailTemplate = emailTemplateRepository.save(emailTemplate);
		auditService.auditing(EntityFlag.EmailTemplate.getName(), RevisionType.INSERT.name(), saveEmailTemplate.getId(),
				saveEmailTemplate.getCreatedBy(), null);
		return saveEmailTemplate;
	}

	public EmailTemplate update(EmailTemplate emailTemplate) throws CloneNotSupportedException {
		EmailTemplate existingEmailTemplate = emailTemplateRepository.findById(emailTemplate.getId()).get();
		EmailTemplate oldEmailTemplate = (EmailTemplate) existingEmailTemplate.clone();
		EmailTemplate version = emailTemplateRepository.findTopByNameOrderByVersionDesc(emailTemplate.getName());
		
		EmailTemplate newET=new EmailTemplate(existingEmailTemplate);
		newET.setVersion(version.getVersion()+1);
		EmailTemplate saveEmailTemplate =  emailTemplateRepository.save(newET);
		auditService.auditing(oldEmailTemplate, saveEmailTemplate, EntityFlag.EmailTemplate.getName(), RevisionType.UPDATE.name(),
				saveEmailTemplate.getId(), saveEmailTemplate.getUpdatedBy());
		return saveEmailTemplate;
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

	public EmailTemplate delete(Long id, String updatedBy) throws CloneNotSupportedException {

		Optional<EmailTemplate> existingEmailTemplate = emailTemplateRepository.findById(id);
		if (existingEmailTemplate.isPresent()) {
			EmailTemplate emailTemplate = existingEmailTemplate.get();
			EmailTemplate oldEmailTemplate = (EmailTemplate) emailTemplate.clone();
			emailTemplate.setUpdatedBy(updatedBy);
			emailTemplate.setDeleted(true);
			EmailTemplate saveEmailTemplate = emailTemplateRepository.save(emailTemplate);
			auditService.auditing(oldEmailTemplate, saveEmailTemplate, EntityFlag.EmailTemplate.getName(), RevisionType.DELETE.name(),
					saveEmailTemplate.getId(), saveEmailTemplate.getUpdatedBy());
			return saveEmailTemplate;
		}
		return null;
	}

	public Page<EmailTemplate> pagingWithSearch(String search, Pageable page) {
		if (ObjectUtils.isEmpty(search)) {
			return emailTemplateRepository.findByIsDeleted(false, page);
		}
		return emailTemplateRepository.findByNameContainingIgnoreCaseAndIsDeleted(search, false, page);
	}
	
//	public List<UserInfo> getUsersByEmail(List<String> recipients) {
//
//		String emailList = recipients.stream().collect(Collectors.joining(","));
//		List<UserInfo> listOfuserInfo = userService.getUserByEmail(emailList);
//
//
//		if (listOfuserInfo.isEmpty())
//			throw new UserDetailsNotFoundException(
//					"No User details found with recipients as :" + recipients.toString());
//
//		return listOfuserInfo;
//	}

}
