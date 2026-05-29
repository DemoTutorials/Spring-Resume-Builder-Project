package com.auth.uam.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth.uam.entity.PasswordResetToken;
import com.auth.uam.entity.User;
import com.auth.uam.repository.PasswordResetTokenRepository;
import com.auth.uam.repository.UserRepository;
import com.auth.uam.security.service.UserService;
import com.auth.uam.utils.SendMails;
import com.auth.uam.utils.UUIDGenerate;

@Service
public class LogInService {
	
	Logger logger = Logger.getLogger(LogInService.class.getName());
	
//	resetPasswordTokenMiniDiff
	
	@Value("${resetPasswordTokenMiniDiff}")
	private long resetPasswordTokenMiniDiff;
	
	@Value("${resetPasswordTokenUrl}")
	private String resetPasswordTokenUrl;

//	@Autowired
//	JwtService jwtService;

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	UserService userService;
	
	@Autowired
	PasswordResetTokenRepository passwordResetTokenRepository;
	
	@Autowired
	SendMails sendMails;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

//	public AuthToken getToken(AuthRequest authRequest) {
//		Optional<User> findByUserName = userRepository.findByUserNameAndIsDeleted(authRequest.getUserName(), false);
//		User user = findByUserName.get();
//
//		AuthToken token = new AuthToken();
//		token.setUserId(user.getUserId());
//		token.setUserName(user.getUserName());
//		token.setName(user.getFirstName() + " " + user.getLastName());
//		token.setEmail(user.getEmail());
//		String genrateToken = jwtService.genrateToken(authRequest.getUserName());
//		token.setAccessToken(genrateToken);
//
//		return token;
//	}

	public String getTokenForResetPassword(String userName, String email) {
		Optional<User> findByUserName =  userRepository.findByUserNameAndEmailAndIsDeleted(userName, email, false);

		if (findByUserName.isEmpty()) {
			return "Invalid User Name and Email";
		}
		
		User user = findByUserName.get();
		
		
		String unicId = UUIDGenerate.generateToken();
		LocalDateTime dateTime = LocalDateTime.now();
		LocalDateTime expiryTime = dateTime.plusMinutes(resetPasswordTokenMiniDiff);
		
	
		PasswordResetToken passwordResetToken = new PasswordResetToken();
		
		passwordResetToken.setUserId(user.getUserId());
		passwordResetToken.setPasswordToken(unicId);
		passwordResetToken.setCreationDateTime(dateTime);
		passwordResetToken.setExpiryDateTime(expiryTime);
		
		passwordResetTokenRepository.save(passwordResetToken);
		
		sendMails.sendMail(resetPasswordTokenUrl+unicId);
		
		return "Mail send";
	}

	public String resetPassword(String newPassword, String key) {
		
		Optional<PasswordResetToken> findPassToken = passwordResetTokenRepository.findByPasswordToken(key);
		
		if (findPassToken.isEmpty()) {
			return "Invalid request.";
		}
		PasswordResetToken passwordResetToken2 = findPassToken.get();
		
		LocalDateTime currentDateTime = LocalDateTime.now();
		logger.info("Current Date Time :" + currentDateTime);
		
		LocalDateTime resolutionTime = passwordResetToken2.getExpiryDateTime();
		logger.info("Resolution Date Time :" + currentDateTime);

		long seconds = currentDateTime.until(resolutionTime, ChronoUnit.SECONDS);

		logger.info("Remaining Time(in seconds) :" + seconds);
		if (seconds < 0) {
			return "Link has expired.";
		}
		
		System.out.println("User id :"+passwordResetToken2.getUserId());
		String userId = passwordResetToken2.getUserId();
		Optional<User> findByUserIdAndIsDeleted = userService.getById(userId);

		if (findByUserIdAndIsDeleted.isEmpty()) {
			return "User Not Found.";
		}
		User user = findByUserIdAndIsDeleted.get();
		System.out.println(user);
		LocalDateTime dateTime = LocalDateTime.now();
		System.out.println(newPassword);
		System.out.println(user.getUserName());
		user.setPassword(passwordEncoder.encode(newPassword));
		user.setLastUpdatePasswordDate(dateTime);
		userRepository.save(user);
		
		return "Password has changed.";
	}

	
}
