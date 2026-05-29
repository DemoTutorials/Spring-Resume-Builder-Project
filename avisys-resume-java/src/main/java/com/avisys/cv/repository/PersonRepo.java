package com.avisys.cv.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.avisys.cv.dto.PersonProjection;
import com.avisys.cv.entity.Person;

import feign.Param;

@Repository
public interface PersonRepo extends JpaRepository<Person, Long> {

	Optional<Person> findByPersonIdAndIsDeletedFalse(Long id);

	List<Person> findByIsDeletedFalse();

	@Query("SELECT DISTINCT p FROM Person p LEFT JOIN p.skillsList s LEFT JOIN p.otherSkillsList o LEFT JOIN p.experienceList e WHERE (LOWER(p.firstName) LIKE %:key% OR LOWER(p.lastName) LIKE %:key% OR (LOWER(s.skillName) LIKE %:key% AND s.isDeleted = false) OR (LOWER(o.otherSkillName) LIKE %:key% AND o.isDeleted = false) OR (p.email LIKE %:key% ) OR (LOWER(e.domain) LIKE %:key% AND e.isDeleted = false)) AND p.isDeleted = false")
	Page<Person> searchPersonByAdmin(String key, Pageable pageable);

	@Query("SELECT DISTINCT p FROM Person p LEFT JOIN p.skillsList s LEFT JOIN p.otherSkillsList o LEFT JOIN p.experienceList e WHERE (LOWER(p.firstName) LIKE %:key% OR LOWER(p.lastName) LIKE %:key% OR (LOWER(s.skillName) LIKE %:key% AND s.isDeleted = false) OR (LOWER(o.otherSkillName) LIKE %:key% AND o.isDeleted = false) OR (p.email LIKE %:key% ) OR (LOWER(e.domain) LIKE %:key% AND e.isDeleted = false)) AND p.isDeleted = false AND p.companyCode = :companyCode")
	Page<Person> searchPersonByHRCode(String key, String companyCode, Pageable pageable);

	Person findByCreatedByAndIsDeletedFalse(String userId);
	
	@Query("SELECT p FROM Person p WHERE p.user.id = :userId")
    Person findByUserId(@Param("userId") Long userId);
		
	@Query(value="select p.person_id as personId, p.first_name as firstName, p.last_name as lastName, p.phone as phone, p.email as email, p.company_code as companyCode, p.total_experience as totalExperience, u.company_name as companyName, p.last_update_date as lastUpdateDate FROM jasper_cv.personal_info p LEFT JOIN jasper_cv.skills_info s ON p.person_id = s.person_id LEFT JOIN jasper_cv.work_experience_info we ON p.person_id = we.person_id\r\n"
			+ "				        INNER JOIN uam.users u ON p.user_id = u.id \r\n"
			+ "				        WHERE (p.total_experience >= :minExperience)\r\n"
			+ "				          AND (p.total_experience <= :maxExperience)\r\n"
			+ "				          AND p.is_deleted = false\r\n"
			+ "				          AND s.is_deleted = false\r\n"
			+ "				          AND we.is_deleted = false\r\n"
			+ "				          AND ((:domain = '') OR (LOWER(we.domain) = :domain))\r\n"
			+ "				          AND ((:company = '') OR (LOWER(u.company_name) = :company))\r\n"
			+ "				          AND ((CONCAT(:skillNames) = '') OR (LOWER(s.skill_name) IN (:skillNames)))\r\n"
			+ "				        GROUP BY p.person_id, u.company_name", nativeQuery = true)
	Page<PersonProjection> searchByAdmin(Pageable pageable, Float minExperience, Float maxExperience, List<String> skillNames, String domain, String company);

	
	@Query("SELECT new com.avisys.cv.entity.Person( p.personId, p.city, p.country, cm2.value as countryValue, p.email, p.firstName, " +
	        "p.lastName, p.linkedin, p.phone, p.profilePic, p.state, cm1.value as stateValue, p.summary, p.companyCode, p.totalExperience, p.uploadFile) " +
	        "FROM Person p " +
	        "LEFT JOIN CommonMaster cm1 ON cm1.code = p.state " +
	        "LEFT JOIN CommonMaster cm2 ON cm2.code = p.country " +
	        "WHERE p.personId = :personId AND p.isDeleted = false")
	Optional<Person> PersonDataByPersonId(Long personId);
	
	@Query(value="SELECT  \r\n"
			+ "			    p.person_id AS personId,\r\n"
			+ "			    p.first_name AS firstName,\r\n"
			+ "			    p.last_name AS lastName,\r\n"
			+ "			    p.phone AS phone,\r\n"
			+ "			    p.email AS email,\r\n"
			+ "			    jp.company_code AS companyCode,\r\n"
			+ "			    p.total_experience AS totalExperience,\r\n"
			+ "			    jp.job_post_code AS jobPostCode,\r\n"
			+ "			    p.last_update_date as lastUpdateDate\r\n"
			+ "			FROM\r\n"
			+ "			    jasper_cv.personal_info p\r\n"
			+ "			LEFT JOIN uam.users u ON  u.id = p.user_id      \r\n"
			+ "			LEFT JOIN\r\n"
			+ "			    jasper_cv.skills_info s ON p.person_id = s.person_id\r\n"
			+ "			left JOIN\r\n"
			+ "			    jasper_cv.work_experience_info we ON p.person_id = we.person_id\r\n"
			+ "			LEFT JOIN\r\n"
			+ "			    jasper_cv.person_jobpost pjp ON p.person_id = pjp.person_id\r\n"
			+ "			LEFT JOIN\r\n"
			+ "			    jasper_cv.job_post jp ON pjp.job_id = jp.job_id\r\n"
			+ "			WHERE 	\r\n"
			+ "			  p.total_experience >=:minExperience\r\n"
			+ "			  AND p.total_experience <= :maxExperience\r\n"
			+ "			  AND (:domain = '' OR LOWER(we.domain) = LOWER(:domain))\r\n"
			+ "			  AND ((CONCAT(:skillNames) = '') OR (LOWER(s.skill_name) IN (:skillNames)))\r\n"
			+ "			  and  (:jobProfile = '' OR  LOWER(jp.job_post_code) = :jobProfile)\r\n"
			+ "			  AND ( u.company_code = :companyCode \r\n"
			+ "			 OR jp.company_code = :companyCode)\r\n"
			+ "			 group by p.person_id, jp.company_code,jp.job_post_code", nativeQuery = true)
		Page<PersonProjection> searchByHR(Pageable pageable, Float minExperience, Float maxExperience, List<String> skillNames,
					String domain, String companyCode, String jobProfile);

}
