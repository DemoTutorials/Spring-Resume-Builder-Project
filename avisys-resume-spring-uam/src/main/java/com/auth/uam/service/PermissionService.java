package com.auth.uam.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.auth.uam.entity.Permission;
import com.auth.uam.entity.Roles;
import com.auth.uam.repository.PermissionRepository;

@Service
public class PermissionService {

	@Autowired
	PermissionRepository permissionRepository;

	@Autowired
	RoleService roleService;

	public Optional<Permission> getById(Long id) {

		return permissionRepository.findById(id);
	}

	public Permission savePermission(Permission permission) {

		return permissionRepository.save(permission);
	}

	public List<Permission> getList() {
		return permissionRepository.findAll();
	}

	public List<Permission> getPermissionListNotAssignToRole(Long roleId) {
		Optional<Roles> byId = roleService.getById(roleId);
		Roles roles = byId.get();
		List<Permission> rolePermission = roles.getPermission();

		List<Permission> findAllPermission = permissionRepository.findAll();
		for (Permission permission : rolePermission) {
			findAllPermission.remove(permission);
		}
		return findAllPermission;
	}

	public Page<Permission> getSearchAndPagination(String name, Pageable pageable) {
		// TODO Auto-generated method stub
		Page<Permission> permissionPage = null;
		if (ObjectUtils.isEmpty(name)) {
			permissionPage = permissionRepository.findAll(pageable);
		}else {
			permissionPage = permissionRepository.findByPermissionNameLikeIgnoreCaseOrDescriptionLikeIgnoreCase("%" + name + "%","%" + name + "%",pageable);
		}
		return permissionPage;
	}

	public List<Permission> getPermissionListAssignToRole(Long roleId) {
		Optional<Roles> byId = roleService.getById(roleId);
		if (byId.isPresent()) {
			
			return byId.get().getPermission();
		} else {
			throw new PersistenceException("Role not found.");
		}
		
	}
}
