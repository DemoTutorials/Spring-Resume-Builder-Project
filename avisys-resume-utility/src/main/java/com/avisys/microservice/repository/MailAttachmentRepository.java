package com.avisys.microservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.avisys.microservice.entity.MailAttachment;
@Repository
public interface MailAttachmentRepository extends  JpaRepository<MailAttachment, Long> {

}
