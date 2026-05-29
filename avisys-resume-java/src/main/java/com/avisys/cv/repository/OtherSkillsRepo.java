package com.avisys.cv.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.avisys.cv.entity.OtherSkills;
import com.avisys.cv.entity.Person;

@Repository
public interface OtherSkillsRepo extends JpaRepository<OtherSkills, Long>{

	Optional<OtherSkills> findByPerson(Person person);
	
	Optional<OtherSkills> findByPersonOrderByLastUpdateDateAsc(Person person);

	Optional<OtherSkills> findByOtherSkillIdAndIsDeletedFalse(Long otherSkillId);

}
