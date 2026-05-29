package com.avisys.microservice.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.avisys.microservice.entity.EmailTemplate;
import com.avisys.microservice.model.EmailRequestBody;
import com.avisys.microservice.model.EmailTemplateVariable;
import com.avisys.microservice.model.ScheduleEmailRequest;
import com.avisys.microservice.service.EmailTemplateService;
import com.avisys.microservice.service.UserDetailsService;

@Service
public class EmailTemplateDetails {


	@Autowired
	EmailTemplateService emailTemplateService;
	@Autowired
	UserDetailsService user;
	@Autowired
	Utility utility;

	private String emailTemplateName = "";
	private String emailTemplateSubject = "";

	private static final Logger logger = LoggerFactory.getLogger(EmailTemplateDetails.class);

	public String getTemplateData(String id) {

		EmailTemplate emailTemplate = emailTemplateService.getById(Long.parseLong(id));
		emailTemplateName = emailTemplate.getName();
		emailTemplateSubject = emailTemplate.getEmailTemplateSubject();
		return emailTemplate.getContent();

	}

	public List<EmailTemplateVariable> getVariablesData(String recipients, String jobName,
			Map<String, String> jobData) {

		List<EmailTemplateVariable> list = new ArrayList<>();
		List<EmailTemplateVariable> dataList = new ArrayList<>();

		String[] split = recipients.split(",");
		List<String> recipientsList = new ArrayList<>();

		Collections.addAll(recipientsList, split);

		if(jobName.contains("mail-service")) {
			list=new ArrayList<>();
			EmailTemplateVariable etv=new EmailTemplateVariable();
//			etv.setTo(recipientsList.toString());
			for (Map.Entry<String,String> entry : jobData.entrySet()) {
				String key = entry.getKey();
				switch(key) {
				case "requestNumber": etv.setRequestNumber(entry.getValue()); break;
				case "accountNumber": etv.setAccountNumber(entry.getValue()); break;
				case "requestCallType": etv.setRequestCallType(entry.getValue()); break;
				case "billingAccount": etv.setBillingAccount(entry.getValue()); break;
				case "redirectUrl": etv.setRedirectUrl(entry.getValue()); break;
				case "date": etv.setDate(entry.getValue()); break;
				case "customerName": etv.setCustomerName(entry.getValue()); break;
				case "text" : etv.setText(entry.getValue()); break;
				default:  break;
				}
			}
			if(recipientsList.size()>1) {
				for(String to : recipientsList) {
					EmailTemplateVariable duplicate=new EmailTemplateVariable(etv);
					duplicate.setTo(to);
					list.add(duplicate);
				}
			}else {
				etv.setTo(recipientsList.get(0));
				list.add(etv);
			}
//			list.add(etv);
			return list;
			
		} else {

			if (jobName.equalsIgnoreCase("user")) {
				list = emailTemplateService.getUsersByEmail(recipientsList);
			} else {
				if (jobName.equalsIgnoreCase("user-creation") || jobName.equalsIgnoreCase("forgot-pass")
						|| jobName.equalsIgnoreCase("user-update")) {
					list = user.getUserByEmail(recipientsList);
				}
			}
			logger.info("Before setting values to the variables");

			for (int i = 0; i < list.size(); i++) {
				EmailTemplateVariable emailDetails = new EmailTemplateVariable();
				emailDetails.setCustomerName(list.get(i).getCustomerName());
				emailDetails.setCustomerEmail(list.get(i).getCustomerEmail());
				emailDetails.setCustomerAddressType(list.get(i).getCustomerAddressType());
				emailDetails.setTo(emailDetails.getCustomerEmail());
				emailDetails.setCustomerBilingAmount(list.get(i).getCustomerBilingAmount());
				emailDetails.setCustomerBillingDate(list.get(i).getCustomerBillingDate());
				emailDetails.setCustomerContactNumber(list.get(i).getCustomerContactNumber());

				emailDetails.setRequestNumber(list.get(i).getRequestNumber());
				emailDetails.setAccountNumber(list.get(i).getAccountNumber());
				emailDetails.setRequestCallType(list.get(i).getRequestCallType());
				emailDetails.setBillingAccount(list.get(i).getBillingAccount());
				emailDetails.setRedirectUrl(list.get(i).getRedirectUrl());
				emailDetails.setDate(list.get(i).getDate());

				if (jobData != null) {
					emailDetails.setForgetPasswordUrl(jobData.get("forgetPasswordUrl"));
					emailDetails.setTemporaryPassword(jobData.get("temporaryPassword"));
				}

				dataList.add(emailDetails);
			}

		}

		return dataList;
	}

	public String replaceEmailTemplateData(String templateContent, List<EmailTemplateVariable> variables,
			Date dateTime) {
		logger.info("Before executing replaceEmailTemplateData()");
		logger.info("size of the list: {} ", variables.size());
		int totalResponse = 0;
		for (int i = 0; i < variables.size(); i++) {

			String finalRequestBody = replaceVariablesInTemplate(templateContent, variables.get(i));
			
			totalResponse = totalResponse + scheduleEmail(finalRequestBody, variables.get(i).getTo());
		}

		int a = totalResponse / 200;
		if (a == variables.size())
			return "Mail Send successfully";
		else
			return "Failed Sending Mail";

	}

	public int scheduleEmail(String finalRequestBody, String to) {
		logger.info("Inside schedule email API for: {}", to);
		ScheduleEmailRequest emailRequest = new ScheduleEmailRequest();
		emailRequest.setEmail(to);
		emailRequest.setBody(finalRequestBody);
		emailRequest.setDateTime(LocalDateTime.now().plusSeconds(10));
		emailRequest.setGroupName(emailTemplateName);
		emailRequest.setSubject(emailTemplateSubject);
		emailRequest.setTimeZone(ZoneId.of("Asia/Kolkata"));
		logger.info("Before scheduling the call:");
		return utility.scheduleEmail(emailRequest);

	}

	public LocalDateTime convertToLocalDateTimeViaMilisecond(Date dateToConvert) {
		return Instant.ofEpochMilli(dateToConvert.getTime()).atZone(ZoneId.of("Asia/Kolkata")).toLocalDateTime();
	}

	public String scheduleEmailRequest(EmailRequestBody emailBody) {

		logger.info(emailBody.toString());

		String templateData = getTemplateData(emailBody.getTemplateId());
		logger.info("Template Data: {}", templateData);

		List<EmailTemplateVariable> variablesData = getVariablesData(emailBody.getRecipient(), emailBody.getJobName(),
				emailBody.getJobData());
		if (variablesData.isEmpty()) {
			return "" + HttpStatus.NOT_FOUND;
		}
		return replaceEmailTemplateData(templateData, variablesData, emailBody.getScheduleTime());
	}
	
	public String replaceVariablesInTemplate(String templateData,EmailTemplateVariable variablesData) {
		
		String finalRequestBody = new String(templateData);

		finalRequestBody = finalRequestBody.replace("{{customerName}}",
				variablesData.getCustomerName() == null ? "" : variablesData.getCustomerName());
		finalRequestBody = finalRequestBody.replace("{{customerEmail}}",
				variablesData.getCustomerEmail() == null ? "" : variablesData.getCustomerEmail());
		finalRequestBody = finalRequestBody.replace("{{customerContactNumber}}",
				variablesData.getCustomerContactNumber() == null ? ""
						: variablesData.getCustomerContactNumber());
		finalRequestBody = finalRequestBody.replace("{{customerBilingAmount}}",
				(variablesData.getCustomerBilingAmount() == null ? ""
						: variablesData.getCustomerBilingAmount()));
		finalRequestBody = finalRequestBody.replace("{{customerBillingDate}}",
				variablesData.getCustomerBillingDate() == null ? "" : variablesData.getCustomerBillingDate());
		finalRequestBody = finalRequestBody.replace("{{customerAddressType}}",
				variablesData.getCustomerAddressType() == null ? "" : variablesData.getCustomerAddressType());
		finalRequestBody = finalRequestBody.replace("{{customerTemporaryPassword}}",
				variablesData.getTemporaryPassword() == null ? "" : variablesData.getTemporaryPassword());
		finalRequestBody = finalRequestBody.replace("{{forgetPasswordUrl}}",
				variablesData.getForgetPasswordUrl() == null ? "" : variablesData.getForgetPasswordUrl());
		
		finalRequestBody = finalRequestBody.replace("{{requestNumber}}",
				variablesData.getRequestNumber() == null ? "" : variablesData.getRequestNumber());
		finalRequestBody = finalRequestBody.replace("{{accountNumber}}",
				variablesData.getAccountNumber() == null ? "" : variablesData.getAccountNumber());
		finalRequestBody = finalRequestBody.replace("{{requestCallType}}",
				variablesData.getRequestCallType() == null ? "" : variablesData.getRequestCallType());
		finalRequestBody = finalRequestBody.replace("{{billingAccount}}",
				variablesData.getBillingAccount() == null ? "" : variablesData.getBillingAccount());
		finalRequestBody = finalRequestBody.replace("{{redirectUrl}}",
				variablesData.getRedirectUrl() == null ? "" : variablesData.getRedirectUrl());
		finalRequestBody = finalRequestBody.replace("{{date}}",
				variablesData.getDate() == null ? "" : variablesData.getDate());
		finalRequestBody = finalRequestBody.replace("{{text}}",
				variablesData.getText() == null ? "" : variablesData.getText());
		
		return finalRequestBody;
	}

}