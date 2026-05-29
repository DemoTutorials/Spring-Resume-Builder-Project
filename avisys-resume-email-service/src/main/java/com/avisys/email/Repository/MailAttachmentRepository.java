package com.avisys.email.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.avisys.email.entity.MailAttachment;

@Repository
public interface MailAttachmentRepository extends  JpaRepository<MailAttachment, Long> {

}
