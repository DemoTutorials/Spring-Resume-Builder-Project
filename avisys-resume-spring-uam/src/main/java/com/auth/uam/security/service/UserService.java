package com.auth.uam.security.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.persistence.PersistenceException;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import com.auth.uam.constant.Role;
import com.auth.uam.constant.UserType;
import com.auth.uam.dto.ChangePasswordDTO;
import com.auth.uam.dto.CommonMaster;
import com.auth.uam.dto.ForgotPasswordDTO;
import com.auth.uam.dto.MailDTO;
import com.auth.uam.dto.PersonDto;
import com.auth.uam.dto.PostDto;
import com.auth.uam.dto.RegistrationDto;
import com.auth.uam.dto.Status;
import com.auth.uam.dto.UserDTO;
import com.auth.uam.dto.UserDatatable;
import com.auth.uam.dto.UserDetailsDTO;
import com.auth.uam.entity.JobPost;
import com.auth.uam.entity.Roles;
import com.auth.uam.entity.User;
import com.auth.uam.entity.UserInvitation;
import com.auth.uam.exception.NotFoundGenericException;
import com.auth.uam.exception.ValidationException;
import com.auth.uam.proxy.CommonMasterProxy;
import com.auth.uam.proxy.ProxyService;
import com.auth.uam.proxy.ResumeService;
import com.auth.uam.repository.JobPostRepository;
import com.auth.uam.repository.RoleRepository;
import com.auth.uam.repository.UserRepository;
import com.auth.uam.service.MailService;
import com.auth.uam.service.TokenService;
import com.auth.uam.utils.UUIDGenerate;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	CommonMasterProxy utilityProxy;
	
	@Autowired
	ResumeService resumeService;
	
	@Autowired
	TokenService tokenService; 
	
	@Autowired
	ProxyService proxyService;
	
	@Autowired
	MailService mailService;
	
	@Autowired
	JobPostRepository jobPostRepo;
	
	@Autowired
    private JobPostRepository jobPostRepository;
	

	public Page<UserDatatable> getSearchAndPagination(String name, Pageable pageable, String userId) {
		Page<UserDatatable> userPage = null;

		UserDetailsDTO userDetailsByUserUniqueId = getUserDetailsByUserUniqueId(userId);
		String userType = userDetailsByUserUniqueId.getUserType();

		if (userType.equals(Role.ADMIN.toString())) {

			if (ObjectUtils.isEmpty(name)) {
				userPage = userRepository.fetchUserAndNotDeleted(pageable);
			} else {
				userPage = userRepository
						.findByParameters(name, pageable);
			}
		} else if (userType.equals(Role.HR.toString())) {
			if (ObjectUtils.isEmpty(name)) {
				userPage = userRepository.findByIsDeletedAndCompanyCode(userDetailsByUserUniqueId.getCompanyCode(), pageable);
			} else {
				userPage = userRepository.
						findByMultipleParameters(name, userDetailsByUserUniqueId.getCompanyCode(), pageable);
			}
		} else {
			throw new ValidationException("Access Denied: You do not have permission to access the datatable.");
		}
		return userPage;
	}

	public Optional<User> getByIdUser(Long id) {
		Optional<User> findByUserIdAndIsDeleted = userRepository.findByIdAndIsDeleted(id, false);
		return findByUserIdAndIsDeleted;
	}

	public User create(UserDTO userDto, String uid) {
		validateDuplicateUserName(userDto);
		validateDuplicateEmail(userDto);

		userDto.setCreationDate(LocalDateTime.now());
		userDto.setLastUpdateDate(LocalDateTime.now());
		userDto.setUserId(UUIDGenerate.generateUUID());
		userDto.setCreatedBy(uid);
		userDto.setLastUpdateBy(uid);
		userDto.setIsDeleted(false);

		User convertValue = objectMapper.convertValue(userDto, User.class);
		convertValue.setPassword(passwordEncoder.encode(userDto.getPassword()));

		UserDetailsDTO userDetailsByUserUniqueId = getUserDetailsByUserUniqueId(uid);
		String userType = userDetailsByUserUniqueId.getUserType();

		if (userType.equals(Role.ADMIN.toString()) && userDto.getUserType().equals(Role.HR.toString())) {

			if (userDto.getCompanyName()==null) {
				throw new PersistenceException("Please provide the company name to proceed.");
			}
			
			convertValue.setCompanyName(userDto.getCompanyName());
			convertValue.setCompanyCode(userDto.getCompanyName().substring(0, 3)+""+UUIDGenerate.generateUUID7());

		} else if (userType.equals(Role.HR.toString()) && userDto.getUserType().equals(Role.HR.toString())) {

			convertValue.setCompanyName(userDetailsByUserUniqueId.getCompanyName());
			convertValue.setCompanyCode(userDetailsByUserUniqueId.getCompanyCode());

		} else if (userType.equals(Role.HR.toString()) && userDto.getUserType().equals(Role.USER.toString())) {

			convertValue.setCompanyName(userDetailsByUserUniqueId.getCompanyName());
			convertValue.setCompanyCode(userDetailsByUserUniqueId.getCompanyCode());
		}else if (userType.equals(Role.ADMIN.toString()) && userDto.getUserType().equals(Role.USER.toString())) {
			
			convertValue.setCompanyName("Resume Creator");
			convertValue.setCompanyCode("CV-001");
		}

		if (userDto.getUserType().equals(Role.HR.toString())) {
			ArrayList<Roles> listOfRoles = new ArrayList<>();
			Roles roleObj = roleRepository.findById(3L).get();
			listOfRoles.add(roleObj);
			convertValue.setRole(listOfRoles);
		} else if (userDto.getUserType().equals(Role.ADMIN.toString())) {
			ArrayList<Roles> listOfRoles = new ArrayList<>();
			Roles roleObj = roleRepository.findById(1L).get();
			listOfRoles.add(roleObj);
			convertValue.setRole(listOfRoles);
		} else if (userDto.getUserType().equals(Role.USER.toString())) {
			ArrayList<Roles> listOfRoles = new ArrayList<>();
			Roles roleObj = roleRepository.findById(2L).get();
			listOfRoles.add(roleObj);
			convertValue.setRole(listOfRoles);
		}
		User saveUser = userRepository.save(convertValue);
		
		if (userDto.getUserType().equals(Role.USER.toString()) || userDto.getUserType().equals(Role.HR.toString())) {
			saveResume(saveUser);
		}
		saveMailForUserCreation(saveUser, userDto.getPassword());
		return saveUser;
	}

	public User updateRoles(UserDTO user, String uid) {

		UserDetailsDTO userDetailsByUserUniqueId = getUserDetailsByUserUniqueId(uid);
		String userType = userDetailsByUserUniqueId.getUserType();

		
		Optional<User> getById = userRepository.findByIdAndIsDeleted(user.getId(), false);
		if (getById.isPresent()) {
			User existingUser = getById.get();

			validateDuplicateUserName(user);
			validateDuplicateEmail(user);

			existingUser.setFirstName(user.getFirstName());
			existingUser.setMiddleName(user.getMiddleName());
			existingUser.setLastName(user.getLastName());
			existingUser.setUserName(user.getUserName());			
			existingUser.setEmail(user.getEmail());
			
			if (userType.equals(Role.ADMIN.toString()) && user.getUserType().equals(Role.HR.toString())) {

				if (user.getCompanyName()==null) {
					throw new PersistenceException("Please provide the company name to proceed.");
				}
				
				existingUser.setCompanyName(user.getCompanyName());
				 if (existingUser.getUserType().equals(Role.USER.toString()) && user.getUserType().equals(Role.HR.toString()))
				 {
				existingUser.setCompanyCode(user.getCompanyName().substring(0, 3)+""+UUIDGenerate.generateUUID7());
				 }

			} else if (userType.equals(Role.HR.toString()) && user.getUserType().equals(Role.HR.toString())) {

				existingUser.setCompanyName(userDetailsByUserUniqueId.getCompanyName());
				existingUser.setCompanyCode(userDetailsByUserUniqueId.getCompanyCode());

			} else if (userType.equals(Role.HR.toString()) && user.getUserType().equals(Role.USER.toString())) {

				existingUser.setCompanyName(userDetailsByUserUniqueId.getCompanyName());
				existingUser.setCompanyCode(userDetailsByUserUniqueId.getCompanyCode());
			}else if (userType.equals(Role.ADMIN.toString()) && user.getUserType().equals(Role.USER.toString())) {
				
				existingUser.setCompanyName("Resume Creator");
				existingUser.setCompanyCode("CV-001");
			}
			if (user.getUserType().equals(Role.HR.toString())) {
				ArrayList<Roles> listOfRoles = new ArrayList<>();
				Roles roleObj = roleRepository.findById(3L).get();
				listOfRoles.add(roleObj);
				existingUser.setRole(listOfRoles);

		
			}  else if (user.getUserType().equals(Role.USER.toString())) {
				ArrayList<Roles> listOfRoles = new ArrayList<>();
				Roles roleObj = roleRepository.findById(2L).get();
				listOfRoles.add(roleObj);
				existingUser.setRole(listOfRoles);

							
			}

			existingUser.setUserType(user.getUserType());
			existingUser.setMobileNumber(user.getMobileNumber());
			existingUser.setLastUpdateBy(uid);
			existingUser.setLastUpdateDate(LocalDateTime.now());
			return userRepository.save(existingUser);
		} else
			throw new PersistenceException("Record not found");
	}

	public String changePassword(ChangePasswordDTO changePasswordDTO, String userId) {
		Optional<User> findByUserName = userRepository.findByUserNameAndIsDeleted(changePasswordDTO.getUserName(),
				false);
		if (findByUserName.isEmpty()) {
			throw new PersistenceException("Invalid UserName");
		}
		User user = findByUserName.get();

		boolean matches = passwordEncoder.matches(changePasswordDTO.getOldPassword(), user.getPassword());
		if (matches) {
			LocalDateTime dateTime = LocalDateTime.now();
			user.setPassword(passwordEncoder.encode(changePasswordDTO.getNewPassword()));
			user.setLastUpdatePasswordDate(dateTime);
			user.setLastUpdateBy(userId);
			userRepository.save(user);
			return "Password has been changed";
		} else {

			throw new PersistenceException("Invalid Password");
		}

	}

	public User softDelete(Long id, String uid) {
		Optional<User> getById = userRepository.findByIdAndIsDeleted(id, false);

		User existingUser = getById.get();
		existingUser.setIsDeleted(true);
		existingUser.setLastUpdateBy(uid);
		existingUser.setLastUpdateDate(LocalDateTime.now());
		return userRepository.save(existingUser);
	}

	public Optional<User> getById(String userId) {
		// TODO Auto-generated method stub
		return null;
	}

	public void validateDuplicateUserName(UserDTO user) {
		Optional<User> getByUserName = userRepository.findByUserNameIgnoreCase(user.getUserName().toLowerCase());
		if (getByUserName.isPresent()) {
			if (user.getId() == null) {
				String errorMessage = StringUtils.EMPTY;
				if (getByUserName.get().getIsDeleted()) {
					errorMessage = "Username already present but is marked deleted";
				} else {
					errorMessage = "Username already present";
				}
				throw new ValidationException(errorMessage);
			} else {
				if (!user.getId().equals(getByUserName.get().getId())) {
					throw new ValidationException("Username already in use by another user");
				}
			}
		}
	}

	public void validateDuplicateEmail(UserDTO user) {
		Optional<User> getByEmail = userRepository.findByEmailIgnoreCase(user.getEmail().toLowerCase());
		if (getByEmail.isPresent()) {
			if (user.getId() == null) {
				String errorMessage = StringUtils.EMPTY;
				if (getByEmail.get().getIsDeleted()) {
					errorMessage = "Email already present but is marked deleted";
				} else {
					errorMessage = "Email already present";
				}
				throw new ValidationException(errorMessage);
			} else {
				if (!user.getId().equals(getByEmail.get().getId())) {
					throw new ValidationException("Email already in use by another user");
				}
			}
		}
	}

	public String forgotPassword(ForgotPasswordDTO forgotPasswordDTO, String userId) {
		Optional<User> getById = userRepository.findByUserNameAndIsDeleted(forgotPasswordDTO.getUserName(), false);
		if (getById.isPresent()) {
			User existingUser = getById.get();
			existingUser.setLastUpdateBy(userId);
			existingUser.setPassword(passwordEncoder.encode(forgotPasswordDTO.getNewPassword()));
			existingUser.setLastUpdateDate(LocalDateTime.now());
			userRepository.save(existingUser);
			return "Password Changed";
		} else
			throw new PersistenceException("User not found");

	}

	public Status registration(RegistrationDto register) {
		Optional<User> getByUserName = userRepository.findByUserNameIgnoreCase(register.getUserName().toLowerCase());
		Set<JobPost> jobPosts = new HashSet<>();
		if (getByUserName.isEmpty()) {
			ArrayList<Roles> listOfRoles = new ArrayList<>();
			Roles roleObj = roleRepository.findById(2L).get();
			listOfRoles.add(roleObj);
			User convertValue = objectMapper.convertValue(register, User.class);
			convertValue.setCreationDate(LocalDateTime.now());
			convertValue.setLastUpdateDate(LocalDateTime.now());
			convertValue.setCreatedBy("user");
			convertValue.setLastUpdateBy("user");
			convertValue.setIsDeleted(false);
			convertValue.setUserType(UserType.USER.toString());
			convertValue.setRole(listOfRoles);
			convertValue.setUserId(UUIDGenerate.generateUUID());
			convertValue.setCompanyName("Resume Creator");
			convertValue.setCompanyCode("CV-001");
			
			if(!register.getJobPostCode().isEmpty()) {
				convertValue.setJobApplicant(true);
				 Optional<JobPost> jobCodeOptional = jobPostRepo.findByJobPostCodeAndIsDeletedFalse(register.getJobPostCode());
					if (jobCodeOptional.isPresent()) {
						if (jobCodeOptional.get().isActive()) {
							jobPosts.add(jobCodeOptional.get());
						} else
							throw new NotFoundGenericException("Job post is currently inactive");	
					} else {
						System.out.println(" register auth failed for new user without jobPostCode");
						throw new NotFoundGenericException("Job post code does not exist");
					}
			}
			if (register.getPassword() != null) {
				convertValue.setPassword(passwordEncoder.encode(register.getPassword()));
			}
			User saveUser = userRepository.save(convertValue);

			saveMailForUserCreation(saveUser, register.getPassword());
			if (jobPosts.isEmpty()) {
				saveResume(saveUser);
			} else {
				saveResumeForApplicant(saveUser, jobPosts);
			}
			return new Status("Registration Successful");
		} else {
			throw new NotFoundGenericException("Email address already used");
		}
	}

	public String getUserDetailsByUserId(String userId) {
		Optional<User> findByUserIdAndIsDeleted = userRepository.findByUserIdAndIsDeleted(userId, false);
		if (findByUserIdAndIsDeleted.isPresent()) {
			User user = findByUserIdAndIsDeleted.get();
			return user.getFirstName() + " " + user.getLastName();
		} else
			throw new PersistenceException("Record not fond");
	}

	public UserDetailsDTO getUserDetailsByUserUniqueId(String userId) {
		Optional<User> findByUserIdAndIsDeleted = userRepository.findByUserIdAndIsDeleted(userId, false);
		if (findByUserIdAndIsDeleted.isPresent()) {
			User user = findByUserIdAndIsDeleted.get();
			UserDetailsDTO convertValue = objectMapper.convertValue(user, UserDetailsDTO.class);
			return convertValue;
		} else
			throw new PersistenceException("Record not fond");
	}

	public List<CommonMaster> getUserTypesDropdownByUserId(String userId) {

		List<CommonMaster> commonMasterData = utilityProxy.getCommonMasterData("User Type");
		
		UserDetailsDTO userDetailsByUserUniqueId = getUserDetailsByUserUniqueId(userId);
		String userType = userDetailsByUserUniqueId.getUserType();
		
		if (userType.equals(Role.ADMIN.toString()) || userType.equals(Role.HR.toString())) {
			
			Iterator<CommonMaster> iterator = commonMasterData.iterator();
	        while (iterator.hasNext()) {
	        	CommonMaster obj = iterator.next();
	            if ("ADMIN".equals(obj.getCode())) {
	                iterator.remove();
	            }
	        }
	        
			return commonMasterData;
		} else {
			throw new ValidationException("Access Denied: You do not have permission to access the User Types.");
		}
	}
	
	public Page<PostDto> searchJobPost(Pageable pageable, String keyword, Long userId) {
	    String formattedKeyword = "%" + keyword.trim().toLowerCase() + "%";
	    return jobPostRepository.searchJobPostByUser(formattedKeyword, userId, pageable);
	}

//	@Transactional
	public User saveInvitedUser(UserInvitation userInvitation, String password) {
		
		Optional<User> getByEmail = userRepository.findByEmailIgnoreCase(userInvitation.getEmail().toLowerCase());
		if (getByEmail.isPresent()) {
			
			throw new PersistenceException("Email already used.");
		}
		
		ArrayList<Roles> listOfRoles = new ArrayList<>();
		Roles roleObj = roleRepository.findById(2L).get();
		listOfRoles.add(roleObj);
		
		User user = new User();
		user.setRole(listOfRoles);
		user.setUserId(UUIDGenerate.generateUUID());
		user.setPassword(passwordEncoder.encode(password));
		user.setUserName(userInvitation.getEmail());
		user.setUserType(Role.USER.toString());
		user.setFirstName(userInvitation.getFirstName());
		user.setLastName(userInvitation.getLastName());
		user.setEmail(userInvitation.getEmail());
		user.setCreatedBy(null);
		user.setCreationDate(LocalDateTime.now());
		user.setLastUpdateBy(null);
		user.setLastUpdateDate(LocalDateTime.now()); 
		user.setCompanyCode(userInvitation.getCompanyCode());
		user.setCompanyName(userInvitation.getCompanyName());
		user.setIsDeleted(false);
	    userRepository.save(user);
		
		saveResume(user);
		return user;
	}
	
	public void saveResume(User user) {
		
		PersonDto person = new PersonDto();
		person.setFirstName(user.getFirstName());
		person.setLastName(user.getLastName());
		person.setUser(user);
		person.setCompanyCode(user.getCompanyCode());
		person.setEmail(user.getEmail());
		resumeService.save(person);
	}
	
public void saveResumeForApplicant(User user, Set<JobPost> jobPost) {	
		PersonDto person = new PersonDto();
		person.setFirstName(user.getFirstName());
		person.setLastName(user.getLastName());
		person.setUser(user);
		person.setCompanyCode(user.getCompanyCode());
		person.setEmail(user.getEmail());
		person.setJobPosts(jobPost);
		resumeService.save(person);
	}
	
	
	public void saveMailForUserCreation(User userDto, String password) {
		List<MultipartFile> file = null;
		MailDTO mailDTO = new MailDTO();
		mailDTO.setSendTO(userDto.getEmail());
		mailDTO.setEmailBody("https://www.buildresume.co.in/app/auth/login");
		mailDTO.setReferenceId(userDto.getId() + "");
		String senderName = "Build Resume Team";
		mailService.sendMailToUser(mailDTO, file, userDto.getFirstName() + " " + userDto.getLastName(), senderName, password);
	}
	
	public User createUserByHr(UserDTO userDto, String uid) {
		validateDuplicateUserName(userDto);
		validateDuplicateEmail(userDto);
 
		userDto.setCreationDate(LocalDateTime.now());
		userDto.setLastUpdateDate(LocalDateTime.now());
		userDto.setUserId(UUIDGenerate.generateUUID());
		userDto.setCreatedBy(uid);
		userDto.setLastUpdateBy(uid);
		userDto.setIsDeleted(false);
 
		User convertValue = objectMapper.convertValue(userDto, User.class);
		convertValue.setPassword(passwordEncoder.encode(userDto.getPassword()));
 
			ArrayList<Roles> listOfRoles = new ArrayList<>();
			Roles roleObj = roleRepository.findById(2L).get();
			listOfRoles.add(roleObj);
			convertValue.setRole(listOfRoles);
		
		User saveUser = userRepository.save(convertValue);
		
		return saveUser;
	}

}
