package com.avisys.cv.service;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.persistence.PersistenceException;
import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avisys.cv.dto.OtherSkillDto;
import com.avisys.cv.entity.OtherSkills;
import com.avisys.cv.entity.Person;
import com.avisys.cv.repository.OtherSkillsRepo;
import com.avisys.cv.repository.PersonRepo;

@Service
public class OtherSkillService {

	@Autowired
	OtherSkillsRepo otherSkillsRepository;

	@Autowired
	PersonRepo personRepository;

	@Autowired
	ModelMapper modelMapper;

	public OtherSkillDto getAllOtherSkills(Long personId) {
		Person person = personRepository.findByPersonIdAndIsDeletedFalse(personId)
				.orElseThrow(() -> new PersistenceException("Person not found"));
		OtherSkills otherSkills = otherSkillsRepository.findByPersonOrderByLastUpdateDateAsc(person).orElseThrow(() -> new PersistenceException("Other skills not found"));
		return this.modelMapper.map(otherSkills, OtherSkillDto.class);
	}

	public OtherSkillDto createOtherSkills(OtherSkillDto otherSkillDto, Long personId, String uid) {
		Person person = personRepository.findByPersonIdAndIsDeletedFalse(personId)
				.orElseThrow(() -> new PersistenceException("Person not found"));
		Optional<OtherSkills> otherSkills = otherSkillsRepository.findByPersonOrderByLastUpdateDateAsc(person);
		if (otherSkills.isPresent()) {
            throw new PersistenceException("Other skills already exists");
        }
		OtherSkills Skill = this.modelMapper.map(otherSkillDto, OtherSkills.class);
		Skill.setIsDeleted(false);
		Skill.setPerson(person);
		Skill.setCreationDate(LocalDateTime.now());
		Skill.setLastUpdateDate(LocalDateTime.now());
		Skill.setCreatedBy(uid);
		Skill.setLastUpdateBy(uid);
		OtherSkills SkillObj = otherSkillsRepository.save(Skill);
		return this.modelMapper.map(SkillObj, OtherSkillDto.class);
	}

	public OtherSkillDto updateOtherSkills(@Valid OtherSkillDto otherSkillsDto, Long personId, String uid) {
		Person person = personRepository.findByPersonIdAndIsDeletedFalse(personId)
				.orElseThrow(() -> new PersistenceException("Person not found"));
		OtherSkills existingSkill = otherSkillsRepository.findByPersonOrderByLastUpdateDateAsc(person)
				.orElseThrow(() -> new PersistenceException("Other skills not found"));
		existingSkill.setIsDeleted(false);
		existingSkill.setOtherSkillName(otherSkillsDto.getOtherSkillName());
		existingSkill.setPerson(person);
		existingSkill.setLastUpdateDate(LocalDateTime.now());
		existingSkill.setLastUpdateBy(uid);
		OtherSkills SkillObj = otherSkillsRepository.save(existingSkill);
		return this.modelMapper.map(SkillObj, OtherSkillDto.class);
	}

	public OtherSkillDto softDelete(Long otherSkillId, String uid) {
		OtherSkills skills = otherSkillsRepository.findByOtherSkillIdAndIsDeletedFalse(otherSkillId).orElseThrow(()->new PersistenceException("other skills not found"));
		skills.setIsDeleted(true);
		skills.setLastUpdateDate(LocalDateTime.now());
		skills.setLastUpdateBy(uid);
		OtherSkills SkillObj = otherSkillsRepository.save(skills);
		return this.modelMapper.map(SkillObj, OtherSkillDto.class);
	}

}
