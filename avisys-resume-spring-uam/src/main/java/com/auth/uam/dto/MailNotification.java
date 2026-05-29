package com.auth.uam.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Data
@ToString
public class MailNotification {


	private Long mailId;

	private String emailTemplate;

	private String emailStatus;

	private String emailBody;

	private String emailSubject;

	private String emailFrom;

	private String sendTO;

	private String sendCc;

	private String sendBcc;

	private Boolean isDeleted;

	private String createdBy;

	private LocalDateTime createdDate;

	private String updatedBy;

	private LocalDateTime updatedDate;

	private String owner;

	private String referenceId;

	private String referenceObjectType;

	private List<MailAttachment> mailAttachment;
	
	private LocalDate date;

}

