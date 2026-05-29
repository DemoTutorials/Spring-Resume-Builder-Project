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

import com.avisys.cv.dto.LanguageDto;
import com.avisys.cv.service.LanguageService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("language")
public class LanguageController {

	@Autowired
	LanguageService LanguageService;

	@GetMapping("/get-all")
	public ResponseEntity<List<LanguageDto>> getAllLanguage(Long personId) {
		try {
			List<LanguageDto> allLanguagealInfo = LanguageService.getAllLanguages(personId);
			return new ResponseEntity<List<LanguageDto>>(allLanguagealInfo, HttpStatus.OK);
		} catch (Exception e) {
			throw new PersistenceException(e.getMessage());
		}
	}

	@PostMapping("/save")
	public ResponseEntity<List<LanguageDto>> createLanguages(@Valid @RequestBody List<LanguageDto> languages,
			Long personId, @RequestHeader HttpHeaders headers) {
		try {
			String userId = headers.getFirst("userId");
			List<LanguageDto> savedLanguages = new ArrayList<>();
			for (LanguageDto language : languages) {
				LanguageDto savedLanguage = LanguageService.saveLanguage(language, personId, userId);
				savedLanguages.add(savedLanguage);
			}
			return new ResponseEntity<>(savedLanguages, HttpStatus.OK);
		} catch (Exception e) {
			throw new PersistenceException(e.getMessage());
		}
	}

	@PutMapping("/update")
	public ResponseEntity<List<LanguageDto>> updateLanguages(@Valid @RequestBody List<LanguageDto> languages,
			Long personId, @RequestHeader HttpHeaders headers) {
		try {
			String userId = headers.getFirst("userId");
			List<LanguageDto> updatedLanguages = new ArrayList<>();
			for (LanguageDto language : languages) {
				LanguageDto updatedLanguage = LanguageService.update(language, personId, userId);
				updatedLanguages.add(updatedLanguage);
			}
			return new ResponseEntity<>(updatedLanguages, HttpStatus.OK);
		} catch (Exception e) {
			throw new PersistenceException(e.getMessage());
		}
	}

	@DeleteMapping("/soft-delete")
	public ResponseEntity<LanguageDto> delete(Long languageId, @RequestHeader HttpHeaders headers) {
		try {
			String userId = headers.getFirst("userId");
			LanguageDto deletedLanguage = LanguageService.softDelete(languageId, userId);
			return new ResponseEntity<>(deletedLanguage, HttpStatus.OK);
		} catch (Exception e) {
			throw new PersistenceException(e.getMessage());
		}
	}

	@GetMapping("/get")
	public ResponseEntity<LanguageDto> getByIdLanguage(Long languageId) {
		try {
			LanguageDto Language = LanguageService.getByLanguageId(languageId);
			return new ResponseEntity<>(Language, HttpStatus.OK);
		} catch (Exception e) {
			throw new PersistenceException(e.getMessage());
		}
	}
}
