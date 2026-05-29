package com.auth.uam.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.auth.uam.entity.Person;
import com.auth.uam.entity.User;

@Repository
public interface PersonRepo extends JpaRepository<Person, Long> {

	Optional<Person> findByPersonIdAndIsDeletedFalse(Long id);

	List<Person> findByIsDeletedFalse();

	Person findByCreatedByAndIsDeletedFalse(String userId);
	
	List<Person> findByUser(User user);

}
