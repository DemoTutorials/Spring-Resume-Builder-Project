package com.avisys.microservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.avisys.microservice.entity.Notes;

public interface NotesRepository extends  JpaRepository<Notes, Long>,
		PagingAndSortingRepository<Notes, Long> {

	Optional<Notes> findByNotesIdAndIsDeleted(Long notesId, boolean b);


	
	List<Notes> findByIsDeletedAndReferenceTypeAndReferenceIdOrderByCreatedDateDesc(boolean b, String refType,
			Long refId);

}
