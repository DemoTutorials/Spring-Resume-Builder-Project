package com.avisys.microservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.avisys.microservice.entity.EmailDetails;

public interface EmailDetailsRepo extends JpaRepository<EmailDetails, String> {
	
	Page<EmailDetails> findByJobGroupContainingIgnoreCase(String name, Pageable page);
	
	EmailDetails findByJobNameContainingIgnoreCase(String name);
}
