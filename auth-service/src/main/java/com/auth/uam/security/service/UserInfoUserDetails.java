package com.auth.uam.security.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.auth.uam.entity.Roles;
import com.auth.uam.entity.User;

public class UserInfoUserDetails implements UserDetails {

	private String userName;
	private String password;
	private List<GrantedAuthority> authorities = new ArrayList<>();
	private boolean isDeleted;

	UserInfoUserDetails(User user) {
		userName = user.getUserName();
		password = user.getPassword();
		List<Roles> roleList = user.getRole();
		List<GrantedAuthority> authorities1 = new ArrayList<>();
		try {
			roleList.forEach(r -> {
				r.getPermission().forEach(p -> {
					authorities1.add(new SimpleGrantedAuthority(p.getPermissionName()));
				});
//				GrantedAuthority g = new GrantedAuthority();

//				System.out.println("authorities :" + authorities);
			});
		} catch (Exception e) {
			e.printStackTrace();
		}

//		authorities = Arrays.stream(user.getRole().split(",")).map(SimpleGrantedAuthority::new)
//				.collect(Collectors.toList());
		authorities = authorities1.stream().distinct().collect(Collectors.toList());
		System.out.println("authorities :" + authorities);

		isDeleted = user.getIsDeleted();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return userName;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
