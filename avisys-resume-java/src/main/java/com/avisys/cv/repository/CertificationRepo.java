package com.avisys.cv.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.avisys.cv.entity.Certification;
import com.avisys.cv.entity.Person;

@Repository
public interface CertificationRepo extends JpaRepository<Certification, Long> {
	
	List<Certification> findByPersonAndIsDeletedFalseOrderByLastUpdateDateAsc(Person person);

	List<Certification> findByPersonAndIsDeletedFalse(Person person);

	Optional<Certification> findByCertIdAndIsDeletedFalse(Long certId);

}
