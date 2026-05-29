package com.auth.uam.filter;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth.uam.security.service.JwtService;
import com.auth.uam.security.service.UserInfoUserDetailsService;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

	@Autowired
	private JwtService jwtService;

	@Autowired
	private UserInfoUserDetailsService userDetailsService;

//	public JwtAuthFilter(){
//		
//	}
//	JwtAuthFilter(JwtService jwtService){
//		this.jwtService=jwtService;
//	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
System.out.println("start");
		
		
		String authHeader = request.getHeader("Authorization");

//		String path = request.getRequestURI().substring(request.getContextPath().length());
//
//		// 1. Directly go to the next filter.
//		if (path.contains("/auth/accessToken")) {
//
//			filterChain.doFilter(request, response); // If not valid, go to the next filter.
//			return;
//		}
//		
//		if (path.contains("/auth/reset-password-token-generation")) {
//
//			filterChain.doFilter(request, response); // If not valid, go to the next filter.
//			return;
//		}
//		if (path.contains("/auth/reset-password")) {
//
//			filterChain.doFilter(request, response); // If not valid, go to the next filter.
//			return;
//		}
//		
//		if (path.contains("/user/change-password")) {
//
//			filterChain.doFilter(request, response); // If not valid, go to the next filter.
//			return;
//		}
//		
//
//		// 2. validate the header and check the prefix
//		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
//			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
//			return;
//		}

		
		
		String token = null;
		String username = null;
		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			System.out.println("Token : " + authHeader);

			token = authHeader.substring(7);
			username = jwtService.extractUsername(token);
		}

		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = userDetailsService.loadUserByUsername(username);
			System.out.println("userDetails filter :" + userDetails.getAuthorities() + " " + "username :"
					+ userDetails.getUsername() + userDetails);
			if (jwtService.validateToken(token, userDetails)) {

				System.out.println("token valid");
//            	Collection<? extends GrantedAuthority> authorities = jwtService.getAuthorities(token);
//            	System.out.println("token authorities :" + authorities);
				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, "",
						userDetails.getAuthorities());
				
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authToken);
//				response.addHeader("accessToken", jwtService.genrateToken(username));
			}
		}
		
		
//		response.addHeader("accessToken", jwtService.genrateToken(username));
		
		filterChain.doFilter(request, response);

	}

}
