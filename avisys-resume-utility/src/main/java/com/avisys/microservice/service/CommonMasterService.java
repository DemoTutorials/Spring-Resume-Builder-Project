package com.avisys.microservice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.avisys.microservice.entity.CommonMaster;
import com.avisys.microservice.repository.CommonMasterRepository;

@Service
public class CommonMasterService {

	@Autowired
	CommonMasterRepository commonMasterRepository;

	public CommonMaster create(CommonMaster commonMaster) throws Exception {

		List<CommonMaster> checkIfAlreadyExists;
		if (commonMaster.getMst()) {
	        List<CommonMaster> checkIfNameAndMstExists = commonMasterRepository
	                .findByMstNameEqualsIgnoreCaseAndMstAndDeleted(commonMaster.getMstName(), commonMaster.getMst(), false);

	        if (!checkIfNameAndMstExists.isEmpty()) {
	            throw new Exception("Master name already present");
	        }
	        
	        if(!commonMasterRepository
	                .findByCodeEqualsIgnoreCaseAndValueEqualsIgnoreCaseAndMstAndDeleted(commonMaster.getCode(), commonMaster.getValue(), commonMaster.getMst(), false)
	                .isEmpty()) {
	        	throw new Exception("Code and Value with same name already present");
	        }
	        
		} else {
			if (!commonMasterRepository.findByCodeEqualsIgnoreCaseAndValueEqualsIgnoreCaseAndMstAndDeleted(
					commonMaster.getCode(), commonMaster.getValue(), commonMaster.getMst(), false).isEmpty()) {
				throw new Exception("Code and Value with same name already present");
			}

		}
		
		if (!commonMaster.getForeignKey().isEmpty() && commonMaster.getForeignKey() != null) {
			checkIfAlreadyExists = commonMasterRepository
					.findByCodeEqualsIgnoreCaseAndForeignKeyEqualsIgnoreCaseAndMstNameEqualsIgnoreCaseAndDeleted(
							commonMaster.getCode(), commonMaster.getForeignKey(), commonMaster.getMstName(), false);
		} else {
			checkIfAlreadyExists = commonMasterRepository.findByCodeEqualsIgnoreCaseAndDeleted(commonMaster.getCode(),
					false);
		}
		if (checkIfAlreadyExists.isEmpty()) {
			return commonMasterRepository.save(commonMaster);
		} else {
			throw new Exception("Conflict while saving the data. Data with the same Code already exists");
		}
	}

	public List<CommonMaster> getParentCommonMaster() {
		return commonMasterRepository.findByMstAndDeleted(true, false);
	}

	public List<CommonMaster> getByForeignKey(String foreignKey, String parent, String dataOf) {
		if (ObjectUtils.isEmpty(parent)) {
			return commonMasterRepository
					.findByMstNameIgnoreCaseAndAdditionalValueContainingIgnoreCaseAndMstAndDeletedOrderByPriorityAscValueAsc(foreignKey,
							dataOf, false, false);
		}
		return commonMasterRepository
				.findByForeignKeyContainingIgnoreCaseAndMstNameContainingIgnoreCaseAndAdditionalValueContainingIgnoreCaseAndDeletedOrderByPriorityAscValueAsc(
						foreignKey, parent, dataOf, false);
	}

	public CommonMaster update(CommonMaster commonMaster) throws Exception {
		CommonMaster existingCommonMaster = commonMasterRepository.findById(commonMaster.getCommonMstId()).get();
		if(!commonMasterRepository.findByValueEqualsIgnoreCaseAndMstAndDeleted(commonMaster.getValue(), commonMaster.getMst(), false).isEmpty()){
			throw new Exception("Value already present");
		}
		existingCommonMaster.setMstName(commonMaster.getMstName());
		existingCommonMaster.setCode(commonMaster.getCode());
		existingCommonMaster.setValue(commonMaster.getValue());
		existingCommonMaster.setForeignKey(commonMaster.getForeignKey());
		existingCommonMaster.setMst(commonMaster.getMst());
		existingCommonMaster.setUpdatedBy(commonMaster.getUpdatedBy());
		existingCommonMaster.setPriority(commonMaster.getPriority());
		existingCommonMaster.setadditionalValue(commonMaster.getadditionalValue());

		return commonMasterRepository.save(existingCommonMaster);

	}

	public CommonMaster getById(Long id) {
		Optional<CommonMaster> findById = commonMasterRepository.findById(id);
		if (findById.isPresent()) {
			return findById.get();
		}
		return null;
	}

	public CommonMaster delete(Long id, String updatedBy) {

		Optional<CommonMaster> existingCommonMaster = commonMasterRepository.findById(id);
		if (existingCommonMaster.isPresent()) {
			CommonMaster commonMaster = existingCommonMaster.get();
			commonMaster.setUpdatedBy(updatedBy);
			commonMaster.setDeleted(true);
			return commonMasterRepository.save(commonMaster);
		}
		return null;

	}

	public Page<CommonMaster> getByParentCommonMasterPagination(String name, Pageable pageable) {
		Page<CommonMaster> commonMasterPaging;
		if (ObjectUtils.isEmpty(name)) {
			commonMasterPaging = commonMasterRepository.findByMstAndDeleted(true, false, pageable);
		} else {
			commonMasterPaging = commonMasterRepository.findByMstNameContainingIgnoreCaseAndMstAndDeleted(name, true,
					false, pageable);
		}
		return commonMasterPaging;
	}

	public Page<CommonMaster> getByChildCommonMasterPagination(String name, String mstName, Pageable pageable) {
		Page<CommonMaster> commonMasterPaging;
		if (ObjectUtils.isEmpty(name)) {
			commonMasterPaging = commonMasterRepository.findByMstNameAndMstAndDeletedOrderByPriorityAscValueAsc(mstName, false, false, pageable);
		} else {
			commonMasterPaging = commonMasterRepository.findByValueContainingIgnoreCaseAndMstNameAndMstAndDeletedOrderByPriorityAscValueAsc(name,
					mstName, false, false, pageable);
		}
		return commonMasterPaging;
	}

	public CommonMaster getParentCommonMasterByForeignKey(String foreignKey) {

		CommonMaster data = commonMasterRepository.findByCodeEqualsIgnoreCaseAndMstAndDeleted(foreignKey, false, false);

		CommonMaster findByValueIgnoreCaseAndMstAndDeleted = commonMasterRepository
				.queryByMstNameEqualsIgnoreCaseAndMstAndDeleted(data.getMstName(), true, false);

		return findByValueIgnoreCaseAndMstAndDeleted;
	}

	public CommonMaster getCommonMasterbyCode(String code) {
		return commonMasterRepository.getByCodeEqualsIgnoreCaseAndDeleted(code, false);
	}

}
