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

import com.avisys.cv.dto.CertificationDto;
import com.avisys.cv.service.CertificationService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("certification")
public class CertificationController {

	@Autowired
	CertificationService CertificationService;

	@GetMapping("/get-all")
	public ResponseEntity<List<CertificationDto>> getAllCertification(Long personId) {
		try {
			List<CertificationDto> allCertificationalInfo = CertificationService.getAllCertifications(personId);
			return new ResponseEntity<List<CertificationDto>>(allCertificationalInfo, HttpStatus.OK);
		} catch (Exception e) {
			throw new PersistenceException(e.getMessage());
		}
	}

	@PostMapping("/save")
	public ResponseEntity<List<CertificationDto>> createCertifications(
			@Valid @RequestBody List<CertificationDto> certifications, Long personId,
			@RequestHeader HttpHeaders headers) {
		try {
			String userId = headers.getFirst("userId");
			List<CertificationDto> savedCertifications = new ArrayList<>();
			for (CertificationDto certification : certifications) {
				CertificationDto savedCertification = CertificationService.saveCertification(certification, personId,
						userId);
				savedCertifications.add(savedCertification);
			}
			return new ResponseEntity<>(savedCertifications, HttpStatus.OK);
		} catch (Exception e) {
			throw new PersistenceException(e.getMessage());
		}
	}

	@PutMapping("/update")
	public ResponseEntity<List<CertificationDto>> updateCertifications(
			@Valid @RequestBody List<CertificationDto> certifications, Long personId,
			@RequestHeader HttpHeaders headers) {
		try {
			String userId = headers.getFirst("userId");
			List<CertificationDto> updatedCertifications = new ArrayList<>();
			for (CertificationDto certification : certifications) {
				CertificationDto updatedCertification = CertificationService.update(certification, personId, userId);
				updatedCertifications.add(updatedCertification);
			}
			return new ResponseEntity<>(updatedCertifications, HttpStatus.OK);
		} catch (Exception e) {
			throw new PersistenceException(e.getMessage());
		}
	}

	@DeleteMapping("/soft-delete")
	public ResponseEntity<CertificationDto> delete(Long certificationId, @RequestHeader HttpHeaders headers) {
		try {
			String userId = headers.getFirst("userId");
			CertificationDto deletedCertification = CertificationService.softDelete(certificationId, userId);
			return new ResponseEntity<>(deletedCertification, HttpStatus.OK);
		} catch (Exception e) {
			throw new PersistenceException(e.getMessage());
		}
	}

	@GetMapping("/get")
	public ResponseEntity<CertificationDto> getByIdCertification(Long certificationId) {
		try {
			CertificationDto Certification = CertificationService.getByCertificationId(certificationId);

			return new ResponseEntity<>(Certification, HttpStatus.OK);
		} catch (Exception e) {
			throw new PersistenceException(e.getMessage());
		}
	}
}
