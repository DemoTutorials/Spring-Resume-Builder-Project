package com.auth.uam.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.auth.uam.dto.UserDatatable;
import com.auth.uam.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	Optional<User> findByUserNameAndIsDeleted(String username, boolean b);
	
	@Query("SELECT m.id as id, m.userId as userId, m.userName as userName, m.firstName as firstName, m.middleName as middleName, m.lastName as lastName, m.email as email, m.companyName as companyName, m.mobileNumber as mobileNumber, cm1.value as userType " +
	           "FROM User m LEFT JOIN CommonMaster cm1 ON cm1.code = m.userType AND cm1.deleted = false WHERE m.isDeleted = false")
	Page<UserDatatable> fetchUserAndNotDeleted(Pageable pageable);

	@Query("SELECT m.id as id, m.userId as userId, m.userName as userName, m.firstName as firstName, m.middleName as middleName, m.lastName as lastName, m.companyName as companyName, m.mobileNumber as mobileNumber, cm1.value as userType " +
		       "FROM User m LEFT JOIN CommonMaster cm1 ON cm1.code = m.userType AND cm1.deleted = false " +
		       "WHERE (LOWER(m.userName) LIKE LOWER(CONCAT('%', :key, '%')) " +
		       "OR LOWER(m.firstName) LIKE LOWER(CONCAT('%', :key, '%')) " +
		       "OR LOWER((CONCAT(m.firstName,' ',m.lastName))) LIKE LOWER(CONCAT('%', :key, '%')) " +
		       "OR LOWER((CONCAT(m.firstName,' ',m.middleName,' ',m.lastName))) LIKE LOWER(CONCAT('%', :key, '%')) " +
		       "OR LOWER(m.middleName) LIKE LOWER(CONCAT('%', :key, '%')) " +
		       "OR LOWER(m.lastName) LIKE LOWER(CONCAT('%', :key, '%')) " +
		       "OR LOWER(m.companyName) LIKE LOWER(CONCAT('%', :key, '%')) " +
		       "OR LOWER(m.mobileNumber) LIKE LOWER(CONCAT('%', :key, '%')) " +
		       "OR LOWER(m.userType) LIKE LOWER(CONCAT('%', :key, '%'))) " +
		       "AND m.isDeleted = false")
	Page<UserDatatable> findByParameters(@Param("key") String key, Pageable pageable);

	Optional<User> findByUserNameAndEmailAndIsDeleted(String userName, String email, boolean b);

	Optional<User> findByIdAndIsDeleted(Long id, boolean b);
	
//	@Query("SELECT u FROM User u LEFT JOIN FETCH u.jobPost WHERE u.id = :id AND u.isDeleted = false")
//    Optional<User> findByIdAndIsDeletedWithJobPosts(@Param("id") Long id);

	Optional<User> findByEmailIgnoreCase(String user);

	Optional<User> findByUserNameIgnoreCase(String user);

	Optional<User> findByMobileNumber(String mobileNumber);

	Optional<User> findByUserIdAndIsDeleted(String userId, boolean b);
	
	@Query("SELECT m.id as id, m.userId as userId, m.userName as userName, m.firstName as firstName, m.middleName as middleName, m.lastName as lastName, m.email as email, m.mobileNumber as mobileNumber, cm1.value as userType, m.companyName as companyName " +
		       "FROM User m LEFT JOIN CommonMaster cm1 ON cm1.code = m.userType AND cm1.deleted = false " +
		       "WHERE m.isDeleted = false AND m.companyCode = :companyCode")
	Page<UserDatatable> findByIsDeletedAndCompanyCode(@Param("companyCode") String companyCode, Pageable pageable);


	@Query("SELECT m.id as id, m.userId as userId, m.userName as userName, m.firstName as firstName, m.middleName as middleName, m.lastName as lastName, m.companyName as companyName, m.mobileNumber as mobileNumber, cm1.value as userType " +
		       "FROM User m LEFT JOIN CommonMaster cm1 ON cm1.code = m.userType AND cm1.deleted = false " +
		       "WHERE (LOWER(m.userName) LIKE LOWER(CONCAT('%', :key, '%')) " +
		       "OR LOWER(m.firstName) LIKE LOWER(CONCAT('%', :key, '%')) " +
		       "OR LOWER((CONCAT(m.firstName,' ',m.lastName))) LIKE LOWER(CONCAT('%', :key, '%')) " +
		       "OR LOWER((CONCAT(m.firstName,' ',m.middleName,' ',m.lastName))) LIKE LOWER(CONCAT('%', :key, '%')) " +
		       "OR LOWER(m.lastName) LIKE LOWER(CONCAT('%', :key, '%')) " +
		       "OR LOWER(m.companyName) LIKE LOWER(CONCAT('%', :key, '%')) " +
		       "OR LOWER(m.mobileNumber) LIKE LOWER(CONCAT('%', :key, '%')) " +
		       "OR LOWER(m.userType) LIKE LOWER(CONCAT('%', :key, '%'))) " +
		       "AND m.isDeleted = false AND m.companyCode = :companyCode")
	Page<UserDatatable> findByMultipleParameters(@Param("key") String key, @Param("companyCode") String companyCode, Pageable pageable);

}
