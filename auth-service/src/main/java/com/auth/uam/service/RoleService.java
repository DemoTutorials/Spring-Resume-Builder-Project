package com.auth.uam.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.auth.uam.entity.Roles;
import com.auth.uam.entity.User;
//import com.auth.uam.entity.RoleEntity;
import com.auth.uam.repository.RoleRepository;
import com.auth.uam.security.service.UserService;

@Service
public class RoleService {

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	UserService userService;

	public Page<Roles> getSearchAndPagination(String name, Pageable pageable) {
		Page<Roles> rolePage = null;
		if (ObjectUtils.isEmpty(name)) {
			rolePage = roleRepository.findAll(pageable);
		}else {
			rolePage = roleRepository.findByRoleNameLikeIgnoreCaseOrDescriptionLikeIgnoreCase("%" + name + "%","%" + name + "%",pageable);
		}
		return rolePage;
	}

	
	public Optional<Roles> getById(Long id) {
		return roleRepository.findById(id);
	}

	

	public Roles saveRole(Roles role) {
		return roleRepository.save(role);
	}

	public Roles updateRole(Roles role) {
		Optional<Roles> findById = roleRepository.findById(role.getRoleId());
		Roles existingRoles = findById.get();
		existingRoles.setPermission(role.getPermission());
		return roleRepository.save(existingRoles);
	}

	
}
