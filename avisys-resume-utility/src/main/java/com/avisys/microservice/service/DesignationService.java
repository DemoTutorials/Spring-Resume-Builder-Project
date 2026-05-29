package com.avisys.microservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.avisys.microservice.dto.HierarchyResponse;
import com.avisys.microservice.entity.Designation;
import com.avisys.microservice.repository.DesignationRepository;

@Service
public class DesignationService {

	@Autowired
	DesignationRepository designationRepository;

	public Optional<Designation> getById(Long id) {
		return designationRepository.findByIdAndDeleted(id, false);
	}

	public Designation save(Designation designation) {
		Designation designationDetails = new Designation();
		designationDetails.setName(designation.getName());
		designationDetails.setParentId(designation.getParentId());
		designationDetails.setCreatedBy(designation.getCreatedBy());
		return designationRepository.save(designationDetails);
	}

	public Designation update(Designation designation) {
		Optional<Designation> findById = designationRepository.findById(designation.getId());

		Designation designation2 = new Designation();
		if (findById.isPresent())
			designation2 = findById.get();
		designation2.setName(designation.getName());
		designation2.setParentId(designation.getParentId());
		designation2.setUpdatedBy(designation.getUpdatedBy());

		return designationRepository.save(designation2);
	}

	public Designation softDelete(Long id, String updatedBy) {

		Optional<Designation> findById = designationRepository.findById(id);
		Designation designation = new Designation();
		if (findById.isPresent())
			designation = findById.get();

		designation.setDeleted(true);
		designation.setUpdatedBy(updatedBy);
		return designationRepository.save(designation);
	}

	public Page<Designation> getByNameAndPagination(String name, Pageable pageable) {
		Page<Designation> designation;
		if (ObjectUtils.isEmpty(name)) {
			designation = designationRepository.findByDeleted(false, pageable);
		} else {
			designation = designationRepository.findByNameContainingIgnoreCaseAndDeleted(name, false, pageable);
		}
		return designation;
	}

	public List<String> getAllUserReportsTo(String userId) {

		return designationRepository.getAllUsersById(userId);
	}

	public List<HierarchyResponse> getAllDesignationStructure(Long userId) {
		List<Designation> allUsers = null;

		allUsers = designationRepository.findByParentIdAndDeleted(userId, false);

		List<HierarchyResponse> hierarchyResponse = new ArrayList<>();

		for (Designation designation : allUsers) {
			if (designation.getParentId() == 0) {
				HierarchyResponse hierarchyResponse1 = new HierarchyResponse();
				hierarchyResponse1.setName(designation.getName());
				hierarchyResponse1.setId(designation.getId());
				hierarchyResponse1.setParentId(designation.getParentId());
				hierarchyResponse1.setChildren(new ArrayList<HierarchyResponse>());
				hierarchyResponse.add(hierarchyResponse1);
				break;
			} else {
				HierarchyResponse hierarchyResponse1 = new HierarchyResponse();
				hierarchyResponse1.setName(designation.getName());
				hierarchyResponse1.setId(designation.getId());
				hierarchyResponse1.setParentId(designation.getParentId());
				hierarchyResponse1.setChildren(new ArrayList<>());
				hierarchyResponse.add(hierarchyResponse1);
			}
		}

		return hierarchyResponse;
	}

}
