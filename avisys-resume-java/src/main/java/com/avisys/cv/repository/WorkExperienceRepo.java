package com.avisys.cv.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.avisys.cv.entity.Person;
import com.avisys.cv.entity.WorkExperience;

@Repository
public interface WorkExperienceRepo extends JpaRepository<WorkExperience, Long> {

	Optional<WorkExperience> findByExpIdAndIsDeletedFalse(Long id);

	@Query("SELECT w FROM WorkExperience w WHERE w.person = :person AND w.isDeleted = false ORDER BY w.creationDate DESC")
	List<WorkExperience> findByPersonAndIsDeletedFalseOrderByCreatedDateDesc(@Param("person") Person person);

	@Query("SELECT new com.avisys.cv.entity.WorkExperience(we.expId, we.person, we.company, we.clientName, we.position, we.startDate, we.endDate, we.description, we.city, we.state, cm2.value as stateValue, we.country, cm3.value as countryValue, we.domain, cm1.value as domainValue, we.yearsOfExperience) " +
	        "FROM WorkExperience we " +
	        "LEFT JOIN CommonMaster cm1 ON cm1.code = we.domain " +
	        "LEFT JOIN CommonMaster cm2 ON cm2.code = we.state " +
	        "LEFT JOIN CommonMaster cm3 ON cm3.code = we.country " +
	        "WHERE we.person = :person AND we.isDeleted = false ORDER BY we.creationDate DESC")
	List<WorkExperience> ExperiencDataByPerson(Person person);
	
	@Query("SELECT SUM(we.yearsOfExperience) FROM WorkExperience we WHERE we.person.id = :personId")
	Float getTotalYearsOfExperience(@Param("personId") Long personId);



}
