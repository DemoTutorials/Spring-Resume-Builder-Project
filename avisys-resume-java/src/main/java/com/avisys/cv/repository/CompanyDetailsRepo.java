package com.avisys.cv.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.avisys.cv.entity.CompanyDetails;

@Repository
public interface CompanyDetailsRepo extends JpaRepository<CompanyDetails, Long> {

	Optional<CompanyDetails> findByCompanyIdAndIsDeletedFalse(Long id);

	CompanyDetails findByCompanyCodeAndIsDeletedFalse(String companyCode);

}
