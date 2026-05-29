package com.avisys.microservice.dto;

import java.time.LocalDate;
import java.util.List;

import com.avisys.microservice.entity.MailNotification;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Data
@ToString
public class MailNotificationDTO {
	
	private LocalDate date;

	private List<MailNotification> mailNotification;
}
