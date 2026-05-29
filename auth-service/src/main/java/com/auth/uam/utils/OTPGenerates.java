package com.auth.uam.utils;

import java.util.Random;

import org.springframework.stereotype.Component;

@Component
public class OTPGenerates {

	 public String generateOTP() {
	        // Generate a random 6-digit OTP
	        Random random = new Random();
	        int otp = 100000 + random.nextInt(900000);
	        return String.valueOf(otp);
	    }
}
