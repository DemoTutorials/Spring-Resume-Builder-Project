package com.avisys.gateway.apigateway.utils;

import org.springframework.data.jpa.repository.JpaRepository;

import com.avisys.gateway.apigateway.entity.Permission;

public interface PermissionRepository   extends JpaRepository<Permission, Long> {

	

}
