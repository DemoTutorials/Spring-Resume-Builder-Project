package com.avisys.microservice.proxy;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.avisys.microservice.dto.APIEndpoints;

@FeignClient(name = "UAM-SERVICE")
public interface RegisterEndpoints {

	@PostMapping(value = "/uam-service/endpoints/save")
	public String registration(@RequestBody List<APIEndpoints> APIEndpoints);

}
