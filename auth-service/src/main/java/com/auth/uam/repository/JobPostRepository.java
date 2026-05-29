package com.auth.uam.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.auth.uam.entity.JobPost;

public interface JobPostRepository extends JpaRepository<JobPost, Long> {
    List<JobPost> findByCompanyCode(String companyCode);
    
    @Query("SELECT DISTINCT j FROM JobPost j WHERE " +
    	       "(LOWER(j.jobPostCode) LIKE :key OR LOWER(j.jobProfile) LIKE :key) " +
    	       "AND j.isDeleted = false")
    Page<JobPost> searchJobPostCode(Pageable pageable, @Param("key") String key);
    
    Optional<JobPost> findByJobPostCodeAndIsDeletedFalse(String jobPostCode);

    
}

