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

import com.avisys.cv.dto.WorkExperienceDto;
import com.avisys.cv.service.WorkExperienceService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("work-experience")
public class WorkExperienceController {

	@Autowired
	WorkExperienceService WorkExperienceService;

	@GetMapping("/get-all")
	public ResponseEntity<List<WorkExperienceDto>> getAllWorkExperience(Long personId) {
		try {
			List<WorkExperienceDto> allWorkExperiencealInfo = WorkExperienceService.getAllWorkExperiences(personId);
			return new ResponseEntity<List<WorkExperienceDto>>(allWorkExperiencealInfo, HttpStatus.OK);
		} catch (Exception e) {
			throw new PersistenceException(e.getMessage());
		}
	}

	@PostMapping("/save")
	public ResponseEntity<WorkExperienceDto> createWorkExperience(@Valid @RequestBody WorkExperienceDto WorkExperience,
			Long personId, @RequestHeader HttpHeaders headers) {
		try {
			String userId = headers.getFirst("userId");
			WorkExperienceDto savedWorkExperience = WorkExperienceService.saveWorkExperience(WorkExperience, personId,
					userId);
			return new ResponseEntity<WorkExperienceDto>(savedWorkExperience, HttpStatus.OK);
		} catch (Exception e) {
			throw new PersistenceException(e.getMessage());
		}
	}

	@PutMapping("/update")
	public ResponseEntity<WorkExperienceDto> updateWorkExperience(
			@Valid @RequestBody WorkExperienceDto WorkExperienceDto, Long personId,
			@RequestHeader HttpHeaders headers) {
		try {
			String userId = headers.getFirst("userId");
			WorkExperienceDto updatedWorkExperience = WorkExperienceService.update(WorkExperienceDto, personId, userId);
			return new ResponseEntity<WorkExperienceDto>(updatedWorkExperience, HttpStatus.OK);
		} catch (Exception e) {
			throw new PersistenceException(e.getMessage());
		}
	}

	@DeleteMapping("/soft-delete")
	public ResponseEntity<WorkExperienceDto> delete(Long experienceId, @RequestHeader HttpHeaders headers) {
		try {
			String userId = headers.getFirst("userId");
			WorkExperienceDto deletedWorkExperience = WorkExperienceService.softDelete(experienceId, userId);
			return new ResponseEntity<>(deletedWorkExperience, HttpStatus.OK);
		} catch (Exception e) {
			throw new PersistenceException(e.getMessage());
		}
	}

	@GetMapping("/get")
	public ResponseEntity<WorkExperienceDto> getByIdWorkExperience(Long experienceId) {
		try {
			WorkExperienceDto WorkExperience = WorkExperienceService.getByWorkExperienceId(experienceId);
			return new ResponseEntity<>(WorkExperience, HttpStatus.OK);
		} catch (Exception e) {
			throw new PersistenceException(e.getMessage());
		}
	}
}
