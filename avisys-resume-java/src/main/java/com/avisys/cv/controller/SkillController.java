package com.avisys.cv.controller;

import java.util.ArrayList;
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

import com.avisys.cv.dto.SkillDto;
import com.avisys.cv.service.SkillService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("skill")
public class SkillController {

	@Autowired
	SkillService SkillService;

	@GetMapping("/get-all")
	public ResponseEntity<List<SkillDto>> getAllSkill(Long personId) {
		try {
			List<SkillDto> allSkillalInfo = SkillService.getAllSkills(personId);
			return new ResponseEntity<List<SkillDto>>(allSkillalInfo, HttpStatus.OK);
		} catch (Exception e) {
			throw new PersistenceException(e.getMessage());
		}
	}

	@PostMapping("/save")
	public ResponseEntity<List<SkillDto>> createSkill(@Valid @RequestBody List<SkillDto> skills, Long personId,
			@RequestHeader HttpHeaders headers) {
		try {
			String userId = headers.getFirst("userId");
			List<SkillDto> savedSkills = new ArrayList<>();
			for (SkillDto skill : skills) {
				SkillDto savedSkill = SkillService.saveSkill(skill, personId, userId);
				savedSkills.add(savedSkill);
			}
			return new ResponseEntity<>(savedSkills, HttpStatus.OK);
		} catch (Exception e) {
			throw new PersistenceException(e.getMessage());
		}
	}

	@PutMapping("/update")
	public ResponseEntity<List<SkillDto>> updateSkills(@Valid @RequestBody List<SkillDto> skills, Long personId,
			@RequestHeader HttpHeaders headers) {
		try {
			String userId = headers.getFirst("userId");
			List<SkillDto> updatedSkills = new ArrayList<>();
			for (SkillDto skill : skills) {
				SkillDto updatedSkill = SkillService.update(skill, personId, userId);
				updatedSkills.add(updatedSkill);
			}
			return new ResponseEntity<>(updatedSkills, HttpStatus.OK);
		} catch (Exception e) {
			throw new PersistenceException(e.getMessage());
		}
	}

	@DeleteMapping("/soft-delete")
	public ResponseEntity<SkillDto> delete(Long skillId, @RequestHeader HttpHeaders headers) {
		try {
			String userId = headers.getFirst("userId");
			SkillDto deletedSkill = SkillService.softDelete(skillId, userId);
			return new ResponseEntity<>(deletedSkill, HttpStatus.OK);
		} catch (Exception e) {
			throw new PersistenceException(e.getMessage());
		}
	}

	@GetMapping("/get")
	public ResponseEntity<SkillDto> getByIdSkill(Long skillId) {
		try {
			SkillDto Skill = SkillService.getBySkillId(skillId);
			return new ResponseEntity<>(Skill, HttpStatus.OK);
		} catch (Exception e) {
			throw new PersistenceException(e.getMessage());
		}
	}
}
