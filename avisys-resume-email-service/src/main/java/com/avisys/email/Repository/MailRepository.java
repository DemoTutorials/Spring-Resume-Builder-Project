package com.avisys.email.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.avisys.email.entity.MailNotification;

@Repository
public interface MailRepository extends   JpaRepository<MailNotification, Long>, PagingAndSortingRepository<MailNotification, Long> {

	Optional<MailNotification> findByMailIdAndIsDeleted(Long id, boolean b);


	List<MailNotification> findByIsDeletedAndReferenceObjectTypeAndReferenceIdOrderByCreatedDateAsc(boolean b,String type, Long referenceId);


	List<MailNotification> findByIsDeletedAndReferenceObjectTypeAndReferenceIdOrderByCreatedDateDesc(boolean b,String type, String referenceId);

}
