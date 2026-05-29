package com.avisys.cv.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.avisys.cv.entity.Person;
import com.avisys.cv.entity.Skill;

@Repository
public interface SkillRepo extends JpaRepository<Skill, Long> {

	List<Skill> findByPersonAndIsDeletedFalseOrderByLastUpdateDateAsc(Person person);
	
	List<Skill> findByPersonAndIsDeletedFalse(Person person);

	Optional<Skill> findBySkillIdAndIsDeletedFalse(Long skillId);

	@Query("SELECT new com.avisys.cv.entity.Skill(s.skillId, s.person, s.skillName, cm1.value as skillValue) " +
	        "FROM Skill s " +
	        "LEFT JOIN CommonMaster cm1 ON cm1.code = s.skillName " +
	        "WHERE s.person = :person AND s.isDeleted = false " +
	        "ORDER BY s.lastUpdateDate ASC")
	List<Skill> SkillDataByPerson(Person person);

}
