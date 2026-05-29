package com.auth.uam.security.service;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.auth.uam.entity.User;
import com.auth.uam.repository.UserRepository;

@Component
public class UserInfoUserDetailsService implements UserDetailsService {

	@Autowired
	UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Optional<User> findByUserName = userRepository.findByUserNameAndIsDeleted(username, false);

		if (findByUserName.isPresent()) {
			
		
		return findByUserName.map(UserInfoUserDetails::new)
				.orElseThrow(() -> new UsernameNotFoundException("User no found" + username));

		}else {
			throw new UsernameNotFoundException("User no found" + username);
		}
	}

}
