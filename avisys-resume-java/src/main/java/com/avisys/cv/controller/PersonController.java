package com.avisys.cv.controller;

import java.util.List;

import javax.persistence.PersistenceException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.avisys.cv.dto.DataTableDto;
import com.avisys.cv.dto.PersonDto;
import com.avisys.cv.dto.UserDetailsDto;
import com.avisys.cv.entity.Person;
import com.avisys.cv.service.PersonService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("person")
public class PersonController {

	@Autowired
	PersonService PersonService;

	@GetMapping("/get-all")
	public ResponseEntity<List<PersonDto>> getAllPerson() {
		try {
			List<PersonDto> allpersonInfo = PersonService.getAllPersons();
			return new ResponseEntity<List<PersonDto>>(allpersonInfo, HttpStatus.OK);
		} catch (Exception e) {
			throw new PersistenceException(e.getMessage());
		}
	}

	@PostMapping(path = "/save", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public ResponseEntity<PersonDto> save(@Valid @RequestPart PersonDto person, @RequestHeader HttpHeaders headers,
			@Nullable @RequestPart MultipartFile file, @Nullable @RequestPart MultipartFile resume) {
		try {
			String userId = headers.getFirst("userId");
			PersonDto savedperson = PersonService.createPerson(person, userId, file, resume);
			return new ResponseEntity<PersonDto>(savedperson, HttpStatus.OK);
		} catch (Exception e) {
			throw new PersistenceException(e.getMessage());
		}
	}

	@PutMapping(path = "/update", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public ResponseEntity<PersonDto> updatePerson(@Valid @RequestPart PersonDto personDto, @RequestHeader HttpHeaders headers,
			@Nullable @RequestPart MultipartFile file, @Nullable @RequestPart MultipartFile resume) {
		try {
			String userId = headers.getFirst("userId");
			PersonDto updatedPerson = PersonService.update(personDto, userId, file, resume);
			return new ResponseEntity<PersonDto>(updatedPerson, HttpStatus.OK);
		} catch (Exception e) {
			throw new PersistenceException(e.getMessage());
		}
	}

	@DeleteMapping("/soft-delete")
	public ResponseEntity<PersonDto> delete(Long personId, @RequestHeader HttpHeaders headers) {
		try {
			String userId = headers.getFirst("userId");
			PersonDto deletedPerson = PersonService.softDelete(personId, userId);
			return new ResponseEntity<>(deletedPerson, HttpStatus.OK);
		} catch (Exception e) {
			throw new PersistenceException(e.getMessage());
		}
	}

	@GetMapping("/get")
	public ResponseEntity<PersonDto> getByIdPerson(@Nullable Long personId) {
		try {
			PersonDto person = PersonService.getByPersonId(personId);
			return new ResponseEntity<>(person, HttpStatus.OK);
		} catch (Exception e) {
			throw new PersistenceException(e.getMessage());
		}
	}

	@GetMapping("/search")
	public ResponseEntity<Page<DataTableDto>> searchEmployee(@PageableDefault Pageable pageable, @RequestHeader HttpHeaders headers,
			@RequestParam(value = "keyword", defaultValue = "", required = false) String keyword) {
		String userId = headers.getFirst("userId");
		Page<DataTableDto> result = this.PersonService.searchPerson(pageable, keyword, userId);
		return new ResponseEntity<Page<DataTableDto>>(result, HttpStatus.OK);
	}

	@GetMapping("/get-user")
	public ResponseEntity<PersonDto> getByUserId(@RequestHeader HttpHeaders headers, Long id) {
		try {
			String userRole = headers.getFirst("userRole");
			PersonDto person = PersonService.getPersonByUserId(id, userRole);
			return new ResponseEntity<>(person, HttpStatus.OK);
		} catch (Exception e) {
			throw new PersistenceException(e.getMessage());
		}
	}

	@GetMapping("dashboard/search")
	public ResponseEntity<Page<DataTableDto>> search(@RequestHeader HttpHeaders headers, @PageableDefault Pageable pageable,
			@RequestParam(value = "minExperience", required = false, defaultValue = "0") Float minExperience,
			@RequestParam(value = "maxExperience", required = false, defaultValue = "100") Float maxExperience,
			@RequestParam(value = "skill", required = false) String skill,
			@RequestParam(value = "domain", required = false) String domain,
			@RequestParam(value = "companyName", required = false) String companyName,
			@RequestParam(value = "jobProfile", required = false) String jobProfile
			) {
		try {
			String userId = headers.getFirst("userId");
			String userRole = headers.getFirst("userRole");
			if (minExperience != null && maxExperience != null && minExperience >= maxExperience) {
				return ResponseEntity.badRequest().build();
			}

			Page<DataTableDto> result = this.PersonService.searchPersonOnDashboard(pageable, minExperience,
					maxExperience, skill, domain, companyName, jobProfile, userRole, userId);
			return new ResponseEntity<>(result, HttpStatus.OK);
		} catch (Exception e) {
			throw new PersistenceException(e.getMessage());
		}
	}
	
	@PostMapping(path = "/resume-triggere")
	public ResponseEntity<PersonDto> createResumeWithUserCreation( @RequestBody UserDetailsDto user) {
		try {
			PersonDto savedperson = PersonService.createPersonByHR(user);
			return new ResponseEntity<PersonDto>(savedperson, HttpStatus.OK);
		} catch (Exception e) {
			throw new PersistenceException(e.getMessage());
		}
	}

}
