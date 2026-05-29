package com.avisys.microservice.controller;

import java.util.List;
import java.util.Optional;

import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.avisys.microservice.dto.HierarchyResponse;
import com.avisys.microservice.entity.Designation;
import com.avisys.microservice.exception.ResourceNotFoundException;
import com.avisys.microservice.service.DesignationService;

@RestController
@RequestMapping("designation")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class DesignationController {

	@Autowired
	private DesignationService designationService;

	@PostMapping(path = "create")
	public ResponseEntity<Designation> userCreate(@RequestBody Designation designation) {

		try {
			Designation designationSaved = designationService.save(designation);
			return new ResponseEntity<>(designationSaved, HttpStatus.OK);
		} catch (Exception e) {
			throw new PersistenceException("Failed saving Designation.", e);
		}
	}

	@PutMapping(path = "update")
	public ResponseEntity<Designation> update(@RequestBody Designation designation) {
		try {
			Designation designationUpdated = designationService.update(designation);
			return new ResponseEntity<>(designationUpdated, HttpStatus.OK);
		} catch (Exception e) {
			throw new PersistenceException("Failed updating Designation.", e);
		}
	}

	@GetMapping("root-desgination")
	public ResponseEntity<List<HierarchyResponse>> getParentUser() {
		List<HierarchyResponse> designationHierarchyResponse = designationService.getAllDesignationStructure(0L);
		return new ResponseEntity<>(designationHierarchyResponse, HttpStatus.OK);
	}

	@GetMapping("node-desgination/{id}")
	public ResponseEntity<List<HierarchyResponse>> getChildUser(@PathVariable Long id) {
		List<HierarchyResponse> designationHierarchyResponse = designationService.getAllDesignationStructure(id);
		return new ResponseEntity<>(designationHierarchyResponse, HttpStatus.OK);
	}

	@GetMapping("{id}")
	public ResponseEntity<Designation> getById(@PathVariable Long id) {
		Optional<Designation> ntdOptional = designationService.getById(id);
		if (ntdOptional.isPresent()) {
			return new ResponseEntity<>(ntdOptional.get(), HttpStatus.OK);
		} else {
			throw new ResourceNotFoundException("Designation not found.");
		}
	}

	@DeleteMapping("softdelete/{id}/{updatedBy}")
	public ResponseEntity<Designation> softDelete(@PathVariable Long id, @PathVariable String updatedBy) {
		try {
			Designation designation = designationService.softDelete(id, updatedBy);
			return new ResponseEntity<>(designation, HttpStatus.OK);
		} catch (Exception e) {
			throw new PersistenceException("Failed deleting Designation.", e);
		}
	}

}
