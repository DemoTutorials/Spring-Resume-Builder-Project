package com.avisys.microservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.avisys.microservice.model.EmailRequestBody;
import com.avisys.microservice.util.EmailTemplateDetails;
import com.avisys.microservice.util.Utility;

@RestController
@RequestMapping("/masters")
public class ScheduleEmailController {

	@Autowired
	EmailTemplateDetails emailTempateDetails;

	@Autowired
	Utility utility;

	@PostMapping("/scheduleEmail")
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	public ResponseEntity<?> scheduleEmailRequest(@RequestBody EmailRequestBody emailBody) {

		String scheduleEmailRequest = emailTempateDetails.scheduleEmailRequest(emailBody);

		if (scheduleEmailRequest.contains("Mail Send successfully")) {
			return new ResponseEntity<>(HttpStatus.OK);
		} else if (scheduleEmailRequest.contains("Failed Sending Mail")) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

	}

}
