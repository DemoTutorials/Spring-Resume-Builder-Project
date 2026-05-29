package com.avisys.email.daoImpl;

import java.util.ArrayList;
import java.util.List;

import org.quartz.JobDataMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import com.avisys.email.dao.EmailDao;
import com.avisys.email.model.EmailDetails;
import com.avisys.email.util.Utility;

@Service
public class EmailDaoImpl implements EmailDao {

	@Value("${email.paging-data}")
	private String emailPagingQuery;

	@Autowired
	NamedParameterJdbcTemplate jdbcTemplate;

	private Logger logger = LoggerFactory.getLogger(EmailDaoImpl.class);

	private String tableName = "email_job_data";

	@Autowired
	Utility utility;

	@Autowired
	JdbcTemplate jdbc;

	@Override
	public Page<EmailDetails> getEmailDetailsPaging(int pageNo, int pageSize, String[] sort, String search) {

		List<Order> orders = new ArrayList<>();
		orders = utility.createSortingList(sort, orders);

		String sortBy = orders.get(0).toString();
		logger.info("SortBy :" + sortBy);
		String sortByName = sortBy.contains("ASC") ? sortBy.substring(sortBy.indexOf("ASC"), sortBy.length())
				: sortBy.substring(sortBy.indexOf("DESC"), sortBy.length());

		Pageable paging = PageRequest.of(--pageNo, pageSize, Sort.by(orders));
		logger.info("page no:" + pageNo);
		logger.info("page size:" + pageSize);

		search = (search == null || search.contentEquals("")) ? "" : search;

		String totalCount = "select count(*) from " + tableName;

		Integer count = jdbc.queryForObject(totalCount, Integer.class);

		MapSqlParameterSource sqlParameters = new MapSqlParameterSource();
		sqlParameters.addValue("search", search);
		sqlParameters.addValue("limit", paging.getPageSize());
		sqlParameters.addValue("offset", paging.getOffset());

		String sqlGetAll = utility.createQueryWithSortOrder(orders, search != null ? search : "", sortByName,
				emailPagingQuery, tableName);

		List<EmailDetails> proList = jdbcTemplate.query(sqlGetAll, sqlParameters, new EmailDetailsMapper());
		
//		JobDataMap jobDataMap= (JobDataMap) proList.get(0).getJobData();
		
		logger.info("Job-Data : ");

		return new PageImpl<EmailDetails>(proList, paging, count);

	}

	@Override
	public JobDataMap getJobData(String jobName) {

		
		
		
		return null;
	}

}
