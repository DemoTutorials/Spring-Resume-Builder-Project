package com.auth.uam.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth.uam.dto.AuthRequest;
import com.auth.uam.dto.AuthToken;
import com.auth.uam.entity.PasswordResetToken;
import com.auth.uam.entity.Roles;
import com.auth.uam.entity.User;
import com.auth.uam.exception.ResourceNotFoundExceptionGeneric;
import com.auth.uam.repository.PasswordResetTokenRepository;
import com.auth.uam.repository.UserRepository;
import com.auth.uam.security.service.JwtService;
import com.auth.uam.security.service.RefreshTokenService;
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

	@Autowired
	JwtService jwtService;

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

	@Autowired
	RefreshTokenService refreshTokenService;

	public AuthToken getToken(AuthRequest authRequest) {
		Optional<User> findByUserName = userRepository.findByUserNameIgnoreCaseAndIsDeleted(authRequest.getUserName(), false);
		if (findByUserName.isEmpty()) {
			throw new ResourceNotFoundExceptionGeneric("Invalid user name.");
		} 
		User user = findByUserName.get();
 
		AuthToken token = new AuthToken();
		token.setId(user.getId());
		token.setUserId(user.getUserId());
		token.setUserName(user.getUserName());
		token.setName(user.getFirstName() + " " + user.getLastName());
		token.setEmail(user.getEmail());
		token.setCompanyName(user.getCompanyName());
		token.setCompanyCode(user.getCompanyCode());
		token.setJobApplicant(user.isJobApplicant());
		try {
			token.setRole(setRolesForAccess(user));
		} catch (Exception e) {
		}
		String genrateToken = jwtService.genrateToken(authRequest.getUserName());
		token.setAccessToken(genrateToken);
		String refreshToken = refreshTokenService.genrateRefreshToken(authRequest.getUserName());
		token.setRefreshToken(refreshToken);
 
		return token;
	}
	
	private String setRolesForAccess(User user) {
		List<Roles> role = user.getRole();

		return role.get(0).getRoleName();
	}

	private List<String>  setRolesForLogIn(User user) {
		List<Roles> role = user.getRole();
		List<String> accessRoles = new ArrayList<>();
		for (Roles roles : role) {
			accessRoles.add(roles.getRoleName());
		}
		
		return accessRoles;
	}

	public AuthToken refreshToken(String refreshToken) {

		try {
			refreshTokenService.tokenValidate(refreshToken);
		} catch (Exception e) {
			throw new ResourceNotFoundExceptionGeneric("Invalid refresh token.");
		}
		
		String userName = refreshTokenService.getUserName(refreshToken);
		
		Optional<User> findByUserName = userRepository.findByUserNameAndIsDeleted(userName, false);
		if (findByUserName.isEmpty()) {
			throw new ResourceNotFoundExceptionGeneric("Invalid user name.");
		} 
		User user = findByUserName.get();

		AuthToken token = new AuthToken();
		token.setId(user.getId());
		token.setUserId(user.getUserId());
		token.setUserName(user.getUserName());
		token.setName(user.getFirstName() + " " + user.getLastName());
		token.setEmail(user.getEmail());
		token.setCompanyCode(user.getCompanyCode());
		token.setCompanyName(user.getCompanyName());
		try {
			token.setRole(setRolesForAccess(user));
		} catch (Exception e) {
		}
		String genrateToken = jwtService.genrateToken(userName);
		token.setAccessToken(genrateToken);
		String setRefreshToken = refreshTokenService.genrateRefreshToken(userName);
		token.setRefreshToken(setRefreshToken);

		return token;


	}

	public String getTokenForResetPassword(String userName, String email) {
		Optional<User> findByUserName = userRepository.findByUserNameAndEmailAndIsDeleted(userName, email, false);

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

		sendMails.sendMail(resetPasswordTokenUrl + unicId);

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

		System.out.println("User id :" + passwordResetToken2.getUserId());
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
