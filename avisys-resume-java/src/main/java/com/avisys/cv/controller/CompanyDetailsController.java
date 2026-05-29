package com.avisys.cv.controller;

import javax.persistence.PersistenceException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.avisys.cv.dto.CompanyDetailsDto;
import com.avisys.cv.service.CompanyDetailService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("company")
public class CompanyDetailsController {
	
	@Autowired
	private CompanyDetailService companyDetails;

	@PostMapping(path = "/save", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public ResponseEntity<CompanyDetailsDto> save(@Valid @RequestPart CompanyDetailsDto details, @RequestHeader HttpHeaders headers,
			@Nullable @RequestPart MultipartFile logo, @Nullable @RequestPart MultipartFile watermark) {
		try {
			String userId = headers.getFirst("userId");
			CompanyDetailsDto saveDetails = companyDetails.createCompanyDetails(details, userId, logo, watermark);
			return new ResponseEntity<CompanyDetailsDto>(saveDetails, HttpStatus.OK);
		} catch (Exception e) {
			throw new PersistenceException(e.getMessage());
		}
	}

	@PutMapping(path = "/update", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public ResponseEntity<CompanyDetailsDto> updatePerson(@Valid @RequestPart CompanyDetailsDto details,
			@RequestHeader HttpHeaders headers, @Nullable @RequestPart MultipartFile logo, @Nullable @RequestPart MultipartFile watermark) {
		try {
			String userId = headers.getFirst("userId");
			CompanyDetailsDto updateDetails = companyDetails.updateCompanyDetails(details, userId, logo, watermark);
			return new ResponseEntity<CompanyDetailsDto>(updateDetails, HttpStatus.OK);
		} catch (Exception e) {
			throw new PersistenceException(e.getMessage());
		}
	}
	
	@GetMapping("/get")
	public ResponseEntity<CompanyDetailsDto> getCompanyDetails( String companyCode) {
		try {
			CompanyDetailsDto person = companyDetails.getByCompanyCode(companyCode);
			return new ResponseEntity<>(person, HttpStatus.OK);
		} catch (Exception e) {
			throw new PersistenceException(e.getMessage());
		}
	}
}
