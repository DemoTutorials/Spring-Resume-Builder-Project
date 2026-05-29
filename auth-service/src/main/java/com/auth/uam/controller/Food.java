package com.auth.uam.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@RequestMapping("food-service")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class Food {
	

//	@GetMapping("test/all")
//	public ResponseEntity<String> validateToken(@RequestParam String token) {
//
//	
//		return new ResponseEntity<>("token validate", HttpStatus.OK);
//	}
}
