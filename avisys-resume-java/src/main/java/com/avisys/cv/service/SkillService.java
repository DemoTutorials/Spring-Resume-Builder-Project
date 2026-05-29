package com.avisys.cv.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.PersistenceException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avisys.cv.dto.SkillDto;
import com.avisys.cv.entity.Person;
import com.avisys.cv.entity.Skill;
import com.avisys.cv.repository.PersonRepo;
import com.avisys.cv.repository.SkillRepo;

@Service
public class SkillService {

	@Autowired
	private SkillRepo SkillRepository;

	@Autowired
	private PersonRepo personRepository;

	@Autowired
	private ModelMapper modelMapper;

	public List<SkillDto> getAllSkills(Long personId) {
		Person person = personRepository.findByPersonIdAndIsDeletedFalse(personId).orElseThrow(()->new PersistenceException("Person not found"));
		List<Skill> Skills = SkillRepository.findByPersonAndIsDeletedFalseOrderByLastUpdateDateAsc(person);
		List<SkillDto> SkillDto= Skills.stream().map((Skill)-> this.modelMapper.map(Skill,SkillDto.class)).collect(Collectors.toList());
	    return SkillDto;
	}

	public SkillDto update(SkillDto SkillDto, Long personId, String uid) {
		Person person = personRepository.findByPersonIdAndIsDeletedFalse(personId).orElseThrow(()->new PersistenceException("Person not found"));
		Skill existingSkill = SkillRepository
				.findBySkillIdAndIsDeletedFalse(SkillDto.getSkillId()).orElseThrow(()->new PersistenceException("Skill not found"));
		existingSkill.setSkillName(SkillDto.getSkillName());
		existingSkill.setPerson(person);
		existingSkill.setLastUpdateDate(LocalDateTime.now());
		existingSkill.setLastUpdateBy(uid);
		Skill SkillObj = SkillRepository.save(existingSkill);
		return this.modelMapper.map(SkillObj, SkillDto.class);
	}

	public SkillDto saveSkill(SkillDto SkillDto, Long personId, String uid) {
		Person person = personRepository.findByPersonIdAndIsDeletedFalse(personId).orElseThrow(()->new PersistenceException("Person not found"));
		Skill Skill = this.modelMapper.map(SkillDto, Skill.class);
		Skill.setIsDeleted(false);
		Skill.setPerson(person);
		Skill.setCreationDate(LocalDateTime.now());
		Skill.setLastUpdateDate(LocalDateTime.now());
		Skill.setCreatedBy(uid);
		Skill.setLastUpdateBy(uid);
		Skill SkillObj = SkillRepository.save(Skill);
		return this.modelMapper.map(SkillObj, SkillDto.class);
	}

	public SkillDto getBySkillId(Long SkillId) {
		Skill Skill = SkillRepository.findBySkillIdAndIsDeletedFalse(SkillId).orElseThrow(()->new PersistenceException("Skill not found"));
		return this.modelMapper.map(Skill, SkillDto.class);
	}

	public SkillDto softDelete(Long SkillId, String uid) {
		Skill skill = SkillRepository.findBySkillIdAndIsDeletedFalse(SkillId).orElseThrow(()->new PersistenceException("Skill not found"));
		skill.setIsDeleted(true);
		skill.setLastUpdateDate(LocalDateTime.now());
		skill.setLastUpdateBy(uid);
		Skill SkillObj = SkillRepository.save(skill);
		return this.modelMapper.map(SkillObj, SkillDto.class);
	}

}
