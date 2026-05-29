package com.avisys.email.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.avisys.email.controller.EmailJobSchedulerController;
import com.avisys.email.job.EmailJob;
import com.avisys.email.payload.ScheduleEmailRequest;
import com.avisys.email.payload.ScheduleEmailResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;

@Service
public class MailTriggerService {
	
	private static final Logger logger = LoggerFactory.getLogger(EmailJobSchedulerController.class);

	@Autowired
	private Scheduler scheduler;

	@Autowired
	FileStorageService fileStorageService;
	
	@Value("${app-url}")
	private String appUrl;
	
	@Autowired
	@Lazy
	JobService jobService;
	
	
	
	public ScheduleEmailResponse scheduleEmailwithAttachment(MultipartFile file,
			ScheduleEmailRequest scheduleEmailRequest) throws IOException {
		try {
			ScheduleEmailRequest scheduleEmailRequestObject = scheduleEmailRequest;

			logger.error("" + file.getOriginalFilename());
			logger.error(scheduleEmailRequestObject.toString());
			logger.error("" + file.getOriginalFilename());
			logger.error("" + scheduleEmailRequestObject.getDateTime());
			logger.error("" + scheduleEmailRequestObject.getTimeZone());
			ZonedDateTime dateTime = ZonedDateTime.of(scheduleEmailRequestObject.getDateTime(),
					scheduleEmailRequestObject.getTimeZone());

			if (dateTime.isBefore(ZonedDateTime.now())) {
				ScheduleEmailResponse scheduleEmailResponse = new ScheduleEmailResponse(false,
						"dateTime must be after current time");
				return scheduleEmailResponse;
			}

			logger.info("before calling to buildJobDetail");
			JobDetail jobDetail = buildJobDetail(scheduleEmailRequestObject, file);
			logger.info("after calling to buildJobDetail");
			Trigger trigger = buildJobTrigger(jobDetail, dateTime);
			scheduler.scheduleJob(jobDetail, trigger);

			ScheduleEmailResponse scheduleEmailResponse = new ScheduleEmailResponse(true, jobDetail.getKey().getName(),
					jobDetail.getKey().getGroup(), "Email Scheduled Successfully!");
			return scheduleEmailResponse;
		} catch (SchedulerException ex) {
			logger.error("Error scheduling email", ex);

			ScheduleEmailResponse scheduleEmailResponse = new ScheduleEmailResponse(false,
					"Error scheduling email. Please try later!");
			return scheduleEmailResponse;
		}
	}
	
	
	public ScheduleEmailResponse scheduleEmail(ScheduleEmailRequest scheduleEmailRequestObject)
			throws IOException {
		try {
			// Student student = new ObjectMapper().readValue(jsonString, Student.class);
//			ScheduleEmailRequest scheduleEmailRequestObject = new ObjectMapper().registerModule(new JSR310Module())
//					.readValue(scheduleEmailRequest, ScheduleEmailRequest.class);

			logger.error(scheduleEmailRequestObject.toString());
			logger.error("get time: " + scheduleEmailRequestObject.getDateTime());
			logger.error("get zone: " + scheduleEmailRequestObject.getTimeZone());
//			logger.error("zonal DateTime: " + ZonedDateTime.now().withZoneSameLocal(ZoneId.of("Asia/Kolkata")));
			ZonedDateTime dateTime = ZonedDateTime.of(scheduleEmailRequestObject.getDateTime(),
					scheduleEmailRequestObject.getTimeZone());
			logger.error("to be compair dateTime: " + dateTime);

			if (dateTime.isBefore(ZonedDateTime.now())) {
				ScheduleEmailResponse scheduleEmailResponse = new ScheduleEmailResponse(false,
						"dateTime must be after current time");
				return scheduleEmailResponse;
			}

			logger.info("before calling to buildJobDetail");
			JobDetail jobDetail = buildJobDetail(scheduleEmailRequestObject, null);
			logger.info("after calling to buildJobDetail");
			Trigger trigger = buildJobTrigger(jobDetail, dateTime);
			scheduler.scheduleJob(jobDetail, trigger);
			
			ScheduleEmailResponse scheduleEmailResponse = new ScheduleEmailResponse(true, jobDetail.getKey().getName(),
					jobDetail.getKey().getGroup(), "Email Scheduled Successfully!");
			return scheduleEmailResponse;
		} catch (SchedulerException ex) {
			logger.error("Error scheduling email", ex);

			ScheduleEmailResponse scheduleEmailResponse = new ScheduleEmailResponse(false,
					"Error scheduling email. Please try later!");
			return scheduleEmailResponse;
		}
	}
	
	
	
	
	
	private JobDetail buildJobDetail(ScheduleEmailRequest scheduleEmailRequest, MultipartFile byteArr) throws IOException {

		// Holds state information for Job instances.
		// All the data to be sent in email such as subject, body, recipients email
		// address
		logger.info("inside buildJobDetail");
		JobDataMap jobDataMap = new JobDataMap();
		jobDataMap.put("email", scheduleEmailRequest.getEmail());
		jobDataMap.put("subject", scheduleEmailRequest.getSubject());
		jobDataMap.put("body", scheduleEmailRequest.getBody());
		if (byteArr != null) {
			jobDataMap.put("fileName",byteArr.getOriginalFilename());
			jobDataMap.put("attachment", byteArr.getBytes());
		}
		// JobBuilder is used to instantiate JobDetails

		return JobBuilder.newJob(EmailJob.class).withIdentity(scheduleEmailRequest.getEmail()+":T:"+LocalDateTime.now(), scheduleEmailRequest.getGroupName())
				// given name and group name to identify the JobDetail.
				.withDescription("Send Email Job")
				// provides the description of the job
				.usingJobData(jobDataMap).storeDurably()
				// Whether or not the Job should remain stored after it is orphaned (no Triggers
				// point to it).
				// If not explicitly set, the default value is false - this method sets the
				// value to true.

				.build();
	}

	
	
	private Trigger buildJobTrigger(JobDetail jobDetail, ZonedDateTime startAt) {
		return TriggerBuilder.newTrigger().forJob(jobDetail)
				// Set the identity of the Job which should be fired, by extracting the JobKey
				// from the given job
				.withIdentity(jobDetail.getKey().getName(), "email-triggers")
				// Use a TriggerKey with the given name and group to identify the Trigger.
				.withDescription("Send Email Trigger")
				// description of trigger
				.startAt(Date.from(startAt.toInstant()))
				// Set the time the Trigger should start at - the trigger may or may not fire at
				// this time -
				// depending upon the schedule configured for the Trigger. However the Trigger
				// will NOT
				// fire before this time,regardless of the Trigger's schedule.
				.withSchedule(SimpleScheduleBuilder.simpleSchedule().withMisfireHandlingInstructionFireNow())
				// Set the ScheduleBuilder that will be used to define theTrigger's schedule.
				.build();
	}


	
	
	public String uploadFile(MultipartFile file) {
		
		String fileName = fileStorageService.storeFile(file);

		//do the changes here
		
		String fileDownloadUri=appUrl+"/downloadFile/"+fileName;
		
//		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/downloadFile/")
//				.path(fileName).toUriString();

		return "File Uploaded..!\nDownload the same file from URI: " + fileDownloadUri;
	}
	
	
	
	
}

