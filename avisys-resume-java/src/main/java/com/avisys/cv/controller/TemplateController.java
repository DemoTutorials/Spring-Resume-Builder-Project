package com.avisys.cv.controller;

import java.io.IOException;

import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.avisys.cv.service.ActionType;
import com.avisys.cv.service.PreviewSample;
import com.avisys.cv.service.TemplateService;

import net.sf.jasperreports.engine.JRException;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("resume-template")
public class TemplateController {

	@Autowired
	private TemplateService service;

	@GetMapping("/report")
	public ResponseEntity<Resource> generateReport(Long personId, ActionType actionType, PreviewSample previewSample, @RequestHeader HttpHeaders headers)
			throws JRException, IOException {
		try {
			String userId = headers.getFirst("userId");
			return service.exportReport(personId, actionType, previewSample, userId);
		} catch (Exception e) {
			throw new PersistenceException(e.getMessage());
		}
	}
}