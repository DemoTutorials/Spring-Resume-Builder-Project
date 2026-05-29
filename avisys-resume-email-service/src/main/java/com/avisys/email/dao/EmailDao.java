package com.avisys.email.dao;

import java.util.List;

import org.quartz.JobDataMap;
import org.springframework.data.domain.Page;

import com.avisys.email.model.EmailDetails;

public interface EmailDao {

	public Page<EmailDetails> getEmailDetailsPaging(int pageNo, int pageSize, String[] sort,String search);
	
	public JobDataMap getJobData(String jobName);

}
