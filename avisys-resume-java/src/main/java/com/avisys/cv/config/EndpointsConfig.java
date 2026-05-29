package com.avisys.cv.config;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import com.avisys.cv.dto.APIEndpoints;
import com.avisys.cv.service.ApiEndPointService;

@Configuration
public class EndpointsConfig {

	@Autowired
	ApplicationContext applicationContext;
	
	@Autowired
	ApiEndPointService endpointConfig;
	
	
	@Bean
	public void loadEndPoints() {

		List<APIEndpoints> list = new ArrayList<APIEndpoints>();
		RequestMappingHandlerMapping requestMappingHandlerMapping = applicationContext
				.getBean("requestMappingHandlerMapping", RequestMappingHandlerMapping.class);
		Map<RequestMappingInfo, HandlerMethod> map = requestMappingHandlerMapping.getHandlerMethods();

		map.forEach((key, value) -> {
			if (!value.getBean().equals("basicErrorController")) {

				APIEndpoints endpoints = new APIEndpoints();
				endpoints.setControllerName(value.getBean() + "");
				endpoints.setMethodName(Test.methodName(key.getMethodsCondition() + ""));
				String endpoint = key.getPathPatternsCondition() + "";
				endpoint = endpoint.substring(1, endpoint.length() - 1);
				endpoints.setPath("/cv" + endpoint);
				endpoints.setServiceName("java-cv");
				endpoints.setCreationDate(LocalDateTime.now());
				list.add(endpoints);
			}
		});
		endpointConfig.configuration(list);
	}
}
