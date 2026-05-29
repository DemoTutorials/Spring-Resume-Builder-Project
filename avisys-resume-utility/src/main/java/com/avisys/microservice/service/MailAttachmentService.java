package com.avisys.microservice.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.avisys.microservice.entity.MailAttachment;
import com.avisys.microservice.entity.MailNotification;
import com.avisys.microservice.proxy.EmailServiceProxy;
import com.avisys.microservice.repository.MailAttachmentRepository;

@Service
public class MailAttachmentService {

	@Autowired
	MailAttachmentRepository mailAttachmentRepository;

	@Autowired
	EmailServiceProxy uploadFileService;

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
			mailAttachmentSave.add(saveAttachment);
		}

		return mailAttachmentSave;
	}

}
