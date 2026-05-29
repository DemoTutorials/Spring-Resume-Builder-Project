package com.auth.uam.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.auth.uam.dto.ForgotPasswordDTO;
import com.auth.uam.dto.MailDTO;
import com.auth.uam.dto.VerificationDTO;
import com.auth.uam.entity.OneTimePassword;
import com.auth.uam.entity.User;
import com.auth.uam.exception.ResourceNotFoundExceptionGeneric;
import com.auth.uam.repository.OTPRepository;
import com.auth.uam.repository.UserRepository;
import com.auth.uam.security.service.JwtService;
import com.auth.uam.utils.OTPGenerates;

@Service
public class ForgotPasswordService {

	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	OTPRepository otpRepository;
	
	@Autowired
	OTPGenerates otpGenerates;
	
	@Autowired
	MailService mailService;
	
	@Autowired
	JwtService jwtService;
	
	public void forgotUserPasswordBySendingOTP(ForgotPasswordDTO dto) {
		
		Optional<User> findByUserNameAndIsDeleted = userRepository.findByUserNameAndIsDeleted(dto.getUserName(), false);
		if (findByUserNameAndIsDeleted.isEmpty()) {
			throw new ResourceNotFoundExceptionGeneric("Invalid User");
		}
		User user = findByUserNameAndIsDeleted.get();
		OneTimePassword newOneTimePassword = new OneTimePassword();
		newOneTimePassword.setOneTimePassword(otpGenerates.generateOTP());
		newOneTimePassword.setUserId(user.getUserId());
		newOneTimePassword.setCreationDate(LocalDateTime.now());
		newOneTimePassword.setExpiryDate(LocalDateTime.now().plusMinutes(10));
		OneTimePassword saveOneTimePassword = otpRepository.save(newOneTimePassword);
		
		List<MultipartFile> file = null;
		
		MailDTO mailDTO = new MailDTO();
		mailDTO.setSendTO(user.getEmail());
		mailDTO.setEmailBody(saveOneTimePassword.getOneTimePassword()+" is your One Time Password (OTP) for forgot password.");
		mailDTO.setReferenceId(user.getId()+"");
		mailService.sendMail(mailDTO, file);
		
	}
	
	

	public String verifyOTPWithUsername(VerificationDTO verify) {
		Optional<User> findByUserNameAndIsDeleted = userRepository.findByUserNameAndIsDeleted(verify.getUserName(), false);
		if (findByUserNameAndIsDeleted.isEmpty()) {
			throw new ResourceNotFoundExceptionGeneric("Invalid User Name");
		}
		User user = findByUserNameAndIsDeleted.get();
		Optional<OneTimePassword> findByUserIdAndOneTimePassword = otpRepository.findByUserIdAndOneTimePassword(user.getUserId(), verify.getOtp());
		
		if (findByUserIdAndOneTimePassword.isEmpty()) {
			throw new ResourceNotFoundExceptionGeneric("Invalid OTP");
		}
		
		OneTimePassword oneTimePassword = findByUserIdAndOneTimePassword.get();
		if (oneTimePassword.getExpiryDate().isBefore(LocalDateTime.now())) {
			throw new ResourceNotFoundExceptionGeneric("OTP is Expired");
		}
		String genrateToken = jwtService.genrateToken(verify.getUserName());
		return genrateToken;
	}

	

	

	

}
