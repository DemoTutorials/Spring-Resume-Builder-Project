package com.auth.uam.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.auth.uam.entity.Roles;

public interface RoleRepository extends JpaRepository<Roles, Long> {

	List<Roles> findByRoleName(String roleName);

	Page<Roles> findByRoleNameLikeIgnoreCaseOrDescriptionLikeIgnoreCase(String string, String string2,
			Pageable pageable);

}
