package com.auth.uam.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.auth.uam.entity.Permission;

public interface PermissionRepository   extends JpaRepository<Permission, Long> {

	Page<Permission> findByPermissionNameLikeIgnoreCaseOrDescriptionLikeIgnoreCase(String string, String string2,
			Pageable pageable);

//	long countByGroupNameAndPath(String k, String string);

}
