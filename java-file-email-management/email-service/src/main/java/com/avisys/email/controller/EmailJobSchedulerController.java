package com.avisys.email.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.avisys.email.job.EmailJob;
import com.avisys.email.payload.ScheduleEmailRequest;
import com.avisys.email.payload.ScheduleEmailResponse;
import com.avisys.email.payload.ServerResponse;
import com.avisys.email.service.FileStorageService;
import com.avisys.email.service.JobService;
import com.avisys.email.util.ServerResponseCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;

@RestController
public class EmailJobSchedulerController {
	private static final Logger logger = LoggerFactory.getLogger(EmailJobSchedulerController.class);

	@Autowired
	private Scheduler scheduler;

	@Autowired
	FileStorageService fileStorageService;
	
	@Value("${app-url-email}")
	private String appUrl;
	
	@Autowired
	@Lazy
	JobService jobService;
	
	@PostMapping(path = "/scheduleEmailWithAttachement", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.MULTIPART_FORM_DATA_VALUE })
	public ResponseEntity<ScheduleEmailResponse> scheduleEmailwithAttachment(@RequestPart MultipartFile file,
			@RequestParam String scheduleEmailRequest) throws IOException {
		try {
			ScheduleEmailRequest scheduleEmailRequestObject = new ObjectMapper().registerModule(new JSR310Module())
					.readValue(scheduleEmailRequest, ScheduleEmailRequest.class);

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
				return ResponseEntity.badRequest().body(scheduleEmailResponse);
			}

			logger.info("before calling to buildJobDetail");
			JobDetail jobDetail = buildJobDetail(scheduleEmailRequestObject, file);
			logger.info("after calling to buildJobDetail");
			Trigger trigger = buildJobTrigger(jobDetail, dateTime);
			scheduler.scheduleJob(jobDetail, trigger);

			ScheduleEmailResponse scheduleEmailResponse = new ScheduleEmailResponse(true, jobDetail.getKey().getName(),
					jobDetail.getKey().getGroup(), "Email Scheduled Successfully!");
			return ResponseEntity.ok(scheduleEmailResponse);
		} catch (SchedulerException ex) {
			logger.error("Error scheduling email", ex);

			ScheduleEmailResponse scheduleEmailResponse = new ScheduleEmailResponse(false,
					"Error scheduling email. Please try later!");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(scheduleEmailResponse);
		}
	}

//	@PostMapping("/uploadFile")
//	public String uploadFile(@RequestParam("file") MultipartFile file) {
//		
//		String fileName = fileStorageService.storeFile(file);
//
//		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/downloadFile/")
//				.path(fileName).toUriString();
//
//		return "File Uploaded..!\nDownload the same file from URI: " + fileDownloadUri;
//	}
	
	@GetMapping("jobs")
	public ResponseEntity<List<Map<String, Object>>> getAllJobs(){
		System.out.println("JobController.getAllJobs()");

		List<Map<String, Object>> list = jobService.getAllJobs();
		return ResponseEntity.ok(list);
	}
	
	
	@PostMapping("start")
	public ServerResponse startJobNow(@RequestParam("jobName") String jobName) {
		System.out.println("JobController.startJobNow()");

		if(jobService.isJobWithNamePresent(jobName)){

			if(!jobService.isJobRunning(jobName)){
				boolean status = jobService.startJobNow(jobName);

				if(status){
					//Success
					return getServerResponse(ServerResponseCode.SUCCESS, true);

				}else{
					//Server error
					return getServerResponse(ServerResponseCode.ERROR, false);
				}

			}else{
				//Job already running
				return getServerResponse(ServerResponseCode.JOB_ALREADY_IN_RUNNING_STATE, false);
			}

		}else{
			//Job doesn't exist
			return getServerResponse(ServerResponseCode.JOB_DOESNT_EXIST, false);
		}
	}

	@PostMapping("/uploadFile")
	public String uploadFile(@RequestParam("file") MultipartFile file) {
		
		String fileName = fileStorageService.storeFile(file);

		//do the changes here
		
		String fileDownloadUri=appUrl+"/downloadFile/"+fileName;
		
//		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/downloadFile/")
//				.path(fileName).toUriString();

		return "File Uploaded..!\nDownload the same file from URI: " + fileDownloadUri;
	}

    @GetMapping("/downloadFile/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        // Load file as Resource
        Resource resource = fileStorageService.loadFileAsResource(fileName);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

	@PostMapping(path = "/scheduleEmail",consumes = { MediaType.APPLICATION_JSON_VALUE})
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	public ResponseEntity<ScheduleEmailResponse> scheduleEmail(@RequestBody ScheduleEmailRequest scheduleEmailRequestObject)
			throws IOException {
		try {
			// Student student = new ObjectMapper().readValue(jsonString, Student.class);
//			ScheduleEmailRequest scheduleEmailRequestObject = new ObjectMapper().registerModule(new JSR310Module())
//					.readValue(scheduleEmailRequest, ScheduleEmailRequest.class);

			logger.error(scheduleEmailRequestObject.toString());
			logger.error("" + scheduleEmailRequestObject.getDateTime());
			logger.error("" + scheduleEmailRequestObject.getTimeZone());
			ZonedDateTime dateTime = ZonedDateTime.of(scheduleEmailRequestObject.getDateTime(),
					scheduleEmailRequestObject.getTimeZone());

			if (dateTime.isBefore(ZonedDateTime.now())) {
				ScheduleEmailResponse scheduleEmailResponse = new ScheduleEmailResponse(false,
						"dateTime must be after current time");
				return ResponseEntity.badRequest().body(scheduleEmailResponse);
			}

			logger.info("before calling to buildJobDetail");
			JobDetail jobDetail = buildJobDetail(scheduleEmailRequestObject, null);
			logger.info("after calling to buildJobDetail");
			Trigger trigger = buildJobTrigger(jobDetail, dateTime);
			scheduler.scheduleJob(jobDetail, trigger);
			
			ScheduleEmailResponse scheduleEmailResponse = new ScheduleEmailResponse(true, jobDetail.getKey().getName(),
					jobDetail.getKey().getGroup(), "Email Scheduled Successfully!");
			return ResponseEntity.ok(scheduleEmailResponse);
		} catch (SchedulerException ex) {
			logger.error("Error scheduling email", ex);

			ScheduleEmailResponse scheduleEmailResponse = new ScheduleEmailResponse(false,
					"Error scheduling email. Please try later!");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(scheduleEmailResponse);
		}
	}

	// JobDetails Conveys the detail properties of a given Job instance
	// JobDetail have a name and group associated with them, which should uniquely
	// identify them within a single Scheduler.

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
	
	
	public ServerResponse getServerResponse(int responseCode, Object data){
		ServerResponse serverResponse = new ServerResponse();
		serverResponse.setStatusCode(responseCode);
		serverResponse.setData(data);
		return serverResponse; 
	}
	
}
