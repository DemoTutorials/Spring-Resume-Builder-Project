package com.avisys.microservice.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.avisys.microservice.entity.EmailTemplate;

public interface EmailTemplateRepository extends PagingAndSortingRepository<EmailTemplate, Long> {

	Page<EmailTemplate> findByNameContainingIgnoreCaseAndIsDeleted(String name, boolean deleted, Pageable pageable);

	Page<EmailTemplate> findByIsDeleted(boolean deleted, Pageable pageable);

	EmailTemplate findByIdAndIsDeleted(Long id, boolean deleted);

	List<EmailTemplate> findByIsDeleted(boolean deleted);
	
	Long countByNameAndVersion(String name,Integer version);
	
	EmailTemplate findTopByNameOrderByVersionDesc(String name);

}
