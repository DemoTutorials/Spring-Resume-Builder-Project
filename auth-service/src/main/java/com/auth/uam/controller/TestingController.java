package com.auth.uam.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.context.annotation.Bean;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.auth.uam.constant.CustomerControllerRoles;
import com.auth.uam.enums.RolesKeyEnums;
import com.auth.uam.security.service.SecurityService;

@RestController
@RequestMapping("testing")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class TestingController {

	@GetMapping("all")
	@PreAuthorize("hasAnyAuthority(@securityService.getPrivilege(#roleKey))")
	public ResponseEntity<String> all(@Value(CustomerControllerRoles.GETALL) String roleKey) {

		return new ResponseEntity<>("get all API", HttpStatus.OK);
	}

	@PostMapping("save")
	@PreAuthorize("hasAnyAuthority(@securityService.getPrivilege(#roleKey))")
	public ResponseEntity<String> post(@Value(CustomerControllerRoles.SAVE) String roleKey) {

		return new ResponseEntity<>("save post API", HttpStatus.OK);
	}

	@PutMapping("update")
	@PreAuthorize("hasAnyAuthority(@securityService.getPrivilege(#roleKey))")
	public ResponseEntity<String> put(@Value(CustomerControllerRoles.UPDATE) String roleKey) {

		return new ResponseEntity<>("update put API", HttpStatus.OK);
	}

	@DeleteMapping("delete")
	@PreAuthorize("hasAnyAuthority(@securityService.getPrivilege(#roleKey))")
	public ResponseEntity<String> delete(@Value(CustomerControllerRoles.DELETE) String roleKey) {

		return new ResponseEntity<>("delete  API", HttpStatus.OK);
	}

	@GetMapping("get-role-key")
//	public ResponseEntity<List<String>> getRoleKey() {
	public ResponseEntity<List<RolesKeyEnums>> getRoleKey() {

//		RolesKeyEnums enumValue = null ;
//		Object[] possibleValues = RolesKeyEnums.getDeclaringClass().getEnumConstants();
//		System.out.println(possibleValues);

		List<RolesKeyEnums> enumValues = Arrays.asList(RolesKeyEnums.values());
		List<String> list = new ArrayList<>();
//		list.add(GETALL);
//		list.add(SAVE);
//		list.add(UPDATE);
//		list.add(DELETE);
		return new ResponseEntity<>(enumValues, HttpStatus.OK);

	}
}
