package com.avisys.microservice.util;

import java.util.Base64;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.SerializationUtils;
import org.quartz.JobDataMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.avisys.microservice.entity.EmailDetails;
import com.avisys.microservice.model.EmailContent;
import com.avisys.microservice.model.ScheduleEmailRequest;
import com.avisys.microservice.model.ScheduleEmailResponse;
import com.avisys.microservice.proxy.EmailServiceProxy;

@Component
public class Utility {

	Logger logger = LoggerFactory.getLogger(Utility.class);

	@Autowired
	Constants constant;

//	@Autowired
//	public ApplicationSqlWrapper sqlQueries = new ApplicationSqlWrapper();
	@Autowired
	private EmailServiceProxy emailServiceProxy;

	public List<Order> createSortingList(String[] sort, List<Order> orders) {

		if (sort[0].contains(",")) {
			for (String sortOrder : sort) {
				if (sortOrder.contains(",")) {
					String[] _sort = sortOrder.split(",");
					for (int i = 0; i < _sort.length; i++) {
						orders.add(i, new Order(
								sort[1].equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, _sort[i]));
					}
				} else {
					break;
				}
			}
		} else {
			orders.add(new Order(sort[1].equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC, sort[0]));
		}
		return orders;
	}

	public String createQueryWithSortOrder(List<Order> orders, String search, String sortByName, String query,
			String tableName) {

		StringBuilder sqlGetAll = null;

		sqlGetAll = new StringBuilder(query);
		sqlGetAll.append((search != null) ? search : "");
		if (query.contains("email.")) {
			sqlGetAll.append("%') as mytable order by ");
		} else {
			sqlGetAll.append("%') as mytable where is_deleted=false order by ");
		}

		if (orders.size() > 1) {
			for (int i = 0; i < orders.size(); i++) {
				if (i == 0) {
					sqlGetAll.append("" + orders.get(i).getProperty().trim() + ",");
				} else {
					if (i < orders.size()) {
						sqlGetAll.append("" + orders.get(i).getProperty().trim());
					} else {
						sqlGetAll.append("" + orders.get(i).getProperty().trim() + " ");
					}
				}
			}
			sqlGetAll.append(" " + sortByName);
		} else {
			sqlGetAll.append(orders.get(0).getProperty().trim() + " " + sortByName);
		}

		sqlGetAll.append(" limit :limit offset :offset");
		return sqlGetAll.toString();
	}

	public String uploadFile(MultipartFile file) {

		String url = constant.getEmailAndFileUpload();
		RestTemplate http = new RestTemplate();
		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.MULTIPART_FORM_DATA);
		MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
		ResponseEntity<String> postForEntity = null;

		body.add("file", file.getResource());
		HttpEntity<MultiValueMap<String, Object>> requestBody = new HttpEntity<>(body, header);
		postForEntity = http.postForEntity(url, requestBody, String.class);
		return postForEntity.getBody();
	}

	public <T> int getMaxVersion(String query, NamedParameterJdbcTemplate jdbcTemplate, String[] queryParameterName,
			T[]... value) {

		int maxversion = 1;

		MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
		if (queryParameterName.length == value.length) {
			for (int i = 0; i < queryParameterName.length; i++) {
				sqlParameterSource.addValue(queryParameterName[i], value[i][i]);
			}
		}

		Integer queryForObject = jdbcTemplate.queryForObject(query, sqlParameterSource, Integer.class);

		logger.info("Query is: {}", query);

		if (queryForObject == null) {
			return maxversion;
		}
		return maxversion + queryForObject.intValue();
	}
	
	public EmailContent getEmailContentFromByteArray(EmailDetails emailDetails) {
		byte[] bytes = emailDetails.getJobData();
		String encoded = Base64.getEncoder().encodeToString(bytes);
		byte[] decoded = Base64.getDecoder().decode(encoded);

		JobDataMap jobData = (JobDataMap) SerializationUtils.deserialize(decoded);

		Date date = new Date(emailDetails.getPrevFireTime());
		logger.info("{}", date);

		EmailContent eContent = new EmailContent();
		eContent.setEmailTo(jobData.getString("email"));
		eContent.setEmailSubject(jobData.getString("subject"));
		eContent.setEmailBody(jobData.getString("body"));
		eContent.setEmailScheduledDate(new Date(emailDetails.getStartTime()));

		return eContent;
	}

	public int scheduleEmail(ScheduleEmailRequest emailRequest) {
		logger.info("Inside schedule Mail:");
		ScheduleEmailResponse response = emailServiceProxy.scheduleEmail(emailRequest);
		logger.info("Status Code: {}", response.isSuccess());
		return response.isSuccess() == true ? 200 : 400;
	}

}
