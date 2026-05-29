package com.auth.uam.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.auth.uam.dto.RoleAssignDTO;
import com.auth.uam.dto.RoleSearchDTO;
import com.auth.uam.entity.Roles;

public interface RoleRepository extends JpaRepository<Roles, Long> {

	List<Roles> findByRoleName(String roleName);

	Page<Roles> findByRoleNameLikeIgnoreCaseOrDescriptionLikeIgnoreCase(String string, String string2,
			Pageable pageable);

	Optional<Roles> findByRoleIdAndIsDeletedFalse(Long id);


	

	@Query(value = "select  r.role_id as roleId, cm.value as roleName  , r.description as description  ,u.first_name ||' '|| u.last_name as ownerName  from role r \r\n"
			+ "left join users u on u.user_id = r.created_by \r\n"
			+ "left join crm_kwa.common_mst cm on cm.code = r.role_name "
			+ " where r.is_deleted = ?1 ", nativeQuery = true)
	Page<RoleSearchDTO> findByAndIsDeleted(boolean b, Pageable pageable);

	Optional<Roles> findByRoleNameIgnoreCase(String role);
	
	@Query(value = "select  r.role_id as roleId, cm.value as roleName  , r.description as description  ,u.first_name ||' '|| u.last_name as ownerName  from role r \r\n"
			+ "left join users u on u.user_id = r.created_by \r\n"
			+ "left join crm_kwa.common_mst cm on cm.code = r.role_name "
			+ " where  "
			+ " ((lower(cm.value) like lower(concat(?1)) )\r\n"
			+ "	or (lower(r.description) like lower(concat(?1)) )\r\n"
			+ " or (lower(u.first_name) like lower(concat(?1)) ) "
			+ "	\r\n"
			+ "	) and r.is_deleted = ?2", nativeQuery = true)
	Page<RoleSearchDTO> findRoleSearchAndIsDeleted(String string, boolean b, Pageable pageable);

	
	@Query(value = "select r.role_id as roleId, cm.value as roleName from role r \r\n"
			+ "left join crm_kwa.common_mst cm on cm.code = r.role_name \r\n"
			+ "where r.is_deleted = ?1 ", nativeQuery = true)
	List<RoleAssignDTO> findAllNotAssign(boolean b);

	
	@Query(value = "select r.role_id as roleId,cm.value as roleName from role r  \r\n"
			+ "inner join users_role ur on ur.user_id = ?1 and r.role_id =ur.role_role_id and r.is_deleted = 'false'\r\n"
			+ "left join crm_kwa.common_mst cm on cm.code = r.role_name ", nativeQuery = true)
	List<RoleAssignDTO> getRoleListAssignToUser(Long userId);

}
