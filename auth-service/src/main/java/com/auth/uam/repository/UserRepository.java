package com.auth.uam.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.auth.uam.entity.User;
import com.auth.uam.projection.UserProjection;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByUserNameAndIsDeleted(String username, boolean b);
	
	Optional<User> findByUserNameIgnoreCaseAndIsDeleted(String username, boolean b);

//	@Query(value = "select new com.auth.uam.projection.UserProjection(u.id , u.userName)  from User u")
//	List<UserProjection> getList();

	Optional<User> findByUserIdAndIsDeleted(String userId, boolean b);

	Page<User> findByIsDeleted(boolean b, Pageable pageable);


	Page<User> findByIsDeletedAndUserNameLikeIgnoreCaseOrIsDeletedAndFirstNameLikeIgnoreCaseOrIsDeletedAndLastNameLikeIgnoreCaseOrIsDeletedAndEmailLikeIgnoreCase(
			boolean b, String string, boolean c, String string2, boolean d, String string3, boolean e, String string4,
			Pageable pageable);

	Optional<User> findByUserNameAndEmailAndIsDeleted(String userName, String email, boolean b);


	

	

	

}
