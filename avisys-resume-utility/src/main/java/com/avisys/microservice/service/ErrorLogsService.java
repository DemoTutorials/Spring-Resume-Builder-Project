package com.avisys.microservice.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.avisys.microservice.entity.ErrorLogs;
import com.avisys.microservice.repository.ErrorLogsRepository;

@Service
public class ErrorLogsService {
	
	@Autowired
	private ErrorLogsRepository errorLogsRepository;
	
	public Page<ErrorLogs> getApplicationLogs(String type, String name,Pageable pageable) {
		
		if (type.equalsIgnoreCase("all")) {
			if(!ObjectUtils.isEmpty(name)) {
				return errorLogsRepository.findByMessageContainingIgnoreCase(name, pageable);
			}
			return errorLogsRepository.findAll(pageable);
        }
		else if(!ObjectUtils.isEmpty(type)) {
			return errorLogsRepository.findByModuleEqualsIgnoreCaseAndMessageContainingIgnoreCase(type,name,pageable);
		}else {
			return errorLogsRepository.findAll(pageable);
		}
	}

	public ErrorLogs save(ErrorLogs errorLogs) {
		return errorLogsRepository.save(errorLogs);
	}

	public Optional<ErrorLogs> getById(Long id) {
		 return errorLogsRepository.findById(id);
	}

}
