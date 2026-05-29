package com.auth.uam.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.auth.uam.entity.APIEndpoints;

public interface EndpointsRepository extends JpaRepository<APIEndpoints, Long> {

	List<APIEndpoints> findByPermission(Object object);

	Page<APIEndpoints> findByMethodNameLikeIgnoreCaseOrControllerNameLikeIgnoreCaseOrPathLikeIgnoreCase(String string,
			String string2, String string3, Pageable pageable);

}
