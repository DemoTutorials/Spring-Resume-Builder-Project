package com.auth.uam.controller;

import java.util.List;
import java.util.Optional;

import javax.persistence.PersistenceException;
import javax.validation.Valid;

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
import org.springframework.web.bind.annotation.RestController;

import com.auth.uam.dto.RolesDTO;
import com.auth.uam.entity.Roles;
import com.auth.uam.exception.ValidationException;
import com.auth.uam.service.RoleService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("role")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class RoleController {

	@Autowired
	RoleService roleService;
	
	@Autowired
	ObjectMapper objectMapper;
	
	@GetMapping("all")
	public ResponseEntity<Page<?>> getSearchAndPagination(@Nullable String name, Pageable pageable) {
		try {
			Page<?> rolePage = roleService.getSearchAndPagination(name, pageable);
			return new ResponseEntity<>(rolePage, HttpStatus.OK);
		} catch (Exception e) {
			throw new PersistenceException(e.getMessage());
		}
	}

	@GetMapping("{id}")
	public ResponseEntity<Roles> getById(@PathVariable(value = "id") Long id) {

		Optional<Roles> optionalRole = roleService.getById(id);
		if (optionalRole.isPresent()) {
			return new ResponseEntity<>(optionalRole.get(), HttpStatus.OK);
		} else throw new PersistenceException("Record not fond");
	}

	@GetMapping("role-not-assign-to-user")
	public ResponseEntity<List<?>> getRoleListNotAssignToUser(Long userId) {
		try {
			List<?> roleList = roleService.getRoleListNotAssignToUser(userId);
			return new ResponseEntity<>(roleList, HttpStatus.OK);
		} catch (Exception e) {
			throw new PersistenceException(e.getMessage());
		}
	}
	
	@GetMapping("role-assign-to-user")
	public ResponseEntity<List<?>> getRoleListAssignToUser(Long userId) {
		try {
			List<?> roleList = roleService.getRoleListAssignToUser(userId);
			return new ResponseEntity<>(roleList, HttpStatus.OK);
		} catch (Exception e) {
			throw new PersistenceException(e.getMessage());
		}
	}

	@PostMapping("save")
	public ResponseEntity<Roles> save(@Valid @RequestBody RolesDTO roleDto,@RequestHeader HttpHeaders headers) {
		try {
			String userId = headers.getFirst("userId");
			System.out.println("headerValue: "+userId);
			Roles savedRole = roleService.saveRole(roleDto, userId);
			return new ResponseEntity<>(savedRole, HttpStatus.OK);
		} catch (ValidationException e) {
			throw e;
		} catch (Exception e) {
			throw new PersistenceException(e.getMessage());
		}
	}

	@PutMapping("update")
	public ResponseEntity<Roles> update(@Valid @RequestBody RolesDTO roleDto,@RequestHeader HttpHeaders headers) {
		try {
			String userId = headers.getFirst("userId");
			System.out.println("headerValue: "+userId);
			Roles savedRole = roleService.updateRole(roleDto, userId);
			return new ResponseEntity<>(savedRole, HttpStatus.OK);
		} catch (ValidationException e) {
			throw e;
		} catch (Exception e) {
			throw new PersistenceException(e.getMessage());
		}
	}
	
	@DeleteMapping("soft-delete")
	public ResponseEntity<Roles> softDelete(Long id,@RequestHeader HttpHeaders headers ) {
		try {
			String userId = headers.getFirst("userId");
			System.out.println("headerValue: "+userId);
			Roles deletedRole = roleService.softDelete(id, userId);
			return new ResponseEntity<>(deletedRole, HttpStatus.OK);
		} catch (Exception e) {
			throw new PersistenceException(e.getMessage());
		}
	}

}
