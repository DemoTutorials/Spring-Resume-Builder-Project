package com.avisys.email.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

//import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.history.RevisionMetadata.RevisionType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.avisys.email.Repository.MailRepository;
import com.avisys.email.dto.EmailRequestBody;
import com.avisys.email.dto.MailDTO;
import com.avisys.email.dto.MailNotificationDTO;
import com.avisys.email.entity.MailAttachment;
import com.avisys.email.entity.MailNotification;
import com.avisys.email.enums.EntityFlag;
import com.avisys.email.exception.PersistentException;
import com.avisys.email.exception.ResourceNotFoundExceptionGeneric;

@Service
public class MailService {

	@Autowired
	MailRepository mailRepository;

	@Autowired
	EmailTemplateDetails emailTempateDetails;

	@Autowired
	MailAttachmentService mailAttachmentService;

	@Autowired
	AuditService auditService;

	public List<MailNotificationDTO> getMails(String type, String referenceId) {

		List<MailNotification> listMails = mailRepository
				.findByIsDeletedAndReferenceObjectTypeAndReferenceIdOrderByCreatedDateDesc(false, type, referenceId);

		List<LocalDate> dates = new ArrayList<LocalDate>();
		listMails.forEach(n -> {

			dates.add(n.getDate());
		});

		List<MailNotificationDTO> mailNotificationDTO = new ArrayList<MailNotificationDTO>();

		Set<LocalDate> foo = new LinkedHashSet<LocalDate>(dates);
		for (LocalDate localDate : foo) {
			MailNotificationDTO mailNotificationDto = new MailNotificationDTO();
			List<MailNotification> newMails = listMails.stream().filter(nn -> nn.getDate().equals(localDate))
					.collect(Collectors.toList());

			mailNotificationDto.setDate(localDate);
			mailNotificationDto.setMailNotification(newMails);
			mailNotificationDTO.add(mailNotificationDto);
		}

		return mailNotificationDTO;
	}

	public MailNotification save(MailDTO mailDTO, List<MultipartFile> file, String sendMail) {
		MultipartFile multipartFile = null;
		LocalDateTime dateTime = LocalDateTime.now();
		mailDTO.setCreatedDate(dateTime);
		mailDTO.setIsDeleted(false);
		mailDTO.setOwner(mailDTO.getCreatedBy());
		MailNotification mailNotification = convertIntoMailNotification(mailDTO);
		MailNotification saveMail = mailRepository.save(mailNotification);
		if (file != null) {
			List<MailAttachment> mailAttachment = mailAttachmentService.save(file, saveMail);
			saveMail.setMailAttachment(mailAttachment);
			multipartFile = file.get(0);
		}
		if (sendMail.equalsIgnoreCase("send-email")) {
			EmailRequestBody mailData = convertIntoMailObject(saveMail, mailDTO.getJobData(), mailDTO.getJobName());

			emailTempateDetails.scheduleEmailRequest(mailData, saveMail.getMailId(),multipartFile);

		}

		auditService.auditing(EntityFlag.MailNotification.getName(), RevisionType.INSERT.name(), saveMail.getMailId(),
				saveMail.getCreatedBy(), null);
		return saveMail;
	}

	public MailNotification update(MailNotification mailNotification) throws CloneNotSupportedException {
		LocalDateTime dateTime = LocalDateTime.now();
		Optional<MailNotification> findById = mailRepository.findById(mailNotification.getMailId());
		if (findById.isEmpty()) {
			throw new ResourceNotFoundExceptionGeneric("Mail not found for ID :" + mailNotification.getMailId());
		}
		MailNotification existingMail = findById.get();
		MailNotification oldMailNotification = (MailNotification) existingMail.clone();
		existingMail.setEmailTemplate(mailNotification.getEmailTemplate());
		existingMail.setEmailStatus(mailNotification.getEmailStatus());
		existingMail.setEmailBody(mailNotification.getEmailBody());
		existingMail.setEmailSubject(mailNotification.getEmailSubject());
		existingMail.setSendTO(mailNotification.getSendTO());
		existingMail.setSendBcc(mailNotification.getSendBcc());
		existingMail.setSendCc(mailNotification.getSendCc());
		MailNotification saveMailNotification = mailRepository.save(existingMail);

		auditService.auditing(oldMailNotification, saveMailNotification, EntityFlag.MailNotification.getName(), RevisionType.UPDATE.name(),
				saveMailNotification.getMailId(), saveMailNotification.getUpdatedBy());
		return saveMailNotification;
	}

	public Optional<MailNotification> getById(Long id) {

		return mailRepository.findByMailIdAndIsDeleted(id, false);
	}

	public MailNotification softDelete(Long id, String updatedBy) throws CloneNotSupportedException {
		LocalDateTime dateTime = LocalDateTime.now();
		Optional<MailNotification> findById = mailRepository.findById(id);
		if (findById.isEmpty()) {
			throw new ResourceNotFoundExceptionGeneric("Mail not found for ID :" + id);
		}
		MailNotification existingMail = findById.get();
		MailNotification oldMailNotification = (MailNotification) existingMail.clone();
		Boolean b = existingMail.getIsDeleted();
		if (Boolean.TRUE.equals(b)) {
			throw new PersistentException("Mail Record already Deleted");
		}
		existingMail.setIsDeleted(true);
		existingMail.setUpdatedBy(updatedBy);
		existingMail.setUpdatedDate(dateTime);
		MailNotification saveMailNotification = mailRepository.save(existingMail);

		auditService.auditing(oldMailNotification, saveMailNotification, EntityFlag.MailNotification.getName(), RevisionType.DELETE.name(),
				saveMailNotification.getMailId(), saveMailNotification.getUpdatedBy());
		return saveMailNotification;
	}

	public EmailRequestBody convertIntoMailObject(MailNotification mail, Map<String, String> jobData, String jobName) {

		EmailRequestBody emailBody = new EmailRequestBody();
		emailBody.setJobData(jobData);
		emailBody.setRecipient(mail.getSendTO());
		emailBody.setTemplateId(mail.getEmailTemplate());
		emailBody.setScheduleTime(null);
		emailBody.setJobName(jobName);
		return emailBody;

	}

	public MailNotification convertIntoMailNotification(MailDTO mailDTO) {
		MailNotification mail = new MailNotification();
		mail.setCreatedBy(mailDTO.getCreatedBy());
		mail.setCreatedDate(mailDTO.getCreatedDate());
		mail.setEmailBody(mailDTO.getEmailBody());
		mail.setEmailFrom(mailDTO.getEmailFrom());
		mail.setEmailStatus(mailDTO.getEmailStatus());
		mail.setEmailSubject(mailDTO.getEmailSubject());
		mail.setEmailTemplate(mailDTO.getEmailTemplate());
		mail.setIsDeleted(mailDTO.getIsDeleted());
		mail.setReferenceId(mailDTO.getReferenceId());
		mail.setReferenceObjectType(mailDTO.getReferenceObjectType());
		mail.setSendBcc(mailDTO.getSendBcc());
		mail.setSendCc(mailDTO.getSendCc());
		mail.setSendTO(mailDTO.getSendTO());
		mail.setUpdatedBy(mailDTO.getUpdatedBy());
		mail.setOwner(mailDTO.getOwner());
		mail.setUpdatedDate(mailDTO.getUpdatedDate());
		return mail;

	}



}
