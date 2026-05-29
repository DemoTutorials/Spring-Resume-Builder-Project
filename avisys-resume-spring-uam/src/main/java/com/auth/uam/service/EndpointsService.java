package com.auth.uam.service;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.PersistenceException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.auth.uam.entity.APIEndpoints;
import com.auth.uam.entity.Permission;
import com.auth.uam.repository.EndpointsRepository;

@Service
public class EndpointsService {

	private static final Logger logger = LoggerFactory.getLogger(EndpointsService.class);

	@Autowired
	EndpointsRepository endpointsRepository;

	@Autowired
	PermissionService permissionService;

	public void saved(List<APIEndpoints> endpoints) {
		endpoints.forEach(list -> {

			try {
				APIEndpoints savedEndpoint = endpointsRepository.save(list);
				try {
				Permission permission = new Permission();
				permission.setPermissionName(extractControllerName(list.getControllerName())+"-"+list.getMethodName()+"-"+extractPath(list.getPath()));
				permission.setApiEndpoints(savedEndpoint);
				permission.setCreationDate(LocalDateTime.now());
				permission.setDescription(list.getMethodName()+" "+list.getControllerName()+" API");
				permissionService.savePermission(permission);
				} catch (Exception e) {
					throw new PersistenceException(e.getMessage());
				}
			} catch (Exception e) {


			}
		});
	}
	
	private String extractPath(String path) {
	    String[] parts = path.split("/");
	    String lastPart = parts[parts.length - 1].toUpperCase();
	    return lastPart;
	}

	private String extractControllerName(String controllerName) {
	    if (controllerName.endsWith("Controller")) {
	        return controllerName.substring(0, controllerName.length() - "Controller".length()).toUpperCase();
	    }
	    return controllerName.toUpperCase();
	}
	
	public List<APIEndpoints> getList() {

		return endpointsRepository.findAll();
	}

	public List<APIEndpoints> getEndPointNotAssignList() {

		return endpointsRepository.findByPermission(null);
	}

	public Page<APIEndpoints> getSearchAndPagination(String name, Pageable pageable) {
		Page<APIEndpoints> aPIEndpointsPage = null;
		if (ObjectUtils.isEmpty(name)) {
			aPIEndpointsPage = endpointsRepository.findAll(pageable);
		} else {
			aPIEndpointsPage = endpointsRepository
					.findByMethodNameLikeIgnoreCaseOrControllerNameLikeIgnoreCaseOrPathLikeIgnoreCase("%" + name + "%",
							"%" + name + "%", "%" + name + "%", pageable);
		}
		return aPIEndpointsPage;
	}

}
