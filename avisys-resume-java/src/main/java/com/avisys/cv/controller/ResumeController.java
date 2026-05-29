package com.avisys.cv.controller;

import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.avisys.cv.dto.ResumeTemplateDto;
import com.avisys.cv.service.ResumeService;

@RestController
@RequestMapping("resume")
public class ResumeController {

	@Autowired
	ResumeService resumeService;

	@GetMapping("/get")
	public ResponseEntity<ResumeTemplateDto> getResumeDataByPersonId(Long personId) {
		try {
			ResumeTemplateDto resume = resumeService.getResumeByPersonId(personId);
			return new ResponseEntity<ResumeTemplateDto>(resume, HttpStatus.OK);
		} catch (Exception e) {
			throw new PersistenceException(e.getMessage());
		}
	}
}
