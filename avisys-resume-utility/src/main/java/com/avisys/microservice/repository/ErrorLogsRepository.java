package com.avisys.microservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.avisys.microservice.entity.ErrorLogs;

public interface ErrorLogsRepository extends JpaRepository<ErrorLogs, Long> {
	
	Page<ErrorLogs> findByModuleEqualsIgnoreCaseAndMessageContainingIgnoreCase(String module,String message,Pageable pageable);
	
	Page<ErrorLogs> findAll(Pageable pageable);
	
	Page<ErrorLogs> findByMessageContainingIgnoreCase(String message, Pageable pageable);

}
