package com.avisys.gateway.apigateway.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.DefaultSingletonBeanRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.avisys.gateway.apigateway.ApiGatewayApplication;
import com.avisys.gateway.apigateway.config.SecurityConfig;
import com.avisys.gateway.apigateway.utils.RolesLoader;

@RestController
@RequestMapping("restart")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class RestartController {

	@Autowired
	ApplicationContext applicationContext;

	@Autowired
	RolesLoader rolesLoader;

//	@GetMapping("get-all-permission")
//	public ResponseEntity<Map<String, String>> all() throws Exception {
//
//		Map<String, String> loadRolesByAPI = rolesLoader.loadRolesByAPI();
//		rolesLoader.getRolesAndUrl();
//		return new ResponseEntity<>(loadRolesByAPI, HttpStatus.OK);
//	}

	@PostMapping("/gateway-service")
	public ResponseEntity<String> restart() {
		ApiGatewayApplication.restart();
		return new ResponseEntity<>("API-Gateway service restart.", HttpStatus.OK);
	}

}
