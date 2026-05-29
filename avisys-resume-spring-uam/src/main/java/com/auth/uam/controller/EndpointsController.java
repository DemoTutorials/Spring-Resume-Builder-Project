package com.auth.uam.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth.uam.entity.APIEndpoints;
import com.auth.uam.entity.Permission;
import com.auth.uam.service.EndpointsService;



@RestController
@RequestMapping("endpoints")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class EndpointsController {
	


	@Autowired
	EndpointsService endpointsService;
	
	
	@GetMapping("all")
	public ResponseEntity<Page<APIEndpoints>> getSearchAndPagination(@Nullable String name,
			Pageable pageable) {
		
		Page<APIEndpoints> rolePage = endpointsService.getSearchAndPagination(name, pageable);
		return new ResponseEntity<>(rolePage, HttpStatus.OK);
		
	}
	
	@PostMapping("save")
	public ResponseEntity<String> saveEndpoints( @RequestBody List<APIEndpoints> endpoints) {
		
		endpointsService.saved(endpoints);
		
		return new ResponseEntity<>("done", HttpStatus.OK);
	}
	
//	@GetMapping("list")
//	public ResponseEntity<List<APIEndpoints>> getEndPointList() {
//
//		List<APIEndpoints> listAPIEndpoints = endpointsService.getList();
//		
//		return new ResponseEntity<>(listAPIEndpoints, HttpStatus.OK);
//	}
//	
//	@GetMapping("list-not-assign")
//	public ResponseEntity<List<APIEndpoints>> getEndPointNotAssign() {
//
//		List<APIEndpoints> listAPIEndpoints = endpointsService.getEndPointNotAssignList();
//		
//		return new ResponseEntity<>(listAPIEndpoints, HttpStatus.OK);
//	}
	
}
