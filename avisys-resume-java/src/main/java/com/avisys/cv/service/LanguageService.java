package com.avisys.cv.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.PersistenceException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avisys.cv.dto.LanguageDto;
import com.avisys.cv.entity.Language;
import com.avisys.cv.entity.Person;
import com.avisys.cv.repository.LanguageRepo;
import com.avisys.cv.repository.PersonRepo;

@Service
public class LanguageService {

	@Autowired
	private LanguageRepo LanguageRepository;

	@Autowired
	private PersonRepo personRepository;

	@Autowired
	private ModelMapper modelMapper;

	public List<LanguageDto> getAllLanguages(Long personId) {
		Person person = personRepository.findByPersonIdAndIsDeletedFalse(personId)
				.orElseThrow(() -> new PersistenceException("Person not found"));
		List<Language> Languages = LanguageRepository.findByPersonAndIsDeletedFalseOrderByLastUpdateDateAsc(person);
		List<LanguageDto> LanguageDto = Languages.stream()
				.map((Language) -> this.modelMapper.map(Language, LanguageDto.class)).collect(Collectors.toList());
		return LanguageDto;
	}

	public LanguageDto update(LanguageDto LanguageDto, Long personId, String uid) {
		Person person = personRepository.findByPersonIdAndIsDeletedFalse(personId)
				.orElseThrow(() -> new PersistenceException("Person not found"));
		Language existingLanguage = LanguageRepository.findByLangIdAndIsDeletedFalse(LanguageDto.getLangId())
				.orElseThrow(() -> new PersistenceException("Language not found"));
		existingLanguage.setLanguage(LanguageDto.getLanguage());
		existingLanguage.setPerson(person);
		existingLanguage.setLastUpdateBy(uid);
		existingLanguage.setLastUpdateDate(LocalDateTime.now());
		Language LanguageObj = LanguageRepository.save(existingLanguage);
		return this.modelMapper.map(LanguageObj, LanguageDto.class);
	}

	public LanguageDto saveLanguage(LanguageDto LanguageDto, Long personId, String uid) {
		Person person = personRepository.findByPersonIdAndIsDeletedFalse(personId)
				.orElseThrow(() -> new PersistenceException("Person not found"));
		Language Language = this.modelMapper.map(LanguageDto, Language.class);
		Language.setIsDeleted(false);
		Language.setPerson(person);
		Language.setCreationDate(LocalDateTime.now());
		Language.setLastUpdateDate(LocalDateTime.now());
		Language.setLastUpdateBy(uid);
		Language.setCreatedBy(uid);
		Language LanguageObj = LanguageRepository.save(Language);
		return this.modelMapper.map(LanguageObj, LanguageDto.class);
	}

	public LanguageDto getByLanguageId(Long languageId) {
		Language Language = LanguageRepository.findByLangIdAndIsDeletedFalse(languageId)
				.orElseThrow(() -> new PersistenceException("Language not found"));
		return this.modelMapper.map(Language, LanguageDto.class);
	}

	public LanguageDto softDelete(Long languageId, String uid) {
		Language language = LanguageRepository.findByLangIdAndIsDeletedFalse(languageId)
				.orElseThrow(() -> new PersistenceException("Language not found"));
		language.setIsDeleted(true);
		language.setLastUpdateDate(LocalDateTime.now());
		language.setLastUpdateBy(uid);
		Language LanguageObj = LanguageRepository.save(language);
		return this.modelMapper.map(LanguageObj, LanguageDto.class);
	}

}
