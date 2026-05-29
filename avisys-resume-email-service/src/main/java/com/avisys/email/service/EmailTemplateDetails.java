package com.avisys.email.service;

import java.io.StringReader;
import java.io.StringWriter;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.avisys.email.Repository.MailRepository;
import com.avisys.email.dto.EmailRequestBody;
import com.avisys.email.dto.UserJobData;
import com.avisys.email.entity.EmailTemplate;
import com.avisys.email.entity.MailNotification;
import com.avisys.email.payload.ScheduleEmailRequest;
import com.avisys.email.util.Utility;

import freemarker.template.Configuration;
import freemarker.template.Template;

@Service
public class EmailTemplateDetails {

//	TODO: user-creation, forgot-password and user-update condition are commented for mail send
	
	@Autowired
	EmailTemplateService emailTemplateService;
	@Autowired
	Utility utility;
	@Autowired
	MailRepository mailRepository;
	
	Configuration cfg = new Configuration(Configuration.VERSION_2_3_31);

	private String emailTemplateName = "";
	private String emailTemplateSubject = "";

	private static final Logger logger = LoggerFactory.getLogger(EmailTemplateDetails.class);

	public String getTemplateData(String id) {

		EmailTemplate emailTemplate = emailTemplateService.getById(Long.parseLong(id));
		emailTemplateName = emailTemplate.getName();
		emailTemplateSubject = emailTemplate.getEmailTemplateSubject();
		return emailTemplate.getContent();

	}

	public int scheduleEmail(String finalRequestBody, String to, MultipartFile multipartFile) {
		logger.info("Inside schedule email API for: {}", to);
		ScheduleEmailRequest emailRequest = new ScheduleEmailRequest();
		emailRequest.setEmail(to);
		emailRequest.setBody(finalRequestBody);
		emailRequest.setDateTime(LocalDateTime.now().plusSeconds(10));
		emailRequest.setGroupName(emailTemplateName);
		emailRequest.setSubject(emailTemplateSubject);
		emailRequest.setTimeZone(ZoneId.of("Asia/Kolkata"));
		logger.info("Before scheduling the call:");
		return utility.scheduleEmail(emailRequest,multipartFile);

	}

	public LocalDateTime convertToLocalDateTimeViaMilisecond(Date dateToConvert) {
		return Instant.ofEpochMilli(dateToConvert.getTime()).atZone(ZoneId.of("Asia/Kolkata")).toLocalDateTime();
	}

	public String scheduleEmailRequest(EmailRequestBody emailBody, Long mailId, MultipartFile multipartFile) {


		String templateData = getTemplateData(emailBody.getTemplateId());
		logger.info("Template Data: {}", templateData);

		List<UserJobData> jobDataByJobName = getJobDataByJobName(emailBody.getRecipient(), emailBody.getJobData(),
				emailBody.getJobName());

		if (jobDataByJobName.isEmpty()) {
			return "" + HttpStatus.NOT_FOUND;
		}
		return newReplaceEmailTemplateData(templateData, jobDataByJobName, mailId,multipartFile);
	}

	private List<UserJobData> getJobDataByJobName(String recipients, Map<String, String> jobData, String jobName) {

		List<UserJobData> userJobDataList = new ArrayList<>();

		String[] split = recipients.split(",");
		List<String> recipientsList = new ArrayList<>();
		Collections.addAll(recipientsList, split);

		if (jobName.contains("mail-service")) {
			for (String string : recipientsList) {
				UserJobData userJobData = new UserJobData();
				userJobData.setJobData(jobData);
				userJobData.setRecipientsList(string);
				userJobDataList.add(userJobData);
			}

		} else {

		
			
//			List<UserInfo> usersByEmail = emailTemplateService.getUsersByEmail(recipientsList);
//			if (jobName.equalsIgnoreCase("user") || jobName.equalsIgnoreCase("user-creation")
//					|| jobName.equalsIgnoreCase("forgot-pass") || jobName.equalsIgnoreCase("user-update")) {
//
//				for (UserInfo userInfo : usersByEmail) {
//					UserJobData userJobData = new UserJobData();
//					userJobData.setJobData(generateJobDataForUser(jobData, userInfo));
//					userJobData.setRecipientsList(userInfo.getEmail());
//					userJobDataList.add(userJobData);
//				}
//
//			}

		}

		return userJobDataList;

	}

//	private Map<String, String> generateJobDataForUser(Map<String, String> jobData, UserInfo userInfo) {
//
//		jobData.put("customerName", userInfo.getFirstName() + " " + userInfo.getLastName());
//		jobData.put("customerEmail", userInfo.getEmail());
//		jobData.put("customerContactNumber", userInfo.getContact());
//
//		return jobData;
//	}

	private String newReplaceEmailTemplateData(String templateData, List<UserJobData> userJobData, Long mailId, MultipartFile multipartFile) {
		try {

			String replaceVariablesInTemplate = null;
			int totalResponse = 0;
			for (UserJobData userJobData2 : userJobData) {
				Template template = new Template("templateName", new StringReader(templateData), cfg);

				// Process the template
				StringWriter stringWriter = new StringWriter();
				template.process(userJobData2.getJobData(), stringWriter);
				String emailContent = stringWriter.toString();

				logger.info("emailBody: "+emailContent);
				logger.info("sendTo: "+userJobData2.getRecipientsList());
				replaceVariablesInTemplate =emailContent;
				totalResponse = totalResponse + scheduleEmail(emailContent, userJobData2.getRecipientsList(),multipartFile);
			}

			updateEmailBody(replaceVariablesInTemplate,mailId);

			int a = totalResponse / 200;
			if (a == userJobData.size())
				return "Mail Send successfully";
			else
				return "Failed Sending Mail";
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	private void updateEmailBody(String replaceVariablesInTemplate, Long mailId) {
		if (mailId!=null) {
		Optional<MailNotification> findByMailIdAndIsDeleted = mailRepository.findByMailIdAndIsDeleted(mailId, false);
		if (findByMailIdAndIsDeleted.isPresent()) {
			
			MailNotification mailNotification = findByMailIdAndIsDeleted.get();
			mailNotification.setEmailBody(replaceVariablesInTemplate);
			mailRepository.save(mailNotification);
		}
		}
	}

}