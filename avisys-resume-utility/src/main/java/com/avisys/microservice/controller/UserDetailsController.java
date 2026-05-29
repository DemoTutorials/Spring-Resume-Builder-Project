package com.avisys.microservice.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.PersistenceException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.avisys.microservice.dto.User;
import com.avisys.microservice.entity.UserInfo;
import com.avisys.microservice.exception.ResourceNotFoundException;
import com.avisys.microservice.model.ResponseStatus;
import com.avisys.microservice.projection.EmailValidateDTO;
import com.avisys.microservice.projection.UserData;
import com.avisys.microservice.service.UserDetailsService;


@RestController
@RequestMapping("user-details")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserDetailsController {

	@Autowired
	UserDetailsService userService;
	
	private static final Logger log = LoggerFactory.getLogger(UserDetailsController.class);

	@GetMapping("user-list/{owner}")
	public ResponseEntity<List<UserData>> getByIdAndOwner( @PathVariable(value = "owner") String owner) {
		
		List<UserData> userList = userService.getListOfUsers(owner);
			return new ResponseEntity<>(userList, HttpStatus.OK);
	}

	@GetMapping("user-name-by-id/{id}")
	public ResponseEntity getUsernameById(@PathVariable String id) {
		return new ResponseEntity<>(userService.getUserNameById(id).getFullName(), HttpStatus.OK);
	}
	
	@GetMapping("all")
	public ResponseEntity<Page<UserInfo>> getByNameAndPagination(@Nullable String name, Pageable pageable) {
		Page<UserInfo> numberTypeDefinition = userService.getByNameAndPagination(name, pageable);
		return new ResponseEntity<>(numberTypeDefinition, HttpStatus.OK);
	}

	@GetMapping("active")
	public ResponseEntity<List<UserInfo>> getAllActiveUsers() {
		List<UserInfo> userDetailsList = userService.allActiveUsers();
		return new ResponseEntity<>(userDetailsList, HttpStatus.OK);
	}

	@PostMapping("assign-designation")
	public ResponseEntity<?> assignDesignation(@RequestParam Long id, @RequestParam Long designationId) {
		UserInfo userDetailsList = userService.assignDesignation(id, designationId);
		return new ResponseEntity<>(userDetailsList, HttpStatus.OK);
	}

	@GetMapping("{id}")
	public ResponseEntity<UserInfo> getById(@PathVariable Long id) {
		Optional<UserInfo> ntdOptional = userService.getById(id);
		if (ntdOptional.isPresent()) {
			return new ResponseEntity<>(ntdOptional.get(), HttpStatus.OK);
		} else {
			throw new ResourceNotFoundException("User not found.");
		}
	}
	
	@GetMapping("id/{id}")
	public ResponseEntity<UserInfo> getById(@PathVariable String id) {
		Optional<UserInfo> ntdOptional = userService.getByUserId(id);
		if (ntdOptional.isPresent()) {
			return new ResponseEntity<>(ntdOptional.get(), HttpStatus.OK);
		} else {
			throw new ResourceNotFoundException("User not found.");
		}
	}

	@GetMapping("userId/{userId}")
	public ResponseEntity getByUserId(@PathVariable String userId) {
		List<String> user = userService.getAllUserReportsTo(userId);
		return new ResponseEntity<>(user, HttpStatus.OK);

	}
	
	@PostMapping("assign-reportsTo")
	public ResponseEntity<Map<String, String>> assignReportsTo(@RequestBody List<String> userAssignment, @RequestParam String owner) {
		Map<String, String> assignReportsTo = userService.assignReportsTo(userAssignment, owner);
		return new ResponseEntity<>(assignReportsTo, HttpStatus.OK);
	}
	
	@PostMapping("de-assign-reportsTo")
	public ResponseEntity<Map<String, String>> deAssignReportsTo(@RequestBody List<String> userAssignment) {
		Map<String, String> assignReportsTo = userService.deAassignReportsTo(userAssignment);
		return new ResponseEntity<>(assignReportsTo, HttpStatus.OK);
	}

	@GetMapping("user-by-email/{email}")
	public ResponseEntity getByUserEmail(@PathVariable String email) {
		return new ResponseEntity<>(userService.getUserByEmail(email), HttpStatus.OK);

	}

	@PostMapping(path = "create")
	public ResponseEntity<UserInfo> userCreate(@RequestBody User user) {

		try {
			UserInfo userSaved = userService.save(user);
			return new ResponseEntity<>(userSaved, HttpStatus.OK);
		} catch (Exception e) {
			throw new PersistenceException("Failed saving User.", e);
		}
	}

	@PutMapping(path = "update")
	public ResponseEntity update(@RequestPart User user,@Nullable @RequestPart MultipartFile file) {
		try {
			UserInfo userUpdated = userService.update(user, file);
			return new ResponseEntity<>(userUpdated, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new ResponseStatus(HttpStatus.BAD_REQUEST.value(), e.getMessage()),
					HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping("softdelete/{email}")
	public ResponseEntity<UserInfo> softDelete(@PathVariable String email) {
		try {
			UserInfo numberTypeDefinition = userService.softDelete(email);
			return new ResponseEntity<>(numberTypeDefinition, HttpStatus.OK);
		} catch (Exception e) {
			throw new PersistenceException("Failed deleting User.", e);
		}
	}
	
	@GetMapping("validate-email/{email}")
	public ResponseEntity<?> validateEmailAddress(@PathVariable String email) {
		EmailValidateDTO emailValidateDTO = userService.validateEmailAddress(email);
		
			return new ResponseEntity<>(emailValidateDTO, HttpStatus.OK);
		
	}
}
