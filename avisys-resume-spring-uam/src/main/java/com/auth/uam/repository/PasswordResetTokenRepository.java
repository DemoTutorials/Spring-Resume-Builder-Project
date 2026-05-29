package com.auth.uam.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.auth.uam.entity.PasswordResetToken;

public interface PasswordResetTokenRepository    extends JpaRepository<PasswordResetToken, Long>  {

	Optional<PasswordResetToken> findByPasswordToken(String newPassword);

	long countByUserId(Long userId);

}
