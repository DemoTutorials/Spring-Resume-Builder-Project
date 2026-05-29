package com.avisys.cv.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.PersistenceException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avisys.cv.dto.EducationDto;
import com.avisys.cv.entity.Education;
import com.avisys.cv.entity.Person;
import com.avisys.cv.repository.EducationRepo;
import com.avisys.cv.repository.PersonRepo;

@Service
public class EducationService {

	@Autowired
	private EducationRepo educationRepository;

	@Autowired
	private PersonRepo personRepository;

	@Autowired
	private ModelMapper modelMapper;

	public List<EducationDto> getAllEducations(Long personId) {
		Person person = personRepository.findByPersonIdAndIsDeletedFalse(personId)
				.orElseThrow(() -> new PersistenceException("Person not found"));
		List<Education> educations = educationRepository.findByPersonAndIsDeletedFalseOrderByCreatedDateDesc(person);
		List<EducationDto> educationDto = educations.stream()
				.map((education) -> this.modelMapper.map(education, EducationDto.class)).collect(Collectors.toList());
		return educationDto;
	}

	public EducationDto update(EducationDto EducationDto, Long personId, String uid) {
		Person person = personRepository.findByPersonIdAndIsDeletedFalse(personId)
				.orElseThrow(() -> new PersistenceException("Person not found"));
		Education existingEducation = educationRepository.findByEduIdAndIsDeletedFalse(EducationDto.getEduId())
				.orElseThrow(() -> new PersistenceException("Education not found"));
		existingEducation.setQualification(EducationDto.getQualification());
		existingEducation.setStream(EducationDto.getStream());
		existingEducation.setCompletionYear(EducationDto.getCompletionYear());
		existingEducation.setInstitution(EducationDto.getInstitution());
		existingEducation.setCity(EducationDto.getCity());
		existingEducation.setState(EducationDto.getState());
		existingEducation.setCountry(EducationDto.getCountry());
		existingEducation.setDescription(EducationDto.getDescription());
		existingEducation.setPerson(person);
		existingEducation.setLastUpdateBy(uid);
		existingEducation.setLastUpdateDate(LocalDateTime.now());
		Education EducationObj = educationRepository.save(existingEducation);
		return this.modelMapper.map(EducationObj, EducationDto.class);
	}

	public EducationDto saveEducation(EducationDto EducationDto, Long personId, String uid) {
		Person person = personRepository.findByPersonIdAndIsDeletedFalse(personId)
				.orElseThrow(() -> new PersistenceException("Person not found"));
		Education Education = this.modelMapper.map(EducationDto, Education.class);
		Education.setIsDeleted(false);
		Education.setPerson(person);
		Education.setCreationDate(LocalDateTime.now());
		Education.setLastUpdateDate(LocalDateTime.now());
		Education.setCreatedBy(uid);
		Education.setLastUpdateBy(uid);
		Education EducationObj = educationRepository.save(Education);
		return this.modelMapper.map(EducationObj, EducationDto.class);
	}

	public EducationDto getByEducationId(Long educationId) {
		Education Education = educationRepository.findByEduIdAndIsDeletedFalse(educationId)
				.orElseThrow(() -> new PersistenceException("Education not found"));
		return this.modelMapper.map(Education, EducationDto.class);
	}

	public EducationDto softDelete(Long educationId, String uid) {
		Education education = educationRepository.findByEduIdAndIsDeletedFalse(educationId)
				.orElseThrow(() -> new PersistenceException("Education not found"));
		education.setIsDeleted(true);
		education.setLastUpdateDate(LocalDateTime.now());
		education.setLastUpdateBy(uid);
		Education EducationObj = educationRepository.save(education);
		return this.modelMapper.map(EducationObj, EducationDto.class);
	}

}
