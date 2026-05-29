//package com.avisys.microservice.daoimpl;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//import javax.sql.DataSource;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cache.annotation.Cacheable;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//import org.springframework.data.domain.Sort.Order;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
//import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
//import org.springframework.jdbc.support.GeneratedKeyHolder;
//import org.springframework.jdbc.support.KeyHolder;
//import org.springframework.stereotype.Service;
//
//import com.avisys.microservice.dao.CommonMasterDao;
//import com.avisys.microservice.exception.CommonMasterNotFoundException;
//import com.avisys.microservice.exception.UniqueConstraintVoilationException;
//import com.avisys.microservice.mapper.CommonMasterMapper;
//import com.avisys.microservice.model.CommonMaster;
//import com.avisys.microservice.sql.ApplicationSqlWrapper;
//import com.avisys.microservice.util.Utility;
//
//@Service
//public class CommonMasterDaoImpl implements CommonMasterDao {
//
//	@Autowired
//	ApplicationSqlWrapper sqlQueries;
//
//	@Autowired
//	NamedParameterJdbcTemplate jdbcTemplate;
//
//	@Autowired
//	JdbcTemplate jdbc;
//
//	@Autowired
//	DataSource dataSource;
//
//	private static final String MASTER_NAME = "mst_name";
//
//	Logger logger = LoggerFactory.getLogger(CommonMasterDaoImpl.class);
//
//	private String tableName = "common_mst";
//
//	@Autowired
//	Utility utility;
//
//	@Cacheable(value = "one-minute-cache", key = "'CommonMaster'+#mstName+'foreign'+#foreignKey", condition = "#isCacheable != null && #isCacheable")
//	public List<CommonMaster> getCommonMasterDetails(String mstName, boolean isCacheable, String foreignKey) {
//
//		if (foreignKey == null) {
//
//			logger.info("Mst_name: {}", mstName);
//			MapSqlParameterSource sqlParameters = new MapSqlParameterSource();
//			sqlParameters.addValue(MASTER_NAME, mstName);
//			sqlParameters.addValue("like_operation", "%" + mstName + "%");
//			return jdbcTemplate.query(sqlQueries.getSqlGetCommonMaster(), sqlParameters, new CommonMasterMapper());
//
//		} else {
//			logger.info("Foreign Key: {}", foreignKey);
//			MapSqlParameterSource sqlParameters = new MapSqlParameterSource();
//			sqlParameters.addValue(MASTER_NAME, mstName);
//			sqlParameters.addValue("foreign_key", foreignKey);
//			return jdbcTemplate.query(sqlQueries.getSqlGetCommonMasterWithForeignkey(), sqlParameters,
//					new CommonMasterMapper());
//		}
//	}
//
//	public CommonMaster createNewCommonMaster(CommonMaster commonMaster) {
//
//		if (commonMaster.getForeignKey() == null || commonMaster.getForeignKey().isEmpty()) {
//			MapSqlParameterSource sqlParametersForDuplicate = new MapSqlParameterSource();
//			sqlParametersForDuplicate.addValue("mstName", commonMaster.getMstName());
//
//			Integer queryForObject = jdbcTemplate.queryForObject(sqlQueries.getSqlCheckIfCommonMasterExists(),
//					sqlParametersForDuplicate, Integer.class);
//
//			if (queryForObject >= 1) {
//				throw new UniqueConstraintVoilationException(
//						"Record with Same name: " + commonMaster.getMstName() + " already exists");
//			}
//		} else {
//			MapSqlParameterSource sqlParametersForDuplicate = new MapSqlParameterSource();
//			sqlParametersForDuplicate.addValue("foreignKey", commonMaster.getForeignKey());
//			sqlParametersForDuplicate.addValue("value", commonMaster.getValue());
//
//			Integer queryForObject = jdbcTemplate.queryForObject(sqlQueries.getSqlCheckIfChildCommonMasterExists(),
//					sqlParametersForDuplicate, Integer.class);
//
//			if (queryForObject >= 1) {
//				throw new UniqueConstraintVoilationException(
//						"Record with Same name: " + commonMaster.getValue() + " already exists");
//			}
//		}
//
//		KeyHolder key = new GeneratedKeyHolder();
//
//		MapSqlParameterSource sqlParameters = new MapSqlParameterSource();
//		sqlParameters.addValue(MASTER_NAME, commonMaster.getMstName());
//		sqlParameters.addValue("code", commonMaster.getCode());
//		sqlParameters.addValue("value", commonMaster.getValue());
//		sqlParameters.addValue("created_by", commonMaster.getCreatedBy());
//		sqlParameters.addValue("created_date", new Date());
//		sqlParameters.addValue("is_deleted", false);
//		sqlParameters.addValue("is_mst", commonMaster.getMst());
//		sqlParameters.addValue("foreign_key", commonMaster.getForeignKey());
//
//		int created = jdbcTemplate.update(sqlQueries.getSqlCreateCommonMaster(), sqlParameters, key);
//		logger.info("created Id: {}", created);
//
//		if (created == 1) {
//			commonMaster.setCommonMstId((int) key.getKeyList().get(0).get("common_mst_id"));
//			return commonMaster;
//		}
//		return null;
//	}
//
//	public CommonMaster updateCommonMaster(CommonMaster commonMaster) {
//
//		KeyHolder key = new GeneratedKeyHolder();
//
//		MapSqlParameterSource sqlParameters = new MapSqlParameterSource();
//		sqlParameters.addValue("mstName", commonMaster.getMstName());
//		sqlParameters.addValue("code", commonMaster.getCode());
//		sqlParameters.addValue("value", commonMaster.getValue());
//		sqlParameters.addValue("id", commonMaster.getCommonMstId());
//		sqlParameters.addValue("updatedBy", commonMaster.getUpdatedBy());
//		sqlParameters.addValue("updatedDate", new Date());
//		sqlParameters.addValue("isMst", commonMaster.getMst());
//		sqlParameters.addValue("foreignKey", commonMaster.getForeignKey());
//
//		int isUpdated = jdbcTemplate.update(sqlQueries.getSqlUpdateCommonMaster(), sqlParameters, key);
//		logger.info("isUpdated Id:{} ", isUpdated);
//		if (isUpdated == 1) {
//			return commonMaster;
//		}
//		return null;
//	}
//
//	public int deleteCommonMaster(long id, String updatedBy) {
//
//		// update common_mst set
//		// is_deleted=1,updated_by=:updatedBy,updated_date=:updatedDate where id=:id and
//		// is_deleted=0
//
//		MapSqlParameterSource sqlParameters = new MapSqlParameterSource();
//		sqlParameters.addValue("id", id);
//		sqlParameters.addValue("updatedDate", new Date());
//		sqlParameters.addValue("updatedBy", updatedBy);
//
//		return jdbcTemplate.update(sqlQueries.getSqlDeleteCommonMaster(), sqlParameters);
//	}
//
//	/*
//	 * Below Code is commented as of now as above API ( getCommonMasterDetails() )
//	 * works the same,and extracts the data as requested.
//	 */
//
//	@Override
//	@Cacheable(value = "one-minute-cache", key = "'CommonMaster'+#mst_name", condition = "#isCacheable != null && #isCacheable")
//	public List<CommonMaster> getCommonMasterTitle(boolean isCacheable) {
//		String mst_name = "Title";
//		try {
//			Thread.sleep(4000);
//		} catch (InterruptedException e) {
//			Thread.currentThread().interrupt();
//			e.printStackTrace();
//		}
//		MapSqlParameterSource sqlParameters = new MapSqlParameterSource();
//		sqlParameters.addValue(mst_name, mst_name);
//		return jdbcTemplate.query(sqlQueries.getSqlGetCommonMaster(), sqlParameters, new CommonMasterMapper());
//	}
//
//	@Override
//	@Cacheable(value = "one-minute-cache", key = "'CommonMaster'+#mst_name", condition = "#isCacheable != null && #isCacheable")
//	public List<CommonMaster> getCommonMasterIdType(boolean isCacheable) {
//		MapSqlParameterSource sqlParameters = new MapSqlParameterSource();
//		sqlParameters.addValue(MASTER_NAME, "ID Type");
//		return jdbcTemplate.query(sqlQueries.getSqlGetCommonMaster(), sqlParameters, new CommonMasterMapper());
//	}
//
//	@Override
//	@Cacheable(value = "one-minute-cache", key = "'CommonMaster'+#mst_name", condition = "#isCacheable != null && #isCacheable")
//	public List<CommonMaster> getCommonMasterAddressType(boolean isCacheable) {
//		MapSqlParameterSource sqlParameters = new MapSqlParameterSource();
//		sqlParameters.addValue(MASTER_NAME, "Address Type");
//		return jdbcTemplate.query(sqlQueries.getSqlGetCommonMaster(), sqlParameters, new CommonMasterMapper());
//	}
//
//	@Override
//	@Cacheable(value = "one-minute-cache", key = "'CommonMaster'+#mst_name", condition = "#isCacheable != null && #isCacheable")
//	public List<CommonMaster> getCommonMasterCity(boolean isCacheable) {
//		MapSqlParameterSource sqlParameters = new MapSqlParameterSource();
//		sqlParameters.addValue(MASTER_NAME, "City");
//		return jdbcTemplate.query(sqlQueries.getSqlGetCommonMaster(), sqlParameters, new CommonMasterMapper());
//	}
//
//	@Override
//	@Cacheable(value = "one-minute-cache", key = "'CommonMaster'+#mst_name", condition = "#isCacheable != null && #isCacheable")
//	public List<CommonMaster> getCommonMasterState(boolean isCacheable) {
//		MapSqlParameterSource sqlParameters = new MapSqlParameterSource();
//		sqlParameters.addValue(MASTER_NAME, "State");
//		return jdbcTemplate.query(sqlQueries.getSqlGetCommonMaster(), sqlParameters, new CommonMasterMapper());
//	}
//
//	@Override
//	@Cacheable(value = "one-minute-cache", key = "'CommonMaster'+#mst_name", condition = "#isCacheable != null && #isCacheable")
//	public List<CommonMaster> getCommonMasterCountry(boolean isCacheable) {
//		MapSqlParameterSource sqlParameters = new MapSqlParameterSource();
//		sqlParameters.addValue(MASTER_NAME, "Country");
//		return jdbcTemplate.query(sqlQueries.getSqlGetCommonMaster(), sqlParameters, new CommonMasterMapper());
//	}
//
//	@Override
//	@Cacheable(value = "one-minute-cache", key = "'CommonMaster'+#mst_name", condition = "#isCacheable != null && #isCacheable")
//	public List<CommonMaster> getCommonMasterPinCode(boolean isCacheable) {
//		MapSqlParameterSource sqlParameters = new MapSqlParameterSource();
//		sqlParameters.addValue(MASTER_NAME, "Pin Code");
//		return jdbcTemplate.query(sqlQueries.getSqlGetCommonMaster(), sqlParameters, new CommonMasterMapper());
//	}
//
//	@Override
//	@Cacheable(value = "one-minute-cache", key = "'CommonMaster'+#mst_name", condition = "#isCacheable != null && #isCacheable")
//	public List<CommonMaster> getCommonMasterRelationship(boolean isCacheable) {
//		MapSqlParameterSource sqlParameters = new MapSqlParameterSource();
//		sqlParameters.addValue(MASTER_NAME, "Relationship");
//		return jdbcTemplate.query(sqlQueries.getSqlGetCommonMaster(), sqlParameters, new CommonMasterMapper());
//	}
//
//	@Override
//	@Cacheable(value = "one-minute-cache", key = "'CommonMaster'+#mst_name", condition = "#isCacheable != null && #isCacheable")
//	public List<CommonMaster> getCommonMasterLineOfBusiness(boolean isCacheable) {
//		MapSqlParameterSource sqlParameters = new MapSqlParameterSource();
//		sqlParameters.addValue(MASTER_NAME, "Line of Business");
//		return jdbcTemplate.query(sqlQueries.getSqlGetCommonMaster(), sqlParameters, new CommonMasterMapper());
//	}
//
//	@Override
//	@Cacheable(value = "one-minute-cache", key = "'CommonMaster'+#mst_name", condition = "#isCacheable != null && #isCacheable")
//	public List<CommonMaster> getCommonMasterGender(boolean isCacheable) {
//		MapSqlParameterSource sqlParameters = new MapSqlParameterSource();
//		sqlParameters.addValue(MASTER_NAME, "Gender");
//		return jdbcTemplate.query(sqlQueries.getSqlGetCommonMaster(), sqlParameters, new CommonMasterMapper());
//	}
//
//	@Override
//	@Cacheable(value = "one-minute-cache", key = "'CommonMaster'+#mst_name", condition = "#isCacheable != null && #isCacheable")
//	public List<CommonMaster> getCommonMasterMaritalStatus(boolean isCacheable) {
//		MapSqlParameterSource sqlParameters = new MapSqlParameterSource();
//		sqlParameters.addValue(MASTER_NAME, "Marital Status");
//		return jdbcTemplate.query(sqlQueries.getSqlGetCommonMaster(), sqlParameters, new CommonMasterMapper());
//	}
//
//	@Override
//	@Cacheable(value = "one-minute-cache", key = "'CommonMaster'+#mst_name", condition = "#isCacheable != null && #isCacheable")
//	public List<CommonMaster> getCommonMasterOwnershipStatus(boolean isCacheable) {
//		MapSqlParameterSource sqlParameters = new MapSqlParameterSource();
//		sqlParameters.addValue(MASTER_NAME, "Ownership status");
//		return jdbcTemplate.query(sqlQueries.getSqlGetCommonMaster(), sqlParameters, new CommonMasterMapper());
//	}
//
//	@Override
//	@Cacheable(value = "one-minute-cache", key = "'CommonMaster'+#mst_name", condition = "#isCacheable != null && #isCacheable")
//	public List<CommonMaster> getCommonMasterCurrentOccupation(boolean isCacheable) {
//		MapSqlParameterSource sqlParameters = new MapSqlParameterSource();
//		sqlParameters.addValue(MASTER_NAME, "Current Occupation");
//		return jdbcTemplate.query(sqlQueries.getSqlGetCommonMaster(), sqlParameters, new CommonMasterMapper());
//	}
//
//	@Override
//	@Cacheable(value = "one-minute-cache", key = "'CommonMaster'+#mst_name", condition = "#isCacheable != null && #isCacheable")
//	public List<CommonMaster> getCommonMasterLastEducationLevel(boolean isCacheable) {
//		MapSqlParameterSource sqlParameters = new MapSqlParameterSource();
//		sqlParameters.addValue(MASTER_NAME, "Last Education Level");
//		return jdbcTemplate.query(sqlQueries.getSqlGetCommonMaster(), sqlParameters, new CommonMasterMapper());
//	}
//
//	@Override
//	@Cacheable(value = "one-minute-cache", key = "'CommonMaster'+#mst_name", condition = "#isCacheable != null && #isCacheable")
//	public List<CommonMaster> getCommonMasterIncomeGroup(boolean isCacheable) {
//		MapSqlParameterSource sqlParameters = new MapSqlParameterSource();
//		sqlParameters.addValue(MASTER_NAME, "Income Group");
//		return jdbcTemplate.query(sqlQueries.getSqlGetCommonMaster(), sqlParameters, new CommonMasterMapper());
//	}
//
//	@Override
//	public List<CommonMaster> getCommonMasterNationality(boolean isCacheable) {
//		MapSqlParameterSource sqlParameters = new MapSqlParameterSource();
//		sqlParameters.addValue(MASTER_NAME, "Nationality");
//		return jdbcTemplate.query(sqlQueries.getSqlGetCommonMaster(), sqlParameters, new CommonMasterMapper());
//	}
//
//	@Override
//	@Cacheable(value = "one-minute-cache", key = "'CommonMaster'+#mst_name", condition = "#isCacheable != null && #isCacheable")
//	public List<CommonMaster> getCommonMasterReligion(boolean isCacheable) {
//		MapSqlParameterSource sqlParameters = new MapSqlParameterSource();
//		sqlParameters.addValue(MASTER_NAME, "Religion");
//		return jdbcTemplate.query(sqlQueries.getSqlGetCommonMaster(), sqlParameters, new CommonMasterMapper());
//	}
//
//	@Override
//	@Cacheable(value = "one-minute-cache", key = "'CommonMaster'+#mst_name", condition = "#isCacheable != null && #isCacheable")
//	public List<CommonMaster> getCommonMasterPreferredLanguage(boolean isCacheable) {
//		MapSqlParameterSource sqlParameters = new MapSqlParameterSource();
//		sqlParameters.addValue(MASTER_NAME, "Preferred Language");
//		return jdbcTemplate.query(sqlQueries.getSqlGetCommonMaster(), sqlParameters, new CommonMasterMapper());
//	}
//
//	@Override
//	@Cacheable(value = "one-minute-cache", key = "'CommonMaster'+#mst_name", condition = "#isCacheable != null && #isCacheable")
//	public List<CommonMaster> getCommonMasterPaymentMode(boolean isCacheable) {
//		MapSqlParameterSource sqlParameters = new MapSqlParameterSource();
//		sqlParameters.addValue(MASTER_NAME, "Payment Mode");
//		return jdbcTemplate.query(sqlQueries.getSqlGetCommonMaster(), sqlParameters, new CommonMasterMapper());
//	}
//
//	@Override
//	@Cacheable(value = "one-minute-cache", key = "'CommonMaster'+#mst_name", condition = "#isCacheable != null && #isCacheable")
//	public List<CommonMaster> getCommonMasterCreditCardType(boolean isCacheable) {
//		MapSqlParameterSource sqlParameters = new MapSqlParameterSource();
//		sqlParameters.addValue(MASTER_NAME, "Credit Card Type");
//		return jdbcTemplate.query(sqlQueries.getSqlGetCommonMaster(), sqlParameters, new CommonMasterMapper());
//	}
//
//	@Override
//	@Cacheable(value = "one-minute-cache", key = "'CommonMaster'+#mst_name", condition = "#isCacheable != null && #isCacheable")
//	public List<CommonMaster> getCommonMasterBillingMedia(boolean isCacheable) {
//		MapSqlParameterSource sqlParameters = new MapSqlParameterSource();
//		sqlParameters.addValue(MASTER_NAME, "Billing Media");
//		return jdbcTemplate.query(sqlQueries.getSqlGetCommonMaster(), sqlParameters, new CommonMasterMapper());
//	}
//
//	@Override
//	@Cacheable(value = "one-minute-cache", key = "'CommonMaster'+#mst_name", condition = "#isCacheable != null && #isCacheable")
//	public List<CommonMaster> getCommonMasterBillingPreferredLanguage(boolean isCacheable) {
//		MapSqlParameterSource sqlParameters = new MapSqlParameterSource();
//		sqlParameters.addValue(MASTER_NAME, "Billing Preferred language");
//		return jdbcTemplate.query(sqlQueries.getSqlGetCommonMaster(), sqlParameters, new CommonMasterMapper());
//	}
//
//	@Override
//	@Cacheable(value = "one-minute-cache", key = "'CommonMaster'+#mst_name", condition = "#isCacheable != null && #isCacheable")
//	public List<CommonMaster> getCommonMasterBillingCycle(boolean isCacheable) {
//		MapSqlParameterSource sqlParameters = new MapSqlParameterSource();
//		sqlParameters.addValue(MASTER_NAME, "Billing Cycle");
//		return jdbcTemplate.query(sqlQueries.getSqlGetCommonMaster(), sqlParameters, new CommonMasterMapper());
//	}
//
//	@Override
//	public Page<CommonMaster> fetchAllParentCommonMaster(int pageNo, int pageSize, String[] sort, String search) {
//
//		List<Order> orders = new ArrayList<>();
//		orders = utility.createSortingList(sort, orders);
//
//		String sortBy = orders.get(0).toString();
//		logger.info("SortBy : {}", sortBy);
//		String sortByName = sortBy.contains("ASC") ? sortBy.substring(sortBy.indexOf("ASC"), sortBy.length())
//				: sortBy.substring(sortBy.indexOf("DESC"), sortBy.length());
//
//		Pageable paging = PageRequest.of(--pageNo, pageSize, Sort.by(orders));
//		logger.info("page no: {}", pageNo);
//		logger.info("page size: {}", pageSize);
//
//		search = (search == null || search.contentEquals("")) ? "" : search;
//
//		String totalCount = "select count(*) from " + tableName
//				+ " where is_mst=true and is_deleted=false and mst_name ilike '%" + search + "%'";
//
//		Integer count = jdbc.queryForObject(totalCount, Integer.class);
//
//		MapSqlParameterSource sqlParameters = new MapSqlParameterSource();
//		sqlParameters.addValue("search", search);
//		sqlParameters.addValue("limit", paging.getPageSize());
//		sqlParameters.addValue("offset", paging.getOffset());
//
//		String sqlGetAll = utility.createQueryWithSortOrder(orders, search != null ? search : "", sortByName,
//				sqlQueries.getSqlCommonMasterParentPaging(), tableName);
//
//		List<CommonMaster> proList = jdbcTemplate.query(sqlGetAll, sqlParameters, new CommonMasterMapper());
//
//		return new PageImpl<>(proList, paging, count);
//	}
//
//	@Override
//	public Page<CommonMaster> fetchAllChildCommonMaster(int pageNo, int pageSize, String[] sort, String search,
//			String foreignKey) {
//
//		List<Order> orders = new ArrayList<>();
//		orders = utility.createSortingList(sort, orders);
//
//		String sortBy = orders.get(0).toString();
//		logger.info("SortBy : {}", sortBy);
//		String sortByName = sortBy.contains("ASC") ? sortBy.substring(sortBy.indexOf("ASC"), sortBy.length())
//				: sortBy.substring(sortBy.indexOf("DESC"), sortBy.length());
//
//		Pageable paging = PageRequest.of(--pageNo, pageSize, Sort.by(orders));
//		logger.info("page no: {}", pageNo);
//		logger.info("page size: {}", pageSize);
//
//		search = (search == null || search.contentEquals("")) ? "" : search;
//
//		String totalCount = "select count(*) from " + tableName + " where is_deleted=false and value ilike '%" + search
//				+ "%' and foreign_key='" + foreignKey + "'";
//
//		Integer count = jdbc.queryForObject(totalCount, Integer.class);
//
//		MapSqlParameterSource sqlParameters = new MapSqlParameterSource();
//		sqlParameters.addValue("foreignKey", "%" + foreignKey + "%");
//		sqlParameters.addValue("search", search);
//		sqlParameters.addValue("limit", pageSize);
//		sqlParameters.addValue("offset", paging.getOffset());
//
//		String sqlGetAll = utility.createQueryWithSortOrder(orders, search != null ? search : "", sortByName,
//				sqlQueries.getSqlCommonMasterChildPaging(), tableName);
//
//		List<CommonMaster> proList = jdbcTemplate.query(sqlGetAll, sqlParameters, new CommonMasterMapper());
//
//		return new PageImpl<>(proList, paging, count);
//	}
//
//	@Override
//	public CommonMaster getCommonMasterById(int id) {
//
//		MapSqlParameterSource sqlParameter = new MapSqlParameterSource();
//		sqlParameter.addValue("id", id);
//
//		CommonMaster commonMaster = new CommonMaster();
//		try {
//			commonMaster = jdbcTemplate.queryForObject(sqlQueries.getSqlGetCommonMasterById(), sqlParameter,
//					new CommonMasterMapper());
//			return commonMaster;
//		} catch (Exception e) {
//			throw new CommonMasterNotFoundException("No such Common Master found with id: " + id);
//		}
//	}
//
//}
