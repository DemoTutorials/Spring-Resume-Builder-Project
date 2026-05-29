package com.avisys.cv.controller;

import java.util.List;

import javax.persistence.PersistenceException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.avisys.cv.dto.EducationDto;
import com.avisys.cv.service.EducationService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("education")
public class EducationController {

	@Autowired
	EducationService EducationService;

	@GetMapping("/get-all")
	public ResponseEntity<List<EducationDto>> getAllEducation(Long personId) {
		try {
			List<EducationDto> allEducationalInfo = EducationService.getAllEducations(personId);
			return new ResponseEntity<List<EducationDto>>(allEducationalInfo, HttpStatus.OK);
		} catch (Exception e) {
			throw new PersistenceException(e.getMessage());
		}
	}

	@PostMapping("/save")
	public ResponseEntity<EducationDto> createEducation(@Valid @RequestBody EducationDto Education, Long personId,
			@RequestHeader HttpHeaders headers) {
		try {
			String userId = headers.getFirst("userId");
			EducationDto savedEducation = EducationService.saveEducation(Education, personId, userId);
			return new ResponseEntity<EducationDto>(savedEducation, HttpStatus.OK);
		} catch (Exception e) {
			throw new PersistenceException(e.getMessage());
		}
	}

	@PutMapping("/update")
	public ResponseEntity<EducationDto> updateEducation(@Valid @RequestBody EducationDto EducationDto, Long personId,
			@RequestHeader HttpHeaders headers) {
		try {
			String userId = headers.getFirst("userId");
			EducationDto updatedEducation = EducationService.update(EducationDto, personId, userId);
			return new ResponseEntity<EducationDto>(updatedEducation, HttpStatus.OK);
		} catch (Exception e) {
			throw new PersistenceException(e.getMessage());
		}
	}

	@DeleteMapping("/soft-delete")
	public ResponseEntity<EducationDto> delete(Long educationId, @RequestHeader HttpHeaders headers) {
		try {
			String userId = headers.getFirst("userId");
			EducationDto deletedEducation = EducationService.softDelete(educationId, userId);
			return new ResponseEntity<>(deletedEducation, HttpStatus.OK);
		} catch (Exception e) {
			throw new PersistenceException(e.getMessage());
		}
	}

	@GetMapping("/get")
	public ResponseEntity<EducationDto> getByIdEducation(Long educationId) {
		try {
			EducationDto Education = EducationService.getByEducationId(educationId);
			return new ResponseEntity<>(Education, HttpStatus.OK);
		} catch (Exception e) {
			throw new PersistenceException(e.getMessage());
		}
	}
}
