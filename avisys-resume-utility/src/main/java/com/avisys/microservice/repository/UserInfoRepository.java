package com.avisys.microservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.avisys.microservice.entity.UserInfo;
import com.avisys.microservice.projection.EmailValidateDTO;
import com.avisys.microservice.projection.UserData;
import com.avisys.microservice.projection.UserName;

public interface UserInfoRepository extends  JpaRepository<UserInfo, Long> {


	Optional<UserInfo> findByIdAndDeleted(Long id, boolean deleted);

	Page<UserInfo> findByDeleted(boolean deleted, Pageable pageable);

	Page<UserInfo> findByFirstNameContainingIgnoreCaseAndDeleted(String name, boolean deleted, Pageable pageable);

	List<UserInfo> findByReportsToAndDeleted(String reportsTo, boolean deleted);

	List<UserInfo> findByDeleted(boolean deleted);
	
	Optional<UserInfo> findByUserId(String id);

	List<UserInfo> findByReportsTo(String userId);

	List<UserInfo> findByEmailIn(List<String> email);

	@Query(value = "WITH RECURSIVE subordinates AS (select user_id FROM	user_info where user_id = :userId union select ud.user_id from user_info ud INNER JOIN subordinates s ON s.user_id = ud.reports_to) SELECT * from subordinates", nativeQuery = true)
	List<String> getAllUsersById(@Param("userId") String userId);
	
//	UserName getByUserId(String id);
	UserName findByUserIdAndEnabled(String id, boolean enabled);
	
	List<UserData> findByUserIdNotAndDeleted(String userId, boolean deleted);

	Optional<UserInfo> findByEmail(String email);

	Optional<EmailValidateDTO> findByEmailAndEnabledTrueAndDeletedFalse(String email);
	

}
