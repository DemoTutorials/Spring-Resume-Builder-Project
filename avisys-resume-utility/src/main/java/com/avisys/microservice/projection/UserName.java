package com.avisys.microservice.projection;

import org.springframework.beans.factory.annotation.Value;

public interface UserName {

//	@Value("#{target.firstName + ' ' + target.lastName}")
	String getFirstName();
	String getLastName();
	
	String getUserId();
	
	default String getFullName() {
		return getFirstName()+" "+getLastName();
	}

}
