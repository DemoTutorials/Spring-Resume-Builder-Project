package com.avisys.microservice.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.avisys.microservice.model.ScheduleEmailRequest;
import com.avisys.microservice.model.ScheduleEmailResponse;

@FeignClient(name = "EMAIL-SERVICE")
public interface EmailServiceProxy {

	@GetMapping("/email/scheduleEmail")
	public ScheduleEmailResponse scheduleEmail(@RequestBody ScheduleEmailRequest emailRequest);

	@PostMapping(path = "/email/uploadFile", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public String uploadFile(@RequestPart(name = "file") MultipartFile file);
	
}
