package com.avisys.microservice.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.avisys.microservice.entity.CommonMaster;

public interface CommonMasterRepository extends PagingAndSortingRepository<CommonMaster, Long> {

	Page<CommonMaster> findByMstAndDeleted(boolean isMst, boolean deleted, Pageable pageable);

	Page<CommonMaster> findByMstNameContainingIgnoreCaseAndMstAndDeleted(String name, boolean isMst, boolean deleted,
			Pageable pageable);

	Page<CommonMaster> findByMstNameAndMstAndDeletedOrderByPriorityAscValueAsc(String foreignKey, boolean isMst, boolean deleted,
			Pageable pageable);

	Page<CommonMaster> findByValueContainingIgnoreCaseAndMstNameAndMstAndDeletedOrderByPriorityAscValueAsc(String value, String foreignKey,
			boolean isMst, boolean deleted, Pageable pageable);

	List<CommonMaster> findByMstAndDeleted(Boolean isMst, Boolean deleted);

	List<CommonMaster> findByMstNameIgnoreCaseAndAdditionalValueContainingIgnoreCaseAndMstAndDeletedOrderByPriorityAscValueAsc(
			String foreignKey,String dataOf, boolean isMst, boolean deleted);

	List<CommonMaster> findByForeignKeyContainingIgnoreCaseAndMstNameContainingIgnoreCaseAndAdditionalValueContainingIgnoreCaseAndDeletedOrderByPriorityAscValueAsc(String foreignKey,
			String mstName,String dataof, boolean deleted);

	List<CommonMaster> findByMstNameContainingIgnoreCaseAndDeleted(String mstName, Boolean deleted);

	// new code

	CommonMaster findByCodeEqualsIgnoreCaseAndMstAndDeleted(String foreignKey, boolean isMst, boolean deleted);

	CommonMaster queryByMstNameEqualsIgnoreCaseAndMstAndDeleted(String mstName, Boolean mst, Boolean deleted);

	List<CommonMaster> findByCodeEqualsIgnoreCaseAndDeleted(String name, Boolean deleted);
	
	List<CommonMaster> findByCodeEqualsIgnoreCaseAndForeignKeyEqualsIgnoreCaseAndMstNameEqualsIgnoreCaseAndDeleted(String name,String foreignKey,String mstName, Boolean deleted);
	
	CommonMaster getByCodeEqualsIgnoreCaseAndDeleted(String name, Boolean deleted);

	List<CommonMaster> findByMstNameEqualsIgnoreCaseAndCodeEqualsIgnoreCaseAndDeleted(
                    String mstName,String code, boolean deleted);
	
	List<CommonMaster> findByMstNameEqualsIgnoreCaseAndMstAndDeleted(
            String mstName,boolean mst,boolean deleted);
	
	List<CommonMaster> findByCodeEqualsIgnoreCaseAndValueEqualsIgnoreCaseAndMstAndDeleted(
            String code,String value,boolean mst,boolean deleted);
	
	List<CommonMaster> findByValueEqualsIgnoreCaseAndMstAndDeleted(String value,boolean mst,boolean deleted);
	
	
}
