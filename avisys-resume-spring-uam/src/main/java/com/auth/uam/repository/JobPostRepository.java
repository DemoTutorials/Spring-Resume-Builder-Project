package com.auth.uam.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.auth.uam.dto.JobPostDTO;
import com.auth.uam.dto.PostDto;
import com.auth.uam.entity.JobPost;

public interface JobPostRepository extends JpaRepository<JobPost, Long> {
	List<JobPost> findByCompanyCode(String companyCode);

	@Query("SELECT new com.auth.uam.dto.JobPostDTO(j.jobId, j.jobPostCode, j.companyCode, j.jobProfile, j.description, j.active, "
	        + "COALESCE(MAX(u.companyName), ''), j.lastUpdateDate) "
	        + "FROM JobPost j "
	        + "INNER JOIN User u ON j.companyCode= u.companyCode "
	        + "WHERE (LOWER(j.jobPostCode) LIKE LOWER(CONCAT('%', :key, '%')) "
	        + "OR LOWER(j.jobProfile) LIKE LOWER(CONCAT('%', :key, '%')) "
	        + "OR LOWER(COALESCE(u.companyName, '')) LIKE LOWER(CONCAT('%', :key, '%'))) "
	        + "AND j.companyCode = :companyCode "
	        + "AND j.isDeleted = false "
	        + "GROUP BY j.jobId")
	Page<JobPostDTO> searchJobPostCode(@Param("key") String key,
	                                   @Param("companyCode") String companyCode, Pageable pageable);

	Optional<JobPost> findByJobPostCodeAndIsDeletedFalse(String jobPostCode);

	Optional<JobPost> findByJobIdAndIsDeletedFalse(Long id);

	@Query(value = "SELECT jp.job_id AS jobId,\r\n"
			+ "				p.person_id AS personId,\r\n"
			+ "            jp.job_post_code AS jobPostCode,\r\n"
			+ "            jp.company_code AS companyCode,\r\n"
			+ "            jp.job_profile AS jobProfile,\r\n"
			+ "            jp.description AS description,\r\n"
			+ "            jp.active AS active,\r\n"
			+ "            sq.company_name AS companyName,\r\n"
			+ "            jp.last_update_date AS lastUpdateDate\r\n"
			+ "            FROM jasper_cv.personal_info p\r\n"
			+ " 			INNER join uam.users u on p.user_id = u.id\r\n"
			+ "            INNER JOIN jasper_cv.person_jobpost pj ON p.person_id = pj.person_id\r\n"
			+ "            INNER JOIN jasper_cv.job_post jp ON pj.job_id = jp.job_id\r\n"
			+ "            INNER JOIN (SELECT DISTINCT company_code, company_name FROM uam.users) sq\r\n"
			+ "            ON sq.company_code = jp.company_code\r\n"
			+ "            WHERE u.id = :userId\r\n"
			+ "            AND jp.is_deleted = false\r\n"
			+ "            AND (LOWER(jp.job_post_code) LIKE :keyword OR\r\n"
			+ "            LOWER(jp.job_profile) LIKE :keyword OR \r\n"
			+ "            LOWER(sq.company_name) LIKE :keyword)", 
    nativeQuery = true)
	Page<PostDto> searchJobPostByUser(@Param("keyword") String keyword, @Param("userId") Long userId, Pageable pageable);
}
