package com.avisys.cv.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.PersistenceException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avisys.cv.dto.WorkExperienceDto;
import com.avisys.cv.entity.Person;
import com.avisys.cv.entity.WorkExperience;
import com.avisys.cv.repository.PersonRepo;
import com.avisys.cv.repository.WorkExperienceRepo;

@Service
public class WorkExperienceService {

	@Autowired
	private WorkExperienceRepo WorkExperienceRepository;

	@Autowired
	private PersonRepo personRepository;

	@Autowired
	private ModelMapper modelMapper;

	public List<WorkExperienceDto> getAllWorkExperiences(Long personId) {
		Person person = personRepository.findByPersonIdAndIsDeletedFalse(personId)
				.orElseThrow(() -> new PersistenceException("Person not found"));
		List<WorkExperience> WorkExperiences = WorkExperienceRepository.findByPersonAndIsDeletedFalseOrderByCreatedDateDesc(person);
		List<WorkExperienceDto> WorkExperienceDto = WorkExperiences.stream()
				.map((WorkExperience) -> this.modelMapper.map(WorkExperience, WorkExperienceDto.class))
				.collect(Collectors.toList());
		return WorkExperienceDto;
	}

	public WorkExperienceDto update(WorkExperienceDto WorkExperienceDto, Long personId, String uid) {
		Person person = personRepository.findByPersonIdAndIsDeletedFalse(personId)
				.orElseThrow(() -> new PersistenceException("Person not found"));
		WorkExperience existingWorkExperience = WorkExperienceRepository
				.findByExpIdAndIsDeletedFalse(WorkExperienceDto.getExpId())
				.orElseThrow(() -> new PersistenceException("Work Experience not found"));
		existingWorkExperience.setCompany(WorkExperienceDto.getCompany());
		existingWorkExperience.setClientName(WorkExperienceDto.getClientName());
		existingWorkExperience.setPosition(WorkExperienceDto.getPosition());
		existingWorkExperience.setStartDate(WorkExperienceDto.getStartDate());
		existingWorkExperience.setCity(WorkExperienceDto.getCity());
		existingWorkExperience.setState(WorkExperienceDto.getState());
		existingWorkExperience.setCountry(WorkExperienceDto.getCountry());
		existingWorkExperience.setDescription(WorkExperienceDto.getDescription());
		existingWorkExperience.setDomain(WorkExperienceDto.getDomain());
		existingWorkExperience.setPerson(person);
		existingWorkExperience.setLastUpdateBy(uid);
		existingWorkExperience.setLastUpdateDate(LocalDateTime.now());
		if(WorkExperienceDto.getEndDate().equals("Present")) {
			existingWorkExperience.setEndDate("Present");
			String currentDateAsString = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM"));
			existingWorkExperience.setYearsOfExperience(ExperienceYears(WorkExperienceDto.getStartDate(), currentDateAsString));
		} else {
			existingWorkExperience.setEndDate(WorkExperienceDto.getEndDate());
			existingWorkExperience.setYearsOfExperience(ExperienceYears(WorkExperienceDto.getStartDate(), WorkExperienceDto.getEndDate()));
	}
		WorkExperience WorkExperienceObj = WorkExperienceRepository.save(existingWorkExperience);
		return this.modelMapper.map(WorkExperienceObj, WorkExperienceDto.class);
	}

	public WorkExperienceDto saveWorkExperience(WorkExperienceDto WorkExperienceDto, Long personId, String uid) {
		Person person = personRepository.findByPersonIdAndIsDeletedFalse(personId)
				.orElseThrow(() -> new PersistenceException("Person not found"));
		WorkExperience WorkExperience = this.modelMapper.map(WorkExperienceDto, WorkExperience.class);
		WorkExperience.setIsDeleted(false);
		WorkExperience.setPerson(person);
		WorkExperience.setCreationDate(LocalDateTime.now());
		WorkExperience.setLastUpdateDate(LocalDateTime.now());
		WorkExperience.setCreatedBy(uid);
		WorkExperience.setLastUpdateBy(uid);
		if(WorkExperience.getEndDate().equals("Present")) {
			WorkExperience.setEndDate("Present");
			String currentDateAsString = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM"));
			WorkExperience.setYearsOfExperience(ExperienceYears(WorkExperience.getStartDate(), currentDateAsString));
		}else {
		WorkExperience.setYearsOfExperience(ExperienceYears(WorkExperience.getStartDate(), WorkExperience.getEndDate()));
		}
		WorkExperience WorkExperienceObj = WorkExperienceRepository.save(WorkExperience);
		return this.modelMapper.map(WorkExperienceObj, WorkExperienceDto.class);
	}

	public WorkExperienceDto getByWorkExperienceId(Long WorkExperienceId) {
		WorkExperience WorkExperience = WorkExperienceRepository.findByExpIdAndIsDeletedFalse(WorkExperienceId)
				.orElseThrow(() -> new PersistenceException("Work Experience not found"));
		return this.modelMapper.map(WorkExperience, WorkExperienceDto.class);
	}

	public WorkExperienceDto softDelete(Long WorkExperienceId, String uid) {
		WorkExperience workExperience = WorkExperienceRepository.findByExpIdAndIsDeletedFalse(WorkExperienceId)
				.orElseThrow(() -> new PersistenceException("Work Experience not found"));
		workExperience.setIsDeleted(true);
		workExperience.setLastUpdateDate(LocalDateTime.now());
		workExperience.setLastUpdateBy(uid);
		WorkExperience WorkExperienceObj = WorkExperienceRepository.save(workExperience);
		return this.modelMapper.map(WorkExperienceObj, WorkExperienceDto.class);
	}
	
	public double ExperienceYears(String startDate, String endDate) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
		YearMonth start = YearMonth.parse(startDate, formatter);
		YearMonth end = YearMonth.parse(endDate, formatter);

		long monthDifference = ChronoUnit.MONTHS.between(start, end);

        double yearDifference = monthDifference / 12.0;
        yearDifference = Math.round(yearDifference * 10.0) / 10.0;
		return yearDifference;
	}

}
