package com.avisys.microservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.avisys.microservice.entity.Designation;

public interface DesignationRepository extends PagingAndSortingRepository<Designation, Long> {

	Optional<Designation> findByIdAndDeleted(Long id, boolean deleted);

	Page<Designation> findByDeleted(boolean deleted, Pageable pageable);

	Page<Designation> findByNameContainingIgnoreCaseAndDeleted(String name, boolean deleted, Pageable pageable);

	List<Designation> findByParentIdAndDeleted(Long parentId, boolean deleted);

	List<Designation> findByDeleted(boolean deleted);

	List<Designation> findByParentId(Long parentId);

	@Query(value = "WITH RECURSIVE subordinates AS (select user_id FROM	user_details where user_id = :userId union select ud.user_id from user_details ud INNER JOIN subordinates s ON s.user_id = ud.reports_to) SELECT * from subordinates", nativeQuery = true)
	List<String> getAllUsersById(@Param("userId") String userId);

}
