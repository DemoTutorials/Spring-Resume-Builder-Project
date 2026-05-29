package com.avisys.email.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.history.RevisionMetadata.RevisionType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.avisys.email.Repository.MailAttachmentRepository;
import com.avisys.email.entity.MailAttachment;
import com.avisys.email.entity.MailNotification;
import com.avisys.email.enums.EntityFlag;

@Service
public class MailAttachmentService {

	@Autowired
	MailAttachmentRepository mailAttachmentRepository;

	@Autowired
	MailTriggerService uploadFileService;
	
	@Autowired
	AuditService auditService;

	public List<MailAttachment> save(List<MultipartFile> file, MailNotification mailNotification) {

		List<MailAttachment> mailAttachmentSave = new ArrayList<MailAttachment>();

		for (Iterator iterator = file.iterator(); iterator.hasNext();) {
			MultipartFile multipartFile = (MultipartFile) iterator.next();

			MailAttachment newAttachment = new MailAttachment();
			String fileUploadLocation = null;
			if (file != null) {
				fileUploadLocation = uploadFileService.uploadFile(multipartFile);

			}
			newAttachment.setMailNotification(mailNotification);
			newAttachment.setAttachmentName(fileUploadLocation.substring(fileUploadLocation.indexOf("http"), fileUploadLocation.length()));
			LocalDateTime dateTime = LocalDateTime.now();
			newAttachment.setCreatedDate(dateTime);
			newAttachment.setIsDeleted(false);
			newAttachment.setOwner(mailNotification.getCreatedBy());
			newAttachment.setCreatedBy(mailNotification.getCreatedBy());
			MailAttachment saveAttachment = mailAttachmentRepository.save(newAttachment);
			
			auditService.auditing(EntityFlag.MailAttachment.getName(), RevisionType.INSERT.name(), saveAttachment.getMailAttachmentId(),
					saveAttachment.getCreatedBy(), null);
			mailAttachmentSave.add(saveAttachment);
		}

		return mailAttachmentSave;
	}

}
