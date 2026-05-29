package com.avisys.email.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.avisys.email.payload.ScheduleEmailResponse;

@FeignClient(name = "UAM-SERVICE")
public interface UserServiceProxy {

	@GetMapping("/uam-service/get-user-details/{userId}")
	public String getUserNameByUserId(@PathVariable(value = "userId") String userId);

	
}
