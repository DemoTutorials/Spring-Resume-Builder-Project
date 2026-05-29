package com.auth.uam.dto;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Data
@ToString
public class MailAttachment {

	private Long mailAttachmentId;

	private String attachmentName;

	private Boolean isDeleted;

	private String createdBy;

	private LocalDateTime createdDate;

	private String updatedBy;

	private LocalDateTime updatedDate;

	private String owner;
}
