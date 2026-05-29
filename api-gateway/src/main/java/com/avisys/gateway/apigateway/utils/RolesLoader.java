package com.avisys.gateway.apigateway.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.stereotype.Component;

import com.avisys.gateway.apigateway.entity.Permission;

@Component
public class RolesLoader {
	
	@Autowired
	ServerHttpSecurity http;
	
	@Autowired
	PermissionRepository permissionRepository;
	
	public   Map<String, String> rolesUrl = new HashMap<>();
	
	@Bean("rolesLoaderBean")
	public void loadRoles() {
		
		System.out.println("get from DB");
		List<Permission> findAll = permissionRepository.findAll();
//		System.out.println(findAll);
//		
		for (Permission permission : findAll) {
			rolesUrl.put(permission.getApiEndpoints().getPath(), permission.getPermissionName());
		}
		
//		System.out.println("roles Loading");
//		rolesUrl.put("/service1/customer/all", "CUSTOMER-GET-ALL");
//		rolesUrl.put("/service1/customer/save", "CUSTOMER-SAVE");
//		rolesUrl.put("/service1/customer/update", "CUSTOMER-UPDATE");
//		rolesUrl.put("/service1/customer/delete/**", "CUSTOMER-DELETE");
	}

	@Bean("getRolesLoader")
	@DependsOn({"rolesLoaderBean"})
	public Map<String, String> getRolesAndUrl() {
		Map<String, String> rolesUrl1 = rolesUrl;
//		System.out.println("run url loader");
		return rolesUrl1;
	}

	public Map<String, String> loadRolesByAPI() {
		
//		rolesUrl.put("/service1/customer/all/**", "CUSTOMER-GET-ALL");
//		rolesUrl.put("/service1/customer/save/**", "CUSTOMER-SAVE");
		
		return rolesUrl;
	}

	
}
