package com.avisys.microservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.avisys.microservice.entity.CommonMaster;
import com.avisys.microservice.exception.CommonMasterNotFoundException;
import com.avisys.microservice.jsonviews.Views;
import com.avisys.microservice.model.ResponseStatus;
import com.avisys.microservice.service.CommonMasterService;
import com.fasterxml.jackson.annotation.JsonView;

@RestController
@RequestMapping("/masters")
public class CommonMasterHibernateController {

	@Autowired
	private CommonMasterService commonMasterService;

	// Get all Parent Common Masters	
	
	@GetMapping("/value-by-code/{code}")
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@JsonView(Views.commonMasterValue.class)
	public ResponseEntity<CommonMaster> getCommonMasterValueByCode(@PathVariable String code) {
		CommonMaster commonMaster = commonMasterService.getCommonMasterbyCode(code);
		return new ResponseEntity<>(commonMaster, HttpStatus.OK);
	}

	@GetMapping("/parentCommonMaster")
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@JsonView(Views.commonMasterView.class)
	public ResponseEntity<Page<CommonMaster>> getAllCommonMasterParent(@Nullable String name, Pageable pageable) {
		Page<CommonMaster> parentCommonMasterPagination = commonMasterService.getByParentCommonMasterPagination(name,
				pageable);
		return new ResponseEntity<>(parentCommonMasterPagination, HttpStatus.OK);

	}

	// Get all parent common master based on mstName

	@GetMapping("/childCommonMaster/{foreignKey}")
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@JsonView(Views.commonMasterView.class)
	public Page<CommonMaster> getAllCommonMasterChild(@Nullable String name, @PathVariable String foreignKey,
			Pageable pageable) {

		return commonMasterService.getByChildCommonMasterPagination(name, foreignKey, pageable);
	}

	// Get common master by ID

	@GetMapping("/commonMasterById/{id}")
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@JsonView(Views.commonMaster.class)
	public ResponseEntity<CommonMaster> fetchCommonMasterById(@PathVariable Long id) {

		CommonMaster commonMaster = commonMasterService.getById(id);

		if (commonMaster != null) {
			return new ResponseEntity<>(commonMaster, HttpStatus.OK);
		} else {
			throw new CommonMasterNotFoundException("No such Common Master found with id: " + id);
		}
	}

	// Get all parent list

	@GetMapping("/parent-list")
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@JsonView(Views.commonMasterView.class)
	public ResponseEntity<List<?>> getParentCommonMaster() {
		List<CommonMaster> commonMasters = commonMasterService.getParentCommonMaster();
		return new ResponseEntity<>(commonMasters, HttpStatus.OK);
	}

	// Get parent record by foreign key

	@GetMapping("/parent-by-foreign-key")
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@JsonView(Views.commonMasterView.class)
	public ResponseEntity<?> getParentCommonMasterByForeignKey(@RequestParam(name = "key") String key) {
		CommonMaster commonMasters = commonMasterService.getParentCommonMasterByForeignKey(key);
		return new ResponseEntity<>(commonMasters, HttpStatus.OK);
	}

	// Get all common master based on foreign key

	@GetMapping("/commonMaster/{parent}")
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@JsonView(Views.commonMasterView.class)
	public ResponseEntity<List<?>> getCommonMasterData(@PathVariable String parent,
			@Nullable @RequestParam(name = "child") String child,
			@Nullable @RequestParam(name = "dataOf", defaultValue = "") String dataOf) {

		List<CommonMaster> commonMasters = commonMasterService.getByForeignKey(parent, child, dataOf);

		return new ResponseEntity<>(commonMasters, HttpStatus.OK);
	}

	// create common Master
	@PostMapping("/commonMaster")
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@JsonView(Views.commonMasterPost.class)
	public <T> ResponseEntity<T> postCommonMasterData(@RequestBody CommonMaster commonMaster,@RequestHeader HttpHeaders headers) {
		CommonMaster commonMasters = null;
		try {
			String userId = headers.getFirst("userId");
			System.out.println("headerValue: "+userId);
			commonMaster.setCreatedBy(userId);
			commonMaster.setUpdatedBy(userId);
			commonMasters = commonMasterService.create(commonMaster);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(new ResponseStatus(HttpStatus.CONFLICT.value(), e.getMessage()),
					HttpStatus.CONFLICT);
		}
		return new ResponseEntity(commonMasters, HttpStatus.OK);
	}

	// Update common master

	@PutMapping("/commonMaster")
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@JsonView(Views.commonMasterPut.class)
	public ResponseEntity<CommonMaster> putCommonMasterData(@RequestBody CommonMaster commonMaster) {

		CommonMaster commonMasters = null;
		try {
			commonMaster = commonMasterService.update(commonMaster);
		}catch(Exception e) {
			return new ResponseEntity(new ResponseStatus(HttpStatus.CONFLICT.value(), e.getMessage()),
					HttpStatus.CONFLICT);
		}
			return new ResponseEntity<>(commonMasters, HttpStatus.OK);
	}

	// Delete common master

	@DeleteMapping("/commonMaster/{id}/{updatedBy}")
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@JsonView(Views.commonMaster.class)
	public ResponseEntity<CommonMaster> deleteCommonMasterData(@PathVariable Long id, @PathVariable String updatedBy) {

		CommonMaster isdeleted = commonMasterService.delete(id, updatedBy);
		if (isdeleted != null)
			return new ResponseEntity<>(isdeleted, HttpStatus.OK);
		else
			throw new CommonMasterNotFoundException("Product not found with id: " + id);
	}

}
