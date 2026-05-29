package com.auth.uam.config;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.auth.uam.enums.RolesKeyEnums;
import com.auth.uam.filter.JwtAuthFilter;
import com.auth.uam.security.service.SecurityService;
import com.auth.uam.security.service.UserInfoUserDetailsService;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

	@Autowired
	private JwtAuthFilter authFilter;
	
//	@Autowired
//	SecurityService securityService;

	/* Authontication */
	@Bean
	public UserDetailsService userDetailsService() {
		return new UserInfoUserDetailsService();
	}
	
	
	/* authorization */
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		List<String> roleList = new ArrayList<>();
		System.out.println("security");
		return http.cors().disable().authorizeHttpRequests().antMatchers("/auth/**").permitAll().and()
				.authorizeHttpRequests().anyRequest().denyAll()
				.and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)

				.and().addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class)
				.csrf().disable().build();

	}

	/* authorization */
//	@Bean("SecurityFilter")
//	@DependsOn({"roleLoder"})
//	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//    System.out.println("run SecurityFilterChain");
//		return http.csrf().disable().authorizeHttpRequests().antMatchers("/token/**", "/user/**", "/update/**").permitAll()
////				.authorizeHttpRequests().anyRequest().authenticated()
//				.and().authorizeHttpRequests().antMatchers(HttpMethod.GET, "/access/**").hasAnyAuthority(securityService.getPrivilege(RolesKeyEnums.get.getName()))
//				.and().authorizeHttpRequests().antMatchers(HttpMethod.POST, "/access/**").hasAnyAuthority(securityService.getPrivilege(RolesKeyEnums.post.getName()))
//				.and().authorizeHttpRequests().anyRequest().denyAll()
//				.and().sessionManagement()
//				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//
//				.and().addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class)
//				.build();
//	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

////	password base
	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(userDetailsService());
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		return daoAuthenticationProvider;

	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();

	}

	@Bean
	public JwtAuthFilter authenticationTokenFilterBean() throws Exception {
		return new JwtAuthFilter();
	}
	
//	@Bean
//	public CorsFilter corsFilter() {
//	    final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//	    final CorsConfiguration configuration = new CorsConfiguration();
//	    configuration.setAllowCredentials(true);
//	    configuration.addAllowedOrigin("*");
//	    configuration.addAllowedHeader("*");
//	    configuration.addAllowedMethod("*");
//	    source.registerCorsConfiguration("/**", configuration);
//	    return new CorsFilter(source);
//	}
	
	
}
