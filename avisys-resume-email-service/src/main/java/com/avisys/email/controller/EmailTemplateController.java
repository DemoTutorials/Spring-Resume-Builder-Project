package com.avisys.email.controller;

import java.util.List;

import javax.annotation.security.RolesAllowed;

import org.apache.commons.lang.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.avisys.email.dto.ErrorResponse;
import com.avisys.email.entity.EmailTemplate;
import com.avisys.email.exception.EmailTemplateNotFoundException;
import com.avisys.email.service.EmailTemplateDetails;
import com.avisys.email.service.EmailTemplateService;
import com.avisys.email.views.Views;
import com.fasterxml.jackson.annotation.JsonView;

@RestController
@RequestMapping("/masters")
@RolesAllowed("admin")
public class EmailTemplateController {

	private Logger logger = LoggerFactory.getLogger(EmailTemplateController.class);

	@Autowired
	private EmailTemplateService emailTemplateService;
	@Autowired
	private EmailTemplateDetails emailTemplateDetails;

	@GetMapping("/emailTemplatePaging")
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@JsonView(Views.EmailTemplate.class)
	public ResponseEntity<Page<EmailTemplate>> getAllCommonMasterParent(@Nullable String name,@PageableDefault(sort={"createdAt"},direction = Direction.DESC) Pageable pageable) {
		Page<EmailTemplate> parentEmailTemplatePagination = emailTemplateService.pagingWithSearch(name, pageable);
		return new ResponseEntity<>(parentEmailTemplatePagination, HttpStatus.OK);

	}
	
	@GetMapping("/template-by-id/{id}")
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	public ResponseEntity<String> getEmailTemplateContentById(@PathVariable String id) {
		return new ResponseEntity<>(emailTemplateDetails.getTemplateData(id), HttpStatus.OK);

	}

	@GetMapping("/emailTemplate/{id}")
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@JsonView(Views.EmailTemplate.class)
	public ResponseEntity<EmailTemplate> fetchCommonMasterById(@PathVariable Long id) {

		EmailTemplate emailTemplate = emailTemplateService.getById(id);

		if (emailTemplate != null) {
			return new ResponseEntity<>(emailTemplate, HttpStatus.OK);
		} else {
			throw new EmailTemplateNotFoundException("No such Common Master found with id: " + id);
		}
	}

	@GetMapping("/emailTemplate")
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@JsonView(Views.EmailTemplate.class)
	public ResponseEntity<List<?>> getCommonMasterData() {

		List<EmailTemplate> emailTemplate = emailTemplateService.getAllList();

		return new ResponseEntity<>(emailTemplate, HttpStatus.OK);
	}

	@PostMapping(path = "/createEmailTemplate")
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@JsonView(Views.EmailTemplatePost.class)
	public <T> ResponseEntity<T> createEmailTemplate(@RequestBody EmailTemplate emailTemplate) {

		String emailTemplateContent = StringEscapeUtils.unescapeHtml(emailTemplate.getContent());
		emailTemplate.setContent(emailTemplateContent);
		logger.info("content: {}", emailTemplate.getContent());

		EmailTemplate emailTemplate2=null;
		try {
			emailTemplate2 = emailTemplateService.save(emailTemplate);
		} catch (Exception e) {
			return new ResponseEntity(new ErrorResponse(e.getMessage()), HttpStatus.CONFLICT);
		}
		return new ResponseEntity(emailTemplate2, HttpStatus.OK);

	}

	@PutMapping(path = "/updateEmailTemplate")
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@JsonView(Views.EmailTemplatePut.class)
	public ResponseEntity<EmailTemplate> updateEmailTemplate(@RequestBody EmailTemplate emailTemplate)
			throws EmailTemplateNotFoundException, CloneNotSupportedException {

		EmailTemplate returnedEmailTemplate = emailTemplateService.update(emailTemplate);
		if (returnedEmailTemplate == null)
			throw new EmailTemplateNotFoundException("No such Email Template found to update");
		else
			return new ResponseEntity<>(returnedEmailTemplate, HttpStatus.OK);

	}

	@DeleteMapping("/deleteEmailTemplate/{id}/{updatedBy}")
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@JsonView(Views.EmailTemplate.class)
	public ResponseEntity deleteContact(@PathVariable Long id, @PathVariable String updatedBy) throws CloneNotSupportedException {
		EmailTemplate isdeleted = emailTemplateService.delete(id, updatedBy);
		if (isdeleted.isDeleted())
			return new ResponseEntity(HttpStatus.OK);
		else
			throw new EmailTemplateNotFoundException("Email Template not found with id: " + id);
	}

}
