package com.avisys.microservice.service;

import java.time.LocalDateTime;
import java.time.ZoneId;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.avisys.microservice.datatable.dto.ButtonForDataTable;
import com.avisys.microservice.entity.EmailDetails;
import com.avisys.microservice.model.EmailContent;
import com.avisys.microservice.model.ScheduleEmailRequest;
import com.avisys.microservice.repository.EmailDetailsRepo;
import com.avisys.microservice.util.Utility;

@Service
public class EmailDetailService {
	
	@Autowired
	EmailDetailsRepo emailDetailsRepo;
	@Autowired
	Utility util;
	
	public Page<EmailDetails> getEmailDetailsPaging(String name,Pageable page){
		
		Page<EmailDetails> pageData=null;
		if(ObjectUtils.isEmpty(name)) {
			pageData = emailDetailsRepo.findByJobGroupContainingIgnoreCase("", page);
		}
		else {
			pageData = emailDetailsRepo.findByJobGroupContainingIgnoreCase(name, page);
		}
		
		ButtonForDataTable btn = new ButtonForDataTable();
		btn.setColor("blue");
		btn.setLabel("View Content");

		ButtonForDataTable btn1 = new ButtonForDataTable();
		btn1.setColor("green");
		btn1.setLabel("Reschedule Email");

		ButtonForDataTable[] btnList = new ButtonForDataTable[2];
		btnList[0] = btn;
		btnList[1] = btn1;

		pageData.stream().forEach(e -> e.setAction(btnList));

		return pageData;
	}
	
	public EmailContent getEmailContent(String name) {
		
		EmailDetails byJobNameContainingIgnoreCase = emailDetailsRepo.findByJobNameContainingIgnoreCase(name);
		return util.getEmailContentFromByteArray(byJobNameContainingIgnoreCase);
//		return new EmailContent();
		
	}
	
	public int rescheduleEmail(String jobName) {
		EmailContent jobData = getEmailContent(jobName);

		ScheduleEmailRequest emailRequest = new ScheduleEmailRequest();
		emailRequest.setEmail(jobData.getEmailTo());
		emailRequest.setBody(jobData.getEmailBody());
		emailRequest.setGroupName("RESCHEDULED EMAIL");
		emailRequest.setSubject(jobData.getEmailSubject());
		emailRequest.setDateTime(LocalDateTime.now().plusSeconds(5));
		emailRequest.setTimeZone(ZoneId.of("Asia/Kolkata"));

		return util.scheduleEmail(emailRequest);
	}
	
	
	
}
