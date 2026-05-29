package com.auth.uam.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javax.persistence.PersistenceException;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.auth.uam.dto.RoleAssignDTO;
import com.auth.uam.dto.RolesDTO;
import com.auth.uam.entity.Roles;
import com.auth.uam.entity.User;
import com.auth.uam.exception.ValidationException;
//import com.auth.uam.entity.RoleEntity;
import com.auth.uam.repository.RoleRepository;
import com.auth.uam.security.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class RoleService {

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	UserService userService;

	@Autowired
	ObjectMapper objectMapper;

	public Page<?> getSearchAndPagination(String name, Pageable pageable) {
		Page<?> rolePage = null;
		if (ObjectUtils.isEmpty(name)) {
			rolePage = roleRepository.findByAndIsDeleted(false, pageable);
		} else {
			rolePage = roleRepository.findRoleSearchAndIsDeleted("%" + name + "%", false, pageable);
		}
		return rolePage;
	}

	public Optional<Roles> getById(Long id) {
		return roleRepository.findByRoleIdAndIsDeletedFalse(id);
	}

	public List<?> getRoleListNotAssignToUser(Long userId) {
		Optional<User> byId = userService.getByIdUser(userId);

		if (byId.isPresent()) {

			User user = byId.get();
			List<Roles> userRoles = user.getRole();
			List<RoleAssignDTO> allRoleList = roleRepository.findAllNotAssign(false);

			Iterator<Roles> iterator = userRoles.iterator();
			while (iterator.hasNext()) {
				Long idToRemove = iterator.next().getRoleId();
				allRoleList.removeIf(item -> item.getRoleId() == idToRemove);
			}

			return allRoleList;
		} else {
			throw new PersistenceException("User not found.");
		}
	}

	public List<?> getRoleListAssignToUser(Long userId) {
		List<RoleAssignDTO> allRoleList = roleRepository.getRoleListAssignToUser(userId);

		return allRoleList;

	}

	public Roles saveRole(RolesDTO roleDto, String uid) {
		validateDuplicateRoleName(roleDto);
		roleDto.setCreationDate(LocalDateTime.now());
		roleDto.setLastUpdateDate(LocalDateTime.now());
		roleDto.setCreatedBy(uid);
		roleDto.setLastUpdateBy(uid);
		roleDto.setIsDeleted(false);
		Roles convertValue = objectMapper.convertValue(roleDto, Roles.class);
		return roleRepository.save(convertValue);
	}

	public Roles updateRole(RolesDTO roleDto, String uid) {
		Optional<Roles> findById = roleRepository.findById(roleDto.getRoleId());
		Roles existingRoles = findById.get();
		validateDuplicateRoleName(roleDto);
		existingRoles.setRoleName(roleDto.getRoleName());
		existingRoles.setDescription(roleDto.getDescription());
		existingRoles.setLastUpdateBy(uid);
		existingRoles.setLastUpdateDate(LocalDateTime.now());
		existingRoles.setPermission(roleDto.getPermission());
		return roleRepository.save(existingRoles);
	}

	public Roles softDelete(Long id, String uid) {
		Optional<Roles> findByRoleIdAndIsDeletedFalse = roleRepository.findByRoleIdAndIsDeletedFalse(id);

		Roles roles = findByRoleIdAndIsDeletedFalse.get();
		roles.setIsDeleted(true);
		roles.setLastUpdateBy(uid);
		roles.setLastUpdateDate(LocalDateTime.now());
		return roleRepository.save(roles);
	}

	public void validateDuplicateRoleName(RolesDTO roleDto) {
		Optional<Roles> getByUserName = roleRepository.findByRoleNameIgnoreCase(roleDto.getRoleName().toLowerCase());
		if (getByUserName.isPresent()) {
			if (roleDto.getRoleId() == null) {
				String errorMessage = StringUtils.EMPTY;
				if (getByUserName.get().getIsDeleted()) {
					errorMessage = "Role Name is already present but is marked deleted";
				} else {
					errorMessage = "Role Name is already present";
				}
				throw new ValidationException(errorMessage);
			} else {
				if (!roleDto.getRoleId().equals(getByUserName.get().getRoleId())) {
					throw new ValidationException("Role Name is already in used");
				}
			}
		}
	}
}
