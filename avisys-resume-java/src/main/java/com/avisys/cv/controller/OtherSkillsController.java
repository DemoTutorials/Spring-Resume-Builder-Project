package com.avisys.cv.controller;

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

import com.avisys.cv.dto.OtherSkillDto;
import com.avisys.cv.service.OtherSkillService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("other-skills")
public class OtherSkillsController {

	@Autowired
	OtherSkillService otherSkills;

	@GetMapping("/get")
	public ResponseEntity<OtherSkillDto> getAllOtherSkill(Long personId) {
		try {
			OtherSkillDto allOtherSkills = otherSkills.getAllOtherSkills(personId);
			return new ResponseEntity<OtherSkillDto>(allOtherSkills, HttpStatus.OK);
		} catch (Exception e) {
			throw new PersistenceException(e.getMessage());
		}
	}

	@PostMapping("/create")
	public ResponseEntity<OtherSkillDto> createOtherSkills(@RequestBody OtherSkillDto otherSkillDto, Long personId, @RequestHeader HttpHeaders headers) {
		try {
			String userId = headers.getFirst("userId");
			OtherSkillDto personalDetails = otherSkills.createOtherSkills(otherSkillDto, personId, userId);
			return new ResponseEntity<OtherSkillDto>(personalDetails, HttpStatus.OK);
		} catch (Exception e) {
			throw new PersistenceException(e.getMessage());
		}

	}

	@PutMapping("/update")
	public ResponseEntity<OtherSkillDto> updateOtherSkills(@Valid @RequestBody OtherSkillDto otherSkillsDto,
			Long personId, @RequestHeader HttpHeaders headers) {

		try {
			String userId = headers.getFirst("userId");
			OtherSkillDto otherSkill = otherSkills.updateOtherSkills(otherSkillsDto, personId, userId);
			return new ResponseEntity<OtherSkillDto>(otherSkill, HttpStatus.OK);
		} catch (Exception e) {
			throw new PersistenceException(e.getMessage());
		}
	}
	
	@DeleteMapping("/soft-delete")
	public ResponseEntity<OtherSkillDto> delete(Long otherSkillId, @RequestHeader HttpHeaders headers) {
		try {
			String userId = headers.getFirst("userId");
			OtherSkillDto deletedSkill = otherSkills.softDelete(otherSkillId, userId);
			return new ResponseEntity<>(deletedSkill, HttpStatus.OK);
		} catch (Exception e) {
			throw new PersistenceException(e.getMessage());
		}
	}

}
