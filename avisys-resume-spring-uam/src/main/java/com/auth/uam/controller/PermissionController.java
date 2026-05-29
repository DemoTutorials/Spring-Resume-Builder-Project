package com.auth.uam.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth.uam.entity.Permission;
import com.auth.uam.entity.Roles;
import com.auth.uam.service.PermissionService;

@RestController
@RequestMapping("permission")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PermissionController {
	

	@Autowired
	PermissionService permissionService;
	
	@GetMapping("all")
	public ResponseEntity<Page<Permission>> getSearchAndPagination(@Nullable String name,
			Pageable pageable) {
		
		Page<Permission> rolePage = permissionService.getSearchAndPagination(name, pageable);
		return new ResponseEntity<>(rolePage, HttpStatus.OK);
		
	}
	
	@GetMapping("list")
	public ResponseEntity<List<Permission>> getPermissionList() {

		List<Permission> listPermission = permissionService.getList();
		
		return new ResponseEntity<>(listPermission, HttpStatus.OK);
	}
	
	
	@GetMapping("{id}")
	public ResponseEntity<Permission> getById(@PathVariable(value = "id") Long id) {

		Optional<Permission> optionalPermission = permissionService.getById(id);
		if (optionalPermission.isPresent()) {
			return new ResponseEntity<>(optionalPermission.get(), HttpStatus.OK);
		}
		return new ResponseEntity<>(optionalPermission.get(), HttpStatus.OK);
	}
	
	@PostMapping("save")
	public ResponseEntity<Permission> save(@RequestBody Permission permission) {

		Permission savedPermission = permissionService.savePermission(permission);
		return new ResponseEntity<>(savedPermission, HttpStatus.OK);
	}
		
	@GetMapping("permission-not-assign-to-role")
	public ResponseEntity<List<Permission>> getPermissionListNotAssignToRole(Long roleId) {

		List<Permission> permissionList = permissionService.getPermissionListNotAssignToRole(roleId);
		return new ResponseEntity<>(permissionList, HttpStatus.OK);
	}
	
	@GetMapping("permission-assign-to-role")
	public ResponseEntity<List<Permission>> getPermissionListAssignToRole(Long roleId) {

		List<Permission> permissionList = permissionService.getPermissionListAssignToRole(roleId);
		return new ResponseEntity<>(permissionList, HttpStatus.OK);
	}
}
