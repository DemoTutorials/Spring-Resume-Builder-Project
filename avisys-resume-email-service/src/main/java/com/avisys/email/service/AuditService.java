package com.avisys.email.service;

import java.lang.reflect.Method;
import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.avisys.email.Repository.AuditingInfoRepository;
import com.avisys.email.Repository.RevisionInfoRepository;
import com.avisys.email.dto.AvtivityLogDTO;
import com.avisys.email.dto.TempActivityDTO;
import com.avisys.email.entity.AuditingInfo;
import com.avisys.email.entity.RevisionInfo;
import com.avisys.email.proxy.UserServiceProxy;

@Service
public class AuditService {

//	TODO: need to call API - get user name by userID 
	
Logger logger = Logger.getLogger(AuditService.class.getName());
	
	@Autowired
	RevisionInfoRepository revisionInfoRepository;

	@Autowired
	UserServiceProxy userServiceProxy;

	@Autowired
	AuditingInfoRepository auditingInfoRepository;


	@Async
	public void auditing(String entityFlag, String revisionType, Long referenceId, String createdBy, String actionFor) {

		String userName = getUserName(createdBy);

		RevisionInfo revisionInfo = new RevisionInfo();
		revisionInfo.setActionBy(userName);
		revisionInfo.setEntityTag(entityFlag);
		revisionInfo.setReferenceId(referenceId);
		revisionInfo.setRevTstmp(Instant.now());
		revisionInfo.setRevType(revisionType);
		revisionInfo.setActionBy(createdBy);
		revisionInfo.setActionFor(actionFor);
		revisionInfoRepository.save(revisionInfo);
	}

	@Async
	public void auditing(Object oldObject, Object newObject, String entityFlag, String revisionType, Long referenceId,
			String updatedBy) {

		RevisionInfo revisionInfo = new RevisionInfo();
		revisionInfo.setEntityTag(entityFlag);
		revisionInfo.setReferenceId(referenceId);
		revisionInfo.setRevTstmp(Instant.now());
		revisionInfo.setRevType(revisionType);
		revisionInfo.setActionBy(updatedBy);
		RevisionInfo saveRevisionInfo = revisionInfoRepository.save(revisionInfo);

		try {
			String objectDifference = getObjectDifference(oldObject, newObject);

			if (objectDifference != null && !objectDifference.isEmpty()) {

				List<AuditingInfo> listAuditingInfo = new ArrayList<>();

				String[] split = objectDifference.split("::::");
				for (String string : split) {

					AuditingInfo auditingInfo = new AuditingInfo();
					String[] split2 = string.split("--->");
					if (split2.length > 0) {

						auditingInfo.setFieldName(split2[0]);

						if (split2.length > 1) {

							auditingInfo.setNewValue(split2[1]);
						} else {
							auditingInfo.setNewValue("");
						}

						if (split2.length > 2) {
							auditingInfo.setOldValue(split2[2]);
						} else {
							auditingInfo.setOldValue("");
						}

					}
					auditingInfo.setRevisionInfo(saveRevisionInfo);
					listAuditingInfo.add(auditingInfo);
				}

				auditingInfoRepository.saveAll(listAuditingInfo);

			}

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	@SuppressWarnings("unused")
	private String getObjectDifference(Object s1, Object s2) throws Exception {

		Method method = s1.getClass().getMethod("compareTo", s1.getClass(), s2.getClass());
		Object newInstance = Class.forName(s1.getClass().getName()).getConstructors()[0].newInstance();
		Object invoke = method.invoke(newInstance, s1, s2);
		return invoke.toString();

	}

	String getUserName(String userId) {
		if (userId==null) {
			return null;
		}
		return userServiceProxy.getUserNameByUserId(userId);

		
	}

	public Object getRevisionInfoByFlagAndReferenc(String entityTag, Long referenceId) {

		List<TempActivityDTO> tempActivity = revisionInfoRepository.getList(entityTag, referenceId);

		List<AvtivityLogDTO> activityConverter = activityConverter(tempActivity);

		logger.info("Count of Revision Info records :"+activityConverter.size());
		return activityConverter;
	}

	public List<AvtivityLogDTO> activityConverter(List<TempActivityDTO> tempActivity) {

		List<AvtivityLogDTO> list = new ArrayList<AvtivityLogDTO>();

		List<Long> revId = new ArrayList<Long>();
		tempActivity.forEach(n -> {

			revId.add(n.getRevId());
		});
		Set<Long> foo = new LinkedHashSet<Long>(revId);

		for (Long long1 : foo) {

			AvtivityLogDTO avtivityLogDTO = new AvtivityLogDTO();
			List<TempActivityDTO> collect = tempActivity.stream().filter(a -> a.getRevId().equals(long1))
					.collect(Collectors.toList());

			avtivityLogDTO.setChangedLog(
					collect.stream().filter(a -> a.getVariableName() != null).collect(Collectors.toList()));
			avtivityLogDTO.setMessage(setMessage(collect.get(0).getRevType(), collect.get(0).getActionBy(),
					collect.get(0).getActionFor()));
			avtivityLogDTO.setDate(collect.get(0).getRevTstmp());
			list.add(avtivityLogDTO);
		}

		return list;

	}

	public String setMessage(String action, String actionBy, String actionFor) {

		switch (action) {
		case "INSERT":
			return actionBy + " created the record";

		case "UPDATE":
			return actionBy + " has updated the record";

		case "DELETE":
			return actionBy + " has deleted the record";

		case "OWNER":
			return "The ownership is given by " + actionBy + " to " + actionFor;

		case "TEMPORARY_OWNER":
			return "The temporary ownership is given by " + actionBy + " to " + actionFor;

		default:
			return "";

		}

	}
}
