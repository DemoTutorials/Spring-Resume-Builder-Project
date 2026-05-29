package com.avisys.microservice.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.avisys.microservice.entity.ErrorLogs;
import com.avisys.microservice.exception.ResourceNotFoundExceptionGeneric;
import com.avisys.microservice.model.ResponseStatus;
import com.avisys.microservice.service.ErrorLogsService;

@RestController
@RequestMapping("log")
public class ErrorLogsController {

	@Autowired
	private ErrorLogsService errorLogsService;

	@GetMapping(path = "all")
	public ResponseEntity<?> getMailListByRequestId(@Nullable @RequestParam(name = "type") String type,
			@Nullable @RequestParam(name="name",defaultValue = " ") String name,Pageable pageable) {

		Page<ErrorLogs> errorLog = errorLogsService.getApplicationLogs(type,name,pageable);
		return new ResponseEntity<>(errorLog, HttpStatus.OK);

	}

	@PostMapping(path = "create")
	public ResponseEntity<?> create(@RequestBody ErrorLogs errorLogs) {
		try {

			ErrorLogs save = errorLogsService.save(errorLogs);
			return new ResponseEntity<>(save, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new ResponseStatus(HttpStatus.BAD_REQUEST.value(), e.getMessage()),
					HttpStatus.OK);
		}
	}

	@GetMapping("{id}")
	public ResponseEntity<ErrorLogs> getById(@PathVariable(value = "id") Long id) {
		Optional<ErrorLogs> mailOptional = errorLogsService.getById(id);
		if (mailOptional.isPresent()) {
			return new ResponseEntity<>(mailOptional.get(), HttpStatus.OK);
		} else {
			throw new ResourceNotFoundExceptionGeneric("No log found with specified id");
		}
	}

}
