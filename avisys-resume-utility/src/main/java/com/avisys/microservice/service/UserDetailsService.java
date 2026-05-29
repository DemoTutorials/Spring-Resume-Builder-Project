package com.avisys.microservice.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.PersistenceException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import com.avisys.microservice.dto.User;
import com.avisys.microservice.entity.Designation;
import com.avisys.microservice.entity.UserInfo;
import com.avisys.microservice.exception.ResourceNotFoundException;
import com.avisys.microservice.model.EmailTemplateVariable;
import com.avisys.microservice.projection.EmailValidateDTO;
import com.avisys.microservice.projection.UserData;
import com.avisys.microservice.projection.UserName;
import com.avisys.microservice.repository.DesignationRepository;
import com.avisys.microservice.repository.UserInfoRepository;

@Service
public class UserDetailsService {

	@Autowired
	private UserInfoRepository userRepository;
	@Autowired
	private DesignationRepository designationRepository;
	@Autowired
	UploadFileService uploadFileService;
	@Value("${profilePhotoSize}")
	private Long profilePhotoSize;

	private static final Logger log = LoggerFactory.getLogger(UserDetailsService.class);

	private String reportsTo = "0";

	public List<UserData> getListOfUsers(String owner) {

		return userRepository.findByUserIdNotAndDeleted(owner, false);
	}

	public Optional<UserInfo> getById(Long id) {
		return userRepository.findByIdAndDeleted(id, false);
	}
	
	public Optional<UserInfo> getByUserId(String id) {
		return userRepository.findByUserId(id);
	}

	public UserInfo assignDesignation(Long userId, Long designation) {

		Optional<UserInfo> user = getById(userId);
		UserInfo userDetails = null;
		if (user.isPresent()) {
			userDetails = user.get();
			Optional<Designation> findById = designationRepository.findById(designation);
			if (findById.isPresent()) {
				userDetails.setDesignation(findById.get());
				return userRepository.save(userDetails);
			}
		}
		return null;
	}

	public UserInfo save(User user) {
		UserInfo userDetails = new UserInfo();
		userDetails.setFirstName(user.getFirstName());
		userDetails.setLastName(user.getLastName());
		userDetails.setUserId(user.getUserId());
		userDetails.setCreatedBy(user.getCreatedBy());
		userDetails.setUserName(user.getUserName());
		userDetails.setRealmId(user.getRealmId());
		userDetails.setReportsTo(user.getReportsTo() != null ? user.getReportsTo() : reportsTo);
		userDetails.setEmail(user.getEmail());
		userDetails.setEnabled(user.getIsEnabled());
		if (user.getDesignationId() != null) {
			Optional<Designation> findById = designationRepository.findById(user.getDesignationId());
			if (findById.isPresent()) {
				userDetails.setDesignation(findById.get());
			}
		}

		return userRepository.save(userDetails);
	}

	public UserInfo update(User user, MultipartFile file) {
		String uploadFile="";
		if(file!=null) {
			if (!file.getOriginalFilename().isEmpty() && file.getSize() > profilePhotoSize) {
				throw new PersistenceException("Profile photo size should be less than " + profilePhotoSize + "Bytes");
			}
			if(!file.getOriginalFilename().isBlank()) {
				uploadFile = uploadFileService.uploadFile(file);				
			}
		}
		UserInfo existingUserId = userRepository.findByUserId(user.getUserId())
				.orElseThrow(() -> new ResourceNotFoundException("No such user found to update"));
		existingUserId.setProfileImage(uploadFile);
		existingUserId.setContact(user.getContact());
		existingUserId.setUpdatedBy(user.getUpdatedBy());
		return userRepository.save(existingUserId);
	}

	
	public UserInfo softDelete(String email) throws Exception {
		Optional<UserInfo> findByEmail = userRepository.findByEmail(email);
		UserInfo user = new UserInfo();
		if (findByEmail.isPresent()) {
			user = findByEmail.get();
			user.setDeleted(true);
			return userRepository.save(user);
		}
		throw new Exception("User not present to delete");
	}

	public Page<UserInfo> getByNameAndPagination(String name, Pageable pageable) {
		Page<UserInfo> user;
		if (ObjectUtils.isEmpty(name)) {
			user = userRepository.findByDeleted(false, pageable);
		} else {
			user = userRepository.findByFirstNameContainingIgnoreCaseAndDeleted(name, false, pageable);
		}
		return user;
	}

	public List<UserInfo> allActiveUsers() {
		return userRepository.findByDeleted(false);
	}

	public Map<String, String> assignReportsTo(List<String> users, String reportsTo) {

		for (String userId : users) {
			UserInfo user = userRepository.findByUserId(userId)
					.orElseThrow(() -> new ResourceNotFoundException("No such user found to update"));
			user.setReportsTo(reportsTo);
			userRepository.save(user);
		}
		Map<String, String> message = new HashMap<String, String>();
		message.put("message", "User Assigned");
		return message;
	}

	public Map<String, String> deAassignReportsTo(List<String> users) {

		for (String userId : users) {
			UserInfo user = userRepository.findByUserId(userId)
					.orElseThrow(() -> new ResourceNotFoundException("No such user found to update"));
			user.setReportsTo("");
			userRepository.save(user);
		}
		Map<String, String> message = new HashMap<String, String>();
		message.put("message", "User De-Assigned");
		return message;
	}

	public List<String> getReportsToOfUser(String userId) {

		List<UserInfo> findByReportsTo = userRepository.findByReportsTo(userId);
		String nameList = "";
		List<String> name = new ArrayList<String>();
		for (UserInfo userDetails : findByReportsTo) {
			name.add(userDetails.getUserId());
		}
		name.add(userId);
		return name;
	}

	public List<String> getAllUserReportsTo(String userId) {

		return userRepository.getAllUsersById(userId);
	}

	public List<UserInfo> getAllEmail(List<String> email) {

		return userRepository.findByEmailIn(email);
	}

	public UserName getUserNameById(String id) {
		UserName byUserId = userRepository.findByUserIdAndEnabled(id, true);
		return byUserId;
	}

	public List<UserInfo> getUserByEmail(String email) {
		log.info("Email: {}", email);
		email = email.replaceAll("\\]", "");
		email = email.replaceAll("\\[", "");
		List<String> list = new ArrayList<String>(new ArrayList<>(Arrays.asList(email.split(","))));

		List<String> trimmedStrings = list.stream().map(String::trim).collect(Collectors.toList());

		List<UserInfo> user = getAllEmail(trimmedStrings);
		return user;
	}

	public EmailValidateDTO validateEmailAddress(String email) {
		Optional<EmailValidateDTO> findByEmailAndEnabledTrue = userRepository.findByEmailAndEnabledTrueAndDeletedFalse(email);
		if (findByEmailAndEnabledTrue.isPresent()) {
			return findByEmailAndEnabledTrue.get();
		} else {
			throw new PersistenceException("Invalid email.");
		}
		
	}
	public List<EmailTemplateVariable> getUserByEmail(List<String> recipients) {

		List<EmailTemplateVariable> email = new ArrayList<EmailTemplateVariable>();
		List<UserInfo> listOfUserAndReportee = getUserByEmail(recipients.toString());

		for (UserInfo user : listOfUserAndReportee) {
			EmailTemplateVariable emailTemplateVariable = new EmailTemplateVariable();
			emailTemplateVariable.setCustomerName(user.getFirstName());
			emailTemplateVariable.setCustomerEmail(user.getEmail());
			email.add(emailTemplateVariable);
		}

		return email;
	}

}
