package com.avisys.email.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.avisys.email.service.AuditService;

@RestController
@RequestMapping("audit")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AuditController {

	@Autowired
	AuditService auditService;

	@GetMapping("all")
	public ResponseEntity<?> getRevisionByEntityAndReference(String type, Long id) {
		                                                                                            

		Object revisionInfoList = auditService.getRevisionInfoByFlagAndReferenc(type,id);
		
		return new ResponseEntity<>(revisionInfoList, HttpStatus.OK);

	}

}
