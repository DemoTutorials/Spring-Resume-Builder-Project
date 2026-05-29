package com.auth.uam.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javax.persistence.PersistenceException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import com.auth.uam.constant.Role;
import com.auth.uam.constant.UserInvitationStatus;
import com.auth.uam.dto.AcceptInvitationDTO;
import com.auth.uam.dto.MailDTO;
import com.auth.uam.dto.Status;
import com.auth.uam.dto.UserDetailsDTO;
import com.auth.uam.dto.UserInvitationDTO;
import com.auth.uam.dto.UserInvitationDatatableDTO;
import com.auth.uam.entity.User;
import com.auth.uam.entity.UserInvitation;
import com.auth.uam.exception.ValidationException;
import com.auth.uam.repository.UserInvitationRepository;
import com.auth.uam.repository.UserRepository;
import com.auth.uam.security.service.UserService;
import com.auth.uam.utils.UUIDGenerate;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class UserInvitationService {

	@Autowired
	UserInvitationRepository userInvitationRepository;

	@Autowired
	UserService userService;

	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	MailService mailService;

	
	public List<UserInvitationDTO> saveUserInvitation(@Valid List<UserInvitationDTO> userInvitationDTO, String userId) {

		UserDetailsDTO userDetailsByUserUniqueId = userService.getUserDetailsByUserUniqueId(userId);
		List<UserInvitation> userInvitationList = new ArrayList<>();

		for (Iterator iterator = userInvitationDTO.iterator(); iterator.hasNext();) {
			UserInvitationDTO userInvitationDTO2 = (UserInvitationDTO) iterator.next();

			UserInvitation userInvitation = new UserInvitation();
			userInvitation.setFirstName(userInvitationDTO2.getFirstName());
			userInvitation.setLastName(userInvitationDTO2.getLastName());
			userInvitation.setEmail(userInvitationDTO2.getEmail());
			userInvitation.setCompanyName(userDetailsByUserUniqueId.getCompanyName());
			userInvitation.setCompanyCode(userDetailsByUserUniqueId.getCompanyCode());
			userInvitation.setInvitationStatus(UserInvitationStatus.Pending.toString());
			userInvitation.setInvitationCode(UUIDGenerate.generateToken());
			userInvitation.setIsDeleted(false);
			userInvitation.setCreatedBy(userId);
			userInvitation.setCreationDate(LocalDateTime.now());
			userInvitation.setUpdatedDate(LocalDateTime.now());
			
			userInvitationList.add(userInvitation);
		}
		List<UserInvitation> save = userInvitationRepository.saveAll(userInvitationList);
		List<UserInvitationDTO> convertValueList = new ArrayList<>();

		for (Iterator iterator = save.iterator(); iterator.hasNext();) {
			UserInvitation userInvitation = (UserInvitation) iterator.next();
			UserInvitationDTO convertValue = objectMapper.convertValue(userInvitation, UserInvitationDTO.class);
			convertValueList.add(convertValue);
		}

		saveMailForUserInvitation(save,
				userDetailsByUserUniqueId.getFirstName() + " " + userDetailsByUserUniqueId.getLastName());
		return convertValueList;
	}

	public void saveMailForUserInvitation(List<UserInvitation> userInvitationList, String senderName) {
		for (Iterator iterator = userInvitationList.iterator(); iterator.hasNext();) {
			UserInvitation userInvitation = (UserInvitation) iterator.next();
			List<MultipartFile> file = null;
			MailDTO mailDTO = new MailDTO();
			mailDTO.setSendTO(userInvitation.getEmail());
			mailDTO.setEmailBody("https://www.buildresume.co.in/app/auth/change-password/" + userInvitation.getInvitationCode());
			mailDTO.setReferenceId(userInvitation.getId() + "");
			mailService.sendMail(mailDTO, file, userInvitation.getFirstName() + " " + userInvitation.getLastName(),
					senderName);
		}

	}

	public Page<UserInvitationDatatableDTO> getSearchAndPagination(String name, Pageable pageable, String userId) {
		Page<UserInvitationDatatableDTO> userInvitationPage = null;

		UserDetailsDTO userDetailsByUserUniqueId = userService.getUserDetailsByUserUniqueId(userId);
		String userType = userDetailsByUserUniqueId.getUserType();

		if (userType.equals(Role.ADMIN.toString())) {

			if (ObjectUtils.isEmpty(name)) {
				userInvitationPage = userInvitationRepository.findByIsDeletedFalse(pageable);
			} else {
				userInvitationPage = userInvitationRepository
						.findByIsDeletedAndFirstNameLikeIgnoreCaseOrIsDeletedAndLastNameLikeIgnoreCaseOrIsDeletedAndEmailLikeIgnoreCase(
								false, "%" + name + "%", false, "%" + name + "%", false, "%" + name + "%", pageable);
			}

		} else if (userType.equals(Role.HR.toString())) {

			if (ObjectUtils.isEmpty(name)) {
				userInvitationPage = userInvitationRepository
						.findByCompanyCodeAndIsDeletedFalse(userDetailsByUserUniqueId.getCompanyCode(), pageable);
			} else {
				userInvitationPage = userInvitationRepository
						.findByCompanyCodeAndIsDeletedAndFirstNameLikeIgnoreCaseOrCompanyCodeAndIsDeletedAndLastNameLikeIgnoreCaseOrCompanyCodeAndIsDeletedAndEmailLikeIgnoreCase(
								userDetailsByUserUniqueId.getCompanyCode(), false, "%" + name + "%",
								userDetailsByUserUniqueId.getCompanyCode(), false, "%" + name + "%",
								userDetailsByUserUniqueId.getCompanyCode(), false, "%" + name + "%", pageable);
			}

		} else {
			throw new ValidationException("Access Denied: You do not have permission to access the datatable.");
		}
		return userInvitationPage;
	}

	public Status acceptUserInvitation(AcceptInvitationDTO acceptInvitationDTO) {

		Optional<UserInvitation> userInvitationResult = userInvitationRepository
				.findByInvitationCodeAndIsDeletedFalse(acceptInvitationDTO.getToken());

		if (userInvitationResult.isEmpty()) {
			return new Status("Invalid data.", "400");
		} else {

			UserInvitation userInvitation = userInvitationResult.get();
			LocalDateTime updatedDate = userInvitation.getUpdatedDate().plusHours(24);

			int comparisonResult = LocalDateTime.now().compareTo(updatedDate);

			if (comparisonResult > 0) {
				userInvitation.setInvitationCode(UUIDGenerate.generateToken());
				userInvitation.setUpdatedDate(LocalDateTime.now());
				userInvitationRepository.save(userInvitation);
				List<UserInvitation> userInvitationList = new ArrayList<>();
				userInvitationList.add(userInvitation);		
				saveMailForUserInvitation(userInvitationList, userInvitation.getFirstName() + " " + userInvitation.getLastName());
				return new Status("Invitation link was Expired, Please check new Invitation Email.", "400");
			}

			try {
				User saveInvitedUser = userService.saveInvitedUser(userInvitation, acceptInvitationDTO.getPassword());
			} catch (Exception e) {
				return new Status(e.getMessage(), "400");
			}

			return new Status("Invitation Accepted.", "200");
		}
	}

}
