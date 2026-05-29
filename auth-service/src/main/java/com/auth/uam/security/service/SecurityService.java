package com.auth.uam.security.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.auth.uam.entity.Roles;
import com.auth.uam.repository.RoleRepository;

@Service("securityService")
public class SecurityService {

//	@Autowired
//	RoleRepository roleRepository;
	
//	public String getPrivilege(){
//        return "USER";
//    }
	
//	for hasAnyRole
//public List<String> getPrivilege(){
//		
//		List<String> list = new ArrayList<>();
//		list.add("USER");
//		list.add("ADMIN");
//        return list;
//    }

	
//	for hasAnyAuthority
	
	
//public List<String> getPrivilege(String s){
//	
//	
//	List<String> list = new ArrayList<>();
//	list.add("ROLE_GET-ACCESS-ROLE");
//    return list;
//}
	
//	private static RoleRepository roleRepository;
//	public SecurityService(RoleRepository roleRepository) {
//		SecurityService.roleRepository = roleRepository;
//	}
	
//	private static Map<String, List<String>> roleHandlerMap = new HashMap<>();
	
//	public SecurityService() {
//		
//		roleHandlerMap.put("admin", getPrivilege("admin"));
////		System.out.println(roleHandlerMap);
//	}
	
	

//public List<String> getPrivilege(String roleName){
//	
//	List<Role> Rolelist = roleRepository.findByRoleName(roleName);
//	
//	List<String> list = new ArrayList<>();
//	
//	Rolelist.stream().forEach(p -> {	
//		list.add(p.getRole());
//	});
//	
//	
//	
//    return list;
//}

@Autowired
RolesLoader rolesLoader;

public List<String> getPrivilege(String roleName){

	System.out.println("rolekey"+roleName);
	List<String> list =	rolesLoader.getValue(roleName);
	
	System.out.println("getPrivilege"+list);
    return list;
}
	
	
//	public String getPrivilege(String roleName){
//
//		System.out.println("rolekey"+roleName);
//		String list =	RolesLoader.getValue(roleName);
//		
//		System.out.println("getPrivilege"+list);
//	    return list;
//	}
	
}
