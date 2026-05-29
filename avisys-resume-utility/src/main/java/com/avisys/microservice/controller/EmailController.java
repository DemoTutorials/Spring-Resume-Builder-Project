package com.avisys.microservice.controller;

import javax.annotation.security.RolesAllowed;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.avisys.microservice.entity.EmailDetails;
import com.avisys.microservice.jsonviews.Views;
import com.avisys.microservice.model.EmailContent;
import com.avisys.microservice.model.ResponseStatus;
import com.avisys.microservice.service.EmailDetailService;
import com.fasterxml.jackson.annotation.JsonView;

@RestController
@RequestMapping("/masters/email")
@RolesAllowed("admin")
public class EmailController {

	@Autowired
	EmailDetailService emailService;

	private static final Logger logger = LoggerFactory.getLogger(EmailController.class);

	@GetMapping("/emailDetails")
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	public Page<EmailDetails> getEmailDetails(@Nullable String name,@PageableDefault(sort={"startTime"},direction = Direction.DESC) Pageable page) {

			return emailService.getEmailDetailsPaging(name,page);

	}

	@GetMapping("/emailContent/{name}")
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@JsonView(Views.EmailTemplate.class)
	public ResponseEntity<?> getEmailContentFromJobName(@PathVariable String name) {

		EmailContent emailContent = emailService.getEmailContent(name);

		if (emailContent != null) {
			return new ResponseEntity<>(emailContent, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.OK);
		}
	}

	@GetMapping("/rescheduleEmail/{name}")
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@JsonView(Views.EmailTemplate.class)
	public ResponseEntity<?> rescheduleEmailByJobName(@PathVariable String name) {

		int emailContent = emailService.rescheduleEmail(name);

		if (emailContent == 200) {
			return new ResponseEntity<>(new ResponseStatus(emailContent, "Email Scheduled."), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(new ResponseStatus(emailContent, "Error while scheduling Email"),
					HttpStatus.BAD_REQUEST);
		}
	}

}
