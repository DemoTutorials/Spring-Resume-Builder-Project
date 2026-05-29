//package com.auth.uam.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//
//import com.auth.uam.config.SecurityConfig;
//import com.auth.uam.constant.CustomerControllerRoles;
//import com.auth.uam.security.service.RolesLoader;
//
//@RestController
//@RequestMapping("update")
//@CrossOrigin(origins = "*", allowedHeaders = "*")
//public class UpdateRoles {
//
//	@Autowired
//	RolesLoader rolesLoader;
////	
////	@Autowired
////	SecurityConfig SecurityConfig;
//	
//	@GetMapping("roles")
//	public ResponseEntity<String> updateRoles( ) throws Exception {
////		rolesLoader.roleLoader();
//		
////		System.out.println(AccessConstant.ALL);
////		HttpSecurity newh = null;
////		SecurityFilterChain securityFilterChain = SecurityConfig.securityFilterChain(newh);
//		return new ResponseEntity<>("Roles Updated", HttpStatus.OK);
//	}
//}
