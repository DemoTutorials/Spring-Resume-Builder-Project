package com.auth.uam.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.auth.uam.entity.OneTimePassword;

public interface OTPRepository extends JpaRepository<OneTimePassword, Long>   {

	@Query(value = "SELECT * FROM OTP \r\n"
			+ "            WHERE user_id = ?1 \r\n"
			+ "            AND one_time_password = ?2 \r\n"
			+ "            ORDER BY expiry_date DESC \r\n"
			+ "            LIMIT 1", nativeQuery = true)
	Optional<OneTimePassword> findByUserIdAndOneTimePassword(String userId, String otp);

}
