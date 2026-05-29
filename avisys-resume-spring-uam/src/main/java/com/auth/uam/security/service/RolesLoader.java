package com.auth.uam.security.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.auth.uam.entity.Permission;
import com.auth.uam.entity.Roles;
import com.auth.uam.repository.PermissionRepository;
import com.auth.uam.repository.RoleRepository;

@Component
public class RolesLoader {

	// Inject Service or repository if you have.
	@Autowired
	PermissionRepository permissionRepository;
	/**
	 * Executes on application ready event Check's if data exists & calls to create
	 * or read data
	 */
	private  Map<String, List<String>> roleHandlerMap = new HashMap<>();

//	@Bean
//	public void roleLoader() {
//		// code here
//		roleHandlerMap = new HashMap<>() ;
//		List<Permission> permissionlist = permissionRepository.findAll();
//
//		permissionlist.stream().forEach(p -> {
//			List<String> roleList = new ArrayList<>();
//			roleList.add(p.getPermissionName());
//			roleHandlerMap.put(p.getPath(), roleList);
//		});
//		System.out.println(roleHandlerMap);
//	}

	public  List<String> getValue(String roleName) {
		List<String> list = roleHandlerMap.get(roleName);
		System.out.println("AppBootstrapListener" + list);

		return list;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
//	// Inject Service or repository if you have.
//		@Autowired
//		RoleRepository roleRepository;
//		/**
//		 * Executes on application ready event Check's if data exists & calls to create
//		 * or read data
//		 */
//		private static Map<String, String> roleHandlerMap = new HashMap<>();
//
//		
//		 @Bean("roleLoder")
//		public void roleLoader() {
//			// code here
//
//			List<Role> rolelist = roleRepository.findAll();
//
//			rolelist.stream().forEach(p -> {
////				List<String> roleList = new ArrayList<>();
////				roleList.add(p.getRole());
//				roleHandlerMap.put(p.getRoleName(), p.getRole());
//			});
//			System.out.println(roleHandlerMap);
//		}
//
//		
//		public static String getValue(String roleName) {
//			String list = roleHandlerMap.get(roleName);
//			System.out.println("AppBootstrapListener" + list);
//
//			return list;
//		}
}