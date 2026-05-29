package com.auth.uam.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth.uam.constant.CustomerControllerRoles;
import com.auth.uam.entity.Permission;
import com.auth.uam.entity.Roles;
import com.auth.uam.entity.User;
import com.auth.uam.repository.PermissionRepository;
import com.auth.uam.repository.RoleRepository;

@RestController
@RequestMapping("metadata")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class MetadataController {

	@Autowired
	PermissionRepository permissionRepository;

	@GetMapping("get-controller-role-key")
	public ResponseEntity<Map<String, List<String>>> getSearchAndPagination(@Nullable String name, Pageable pageable) {

//		Step 1 : Add keys
		
		/* Map<GroupName, List<RoleKey>> */
		Map<String, List<String>> roleHandlerMap = new HashMap<String, List<String>>();
		roleHandlerMap.put("AccessController", CustomerControllerRoles.get());
//		roleHandlerMap.put("CustomerController", CustomerControllerRoles.get());
//		roleHandlerMap.put("AddressController", AddressControllerRoles.get());

//		Step 2 : Get keys list which is registered in DB
		List<Permission> allPermission = permissionRepository.findAll();
		
		
		
//		Map<String, List<String>> newRoleHandlerMap = new HashMap<>();
		
//		roleHandlerMap.forEach((k,v)->{
//			List<String> newList = new ArrayList<>(); 
//			v.forEach(l->{
//				
////				long  count =  allPermission.stream()
////						  .filter(p -> !l.contentEquals(p.getPermissionKey())).count();
////				if (count==0) {
////					newList.add(l);
////				}
//				
//				allPermission.forEach(p->{
//					
//					if (!l.contentEquals(p.getPermissionKey())) {
//						newList.add(l);
//					}
//				});
//				
//				
//				
//			});
//			newRoleHandlerMap.put(k, newList);
//		});
//		
		
		
		
		Map<String, List<String>> newMap =new HashMap<>();
		roleHandlerMap.forEach((k, v) -> {
			List<String> newList = new ArrayList<>();
			for (String string : v) {
//				long role = permissionRepository.countByGroupNameAndPermissionKey(k, string);
				long role = 0;
				System.out.println(role);
				if (role == 0) {
					newList.add(string);
				}
			}
			
			newMap.put(k, newList);
		});

		return new ResponseEntity<>(newMap, HttpStatus.OK);
	}
}
