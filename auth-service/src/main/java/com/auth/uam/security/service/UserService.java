package com.auth.uam.security.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.auth.uam.entity.User;
import com.auth.uam.proxy.RegistrationService;
import com.auth.uam.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	RegistrationService registration;

	
	public Page<User> getSearchAndPagination(String name, Pageable pageable) {
		Page<User> userPage = null;
		
		if (ObjectUtils.isEmpty(name)) {
			userPage= userRepository.findByIsDeleted(false,pageable);
		}else {
			userPage = userRepository.findByIsDeletedAndUserNameLikeIgnoreCaseOrIsDeletedAndFirstNameLikeIgnoreCaseOrIsDeletedAndLastNameLikeIgnoreCaseOrIsDeletedAndEmailLikeIgnoreCase(false,"%" + name + "%",false,"%" + name + "%",false,"%" + name + "%",false,"%" + name + "%",pageable);
		}
		return userPage;
	}
	
	public Optional<User> getById(String id) {
		Optional<User> findByUserIdAndIsDeleted = userRepository.findByUserIdAndIsDeleted(id, false);
		return findByUserIdAndIsDeleted;
	}
	
	public User create(User user) {
		LocalDateTime dateTime = LocalDateTime.now();
		user.setCreationDate(dateTime);
		user.setIsDeleted(false);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}

//	public List<UserProjection> getListUser() {
//
//		return userRepository.getList();
//	}

//	public User updateRoles(User user) {
//		Optional<User> getById = userRepository.findByUserIdAndIsDeleted(user.getUserId(), false);
//		User existingUser = getById.get();
//		LocalDateTime dateTime = LocalDateTime.now();
//		existingUser.setRole(user.getRole());
//		existingUser.setLastUpdateBy(user.getLastUpdateBy());
//		existingUser.setLastUpdateDate(dateTime);
//		return userRepository.save(existingUser);
//	}

	public String changePassword(String userName, String password, String newPassword) {
		Optional<User> findByUserName = userRepository.findByUserNameAndIsDeleted(userName, false);
		if (findByUserName.isEmpty()) {
			return "Invalid UserName.";
		}
		User user = findByUserName.get();

		boolean matches = passwordEncoder.matches(password, user.getPassword());
		if (matches) {
			LocalDateTime dateTime = LocalDateTime.now();
			user.setPassword(passwordEncoder.encode(newPassword));
			user.setLastUpdatePasswordDate(dateTime);
			userRepository.save(user);
			return "Password has been changed.";
		}else {
			
			return "Invalid Password.";
		}
		
		
	}

	public User softDelete(String userId, String updatedBy) {
		Optional<User> getById = userRepository.findByUserIdAndIsDeleted(userId, false);
		
		User existingUser = getById.get();
		existingUser.setIsDeleted(true);
		return userRepository.save(existingUser);
	}	

}
