package com.avisys.cv.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.avisys.cv.entity.Education;
import com.avisys.cv.entity.Person;

@Repository
public interface EducationRepo extends JpaRepository<Education, Long> {

	Optional<Education> findByEduIdAndIsDeletedFalse(Long id);

	@Query("SELECT e FROM Education e WHERE e.person = :person AND e.isDeleted = false ORDER BY e.creationDate DESC")
    List<Education> findByPersonAndIsDeletedFalseOrderByCreatedDateDesc(@Param("person") Person person);

	@Query("SELECT new com.avisys.cv.entity.Education(e.eduId, e.person, e.qualification, cm1.value as qualificationValue, e.stream, e.institution, e.completionYear, e.city, e.state, cm2.value as stateValue, e.country, cm3.value as countryValue, e.description) " +
	        "FROM Education e " +
	        "LEFT JOIN CommonMaster cm1 ON cm1.code = e.qualification " +
	        "LEFT JOIN CommonMaster cm2 ON cm2.code = e.state " +
	        "LEFT JOIN CommonMaster cm3 ON cm3.code = e.country " +
	        "WHERE e.person = :person AND e.isDeleted = false and cm1.deleted = false and cm2.deleted = false and cm3.deleted = false ORDER BY e.creationDate DESC")
	List<Education> EducationDataByPerson(@Param("person") Person person);

}
