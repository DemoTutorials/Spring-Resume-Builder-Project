package com.auth.uam.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.auth.uam.dto.AcceptInvitationDTO;
import com.auth.uam.dto.AuthRequest;
import com.auth.uam.dto.AuthToken;
import com.auth.uam.dto.ForgotPasswordDTO;
import com.auth.uam.dto.LinkdinTokenDto;
import com.auth.uam.dto.RegistrationDto;
import com.auth.uam.dto.Status;
import com.auth.uam.dto.UserInfo;
import com.auth.uam.dto.VerificationDTO;
import com.auth.uam.dto.VerificationResponse;
import com.auth.uam.entity.ContactUs;
import com.auth.uam.entity.JobPost;
import com.auth.uam.entity.Person;

import com.auth.uam.entity.User;
import com.auth.uam.proxy.RegistrationService;
import com.auth.uam.repository.JobPostRepository;

import com.auth.uam.repository.PersonRepo;

import com.auth.uam.repository.UserRepository;
import com.auth.uam.security.service.JwtService;
import com.auth.uam.security.service.LinkedInAuthService;
import com.auth.uam.security.service.RegisterService;
import com.auth.uam.service.ForgotPasswordService;
import com.auth.uam.service.LogInService;
import com.auth.uam.service.OTPService;
import com.auth.uam.service.UserInvitationService;

@RestController
@RequestMapping("auth")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AuthController {

	/* validate user for token */
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	LogInService logInService;

	@Autowired
	JwtService jwtService;
	
	@Autowired
	UserRepository userRepo;

	@Autowired
	RegisterService registerService;

	@Autowired
	OTPService otpService;

	@Autowired
	ForgotPasswordService forgotPasswordService;

	@Autowired
	RegistrationService registration;

	@Autowired
	LogInController logInController;

	@Autowired
	UserInvitationService userInvitationService;
	
	@Autowired
    LinkedInAuthService linkedInAuthService;
	
	@Autowired
	JobPostRepository jobPostRepo;
	
	@Autowired
	PersonRepo personRepository;


	@PostMapping("accessToken")
	public ResponseEntity<?> authonticatAndGetToken(@RequestBody AuthRequest authRequest) {

		try {
				
			User existingUser = userRepo.findByUserNameIgnoreCaseAndIsDeleted(authRequest.getUserName(),
					false).orElseThrow(() -> new PersistenceException("Bad credentials"));
			
			
			
			Authentication authenticate = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(existingUser.getUserName(), authRequest.getPassword()));

			if (authenticate.isAuthenticated()) {
				SecurityContextHolder.getContext().setAuthentication(authenticate);
				AuthToken authToken = logInService.getToken(authRequest);
				Optional<User> userDetailsOptional = userRepo.findByUserNameIgnoreCaseAndIsDeleted(authRequest.getUserName(),
						false);
				if (!authRequest.getJobPostCode().isEmpty() && userDetailsOptional.get().isJobApplicant()) {
					System.out.println(authRequest.getJobPostCode().isEmpty()+" login auth failed for new user without jobPostCode");
					JobPost jobPost = jobPostRepo
							.findByJobPostCodeAndIsDeletedFalse(authRequest.getJobPostCode()).orElseThrow(() -> new PersistenceException("Job post code does not exist"));
					
						
					if (userDetailsOptional.isPresent()) {
						User user = userDetailsOptional.get();
						boolean isJobPostApplied = false;
						List<Person> listOfperson = personRepository.findByUser(user);
						for (Person person : listOfperson) {
						    Set<JobPost> personjobPosts = person.getJobPosts();
						    if (personjobPosts.contains(jobPost)) {
						    	isJobPostApplied = true;
						    }
						}

						if (!isJobPostApplied) {
							if (jobPost.isActive()) {
								registerService.saveResumeForApplicant(user, jobPost);  // call resume feign client with userId
							} else
								throw new PersistenceException("Job post is currently inactive");
						}
					}
				}
				Optional<User> userDetail = userRepo.findByUserNameIgnoreCaseAndIsDeleted(authRequest.getUserName(),
						false);
				if (userDetail.isPresent() && userDetail.get().isJobApplicant()) {

					List<Person> listOfperson = personRepository.findByUser(userDetail.get());
					if (listOfperson.size() == 1) {
						authToken.setNewApplicant(true);
					}
				}
				return new ResponseEntity<>(authToken, HttpStatus.OK);
			} else {

				throw new UsernameNotFoundException("Invalid User Request!");
			}
		} catch (Exception e) {
			Status status = new Status();
			status.setMessage(e.getMessage());
			return new ResponseEntity<>(status, HttpStatus.UNAUTHORIZED);
		}
	}
	
	@PostMapping("refreshToken")
	public ResponseEntity<?> refreshToken(String refreshToken) {

		/* validate refresh token */
		try {

			AuthToken authToken = logInService.refreshToken(refreshToken);
			return new ResponseEntity<>(authToken, HttpStatus.OK);

		} catch (Exception e) {
			Status status = new Status();
			status.setMessage(e.getMessage());
			return new ResponseEntity<>(status, HttpStatus.UNAUTHORIZED);
		}
	}

	@PostMapping("forgot-password")
	public ResponseEntity<?> forgotPassword(@RequestBody ForgotPasswordDTO forgotPassword) {
		try {
			forgotPasswordService.forgotUserPasswordBySendingOTP(forgotPassword);
			return new ResponseEntity<>(new Status("OTP Sent Successfully"), HttpStatus.OK);
		} catch (Exception e) {
			Status status = new Status();
			status.setMessage(e.getMessage());
			return new ResponseEntity<>(status, HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("verify-otp")
	public ResponseEntity<?> verifyOTPWithUsername(@RequestBody VerificationDTO verify) {
		try {
			String verifyOTPWithUsername = forgotPasswordService.verifyOTPWithUsername(verify);
			VerificationResponse verified = new VerificationResponse("OTP is verified", verifyOTPWithUsername);
			return new ResponseEntity<>(verified, HttpStatus.OK);
		} catch (Exception e) {
			Status status = new Status();
			status.setMessage(e.getMessage());
			return new ResponseEntity<>(status, HttpStatus.BAD_REQUEST);
		}
	}

    @GetMapping("/linkedin")
    public ResponseEntity<?> authenticateWithLinkedIn(
        @RequestParam("code") String code, 
        @RequestParam(value = "jobPostCode", required = false) String jobPostCode
    ) {
    	LinkdinTokenDto response = linkedInAuthService.fetchAccessToken(code);
    	
    	String accessToken = response.getAccess_token();
    	
    	UserInfo user = linkedInAuthService.getUserInfo(accessToken);
    	
    	RegistrationDto register = new RegistrationDto();
    	register.setFirstName(user.getGiven_name());
    	register.setLastName(user.getFamily_name());
    	register.setEmail(user.getEmail());
    	register.setJobPostCode(jobPostCode);
    
        return socialRegistration(register);
    }

	@PostMapping("register")
	public ResponseEntity<?> userRegistration(@RequestBody RegistrationDto register) {
		try {
			Status response = registerService.Registration(register);
			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();
			Status status = new Status();
			status.setMessage(e.getMessage());
			return new ResponseEntity<>(status, HttpStatus.BAD_REQUEST);
		}

	}

	@PostMapping("user-invitation")
	public ResponseEntity<Status> acceptUserInvitation(@RequestBody AcceptInvitationDTO acceptInvitationDTO) {
		try {

			Status acceptInvitation = userInvitationService.acceptUserInvitation(acceptInvitationDTO);
			return new ResponseEntity<>(acceptInvitation, HttpStatus.OK);
		} catch (Exception e) {
			throw new PersistenceException(e.getMessage());
		}
	}
	
	@PostMapping("/send-contact")
	public ResponseEntity<Status> sendContacts(@RequestBody ContactUs contactUs) {
		try {
			Status personalDetails = userInvitationService.createContactUs(contactUs);
			return new ResponseEntity<>(personalDetails, HttpStatus.OK);
		} catch (Exception e) {
			throw new PersistenceException(e.getMessage());
		}

	}
	
	@PostMapping("registerAndLogin")
	public ResponseEntity<?> socialRegistration(@RequestBody RegistrationDto register) {
		try {
			Optional<User> findByUserName = userRepo.findByUserNameAndIsDeleted(register.getEmail(), false);
			
			if (findByUserName.isEmpty()) {
				register.setPassword("Default");
				
				registerService.Registration(register);
			}
	
			//make this code reusable in services
			if (!register.getJobPostCode().isEmpty() && !findByUserName.isEmpty()) {
			
				JobPost jobPost = jobPostRepo
						.findByJobPostCodeAndIsDeletedFalse(register.getJobPostCode()).orElseThrow(() -> new PersistenceException("Job post code does not exist"));
					User user = findByUserName.get();
					boolean isJobPostApplied = false;
					List<Person> listOfperson = personRepository.findByUser(user);
					for (Person person : listOfperson) {
						Set<JobPost> personjobPosts = person.getJobPosts();
					    
					    if (personjobPosts.contains(jobPost)) {
					    	isJobPostApplied = true;
					    	break;
					    }
					}
					
					if (!isJobPostApplied) {
						if (jobPost.isActive()) {
							registerService.saveResumeForApplicant(user, jobPost);  // call resume feign client with userId
						} else
							throw new PersistenceException("Job post is currently inactive");
					}
				}
			
			AuthRequest authRequest = new AuthRequest();
			authRequest.setUserName(register.getEmail());
			AuthToken authToken = new AuthToken();
			authToken = logInService.getToken(authRequest);
			Optional<User> userDetailsOptional = userRepo.findByUserNameAndIsDeleted(authRequest.getUserName(),
					false);
			if (userDetailsOptional.isPresent() && userDetailsOptional.get().isJobApplicant()) {

				List<Person> listOfperson = personRepository.findByUser(userDetailsOptional.get());
				if (listOfperson.size() == 1) {
					authToken.setNewApplicant(true);
				}
			}			
			return new ResponseEntity<>(authToken, HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();
			Status status = new Status();
			status.setMessage(e.getMessage());
			return new ResponseEntity<>(status, HttpStatus.UNAUTHORIZED);
		}
	}

}
