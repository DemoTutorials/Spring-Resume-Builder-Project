package com.avisys.gateway.apigateway.config;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class AuthenticationManager implements ReactiveAuthenticationManager {

	/*
	 * Create AuthenticationManager that implementing ReactiveAuthenticationManager
	 * for validate token and role.
	 */

	private JWTUtil jwtUtil;

//	@Override
//	@SuppressWarnings("unchecked")
//	public Mono<Authentication> authenticate(Authentication authentication) {
//		String authToken = authentication.getCredentials().toString();
//		String username = jwtUtil.getUsernameFromToken(authToken);
//		return Mono.just(jwtUtil.validateToken(authToken)).filter(valid -> valid).switchIfEmpty(Mono.empty())
//				.map(valid -> {
////                Claims claims = jwtUtil.extractAllClaims(authToken);
////                List<String> rolesMap = claims.get("role", List.class);
//					List<String> rolesMap = jwtUtil.getPermission(authToken);
//					System.out.println("auth---------"+rolesMap);
//					return new UsernamePasswordAuthenticationToken(username, null,
//							rolesMap.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
//				});
//	}
	
	 @Override
	    public Mono<Authentication> authenticate(Authentication authentication) {
		 System.out.println("AuthenticationManager");
	        String authToken = authentication.getCredentials().toString();
	        String username= jwtUtil.getUsernameFromToken(authToken);
	        if(username == null) {
	            return Mono.error(new BadCredentialsException("Invalid or expired token"));
	        }
	        return Mono.justOrEmpty(jwtUtil.validateToken(authToken))
	            .flatMap(valid -> {
	                if (!valid) {
	                    return Mono.error(new BadCredentialsException("Expired token"));
	                }
	                List<String> rolesMap = jwtUtil.getPermission(authToken);
	                System.out.println(rolesMap.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
	                
	                return Mono.just(new UsernamePasswordAuthenticationToken(username, null,
	                        rolesMap.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList())));
	            });
	    }
	

}
