package com.avisys.gateway.apigateway.config;


import java.util.logging.Logger;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;


import java.util.logging.Logger; 
import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@Component
public class SecurityContextRepository implements ServerSecurityContextRepository {

	/* create SecurityContextRepository that implementing 
	 * ServerSecurityContextRepository for get the token and forward 
	 * to AuthenticationManager. */
	
	
//    private AuthenticationManager authenticationManager;
//
//    @Override
//    public Mono<Void> save(ServerWebExchange swe, SecurityContext sc) {
//        throw new UnsupportedOperationException("Not supported yet.");
//    }
//
//    @Override
//    public Mono<SecurityContext> load(ServerWebExchange swe) {
//        return Mono.justOrEmpty(swe.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION))
//            .filter(authHeader -> authHeader.startsWith("Bearer "))
//            .flatMap(authHeader -> {
//                String authToken = authHeader.substring(7);
//                Authentication auth = new UsernamePasswordAuthenticationToken(authToken, authToken);
//                return this.authenticationManager.authenticate(auth).map(SecurityContextImpl::new);
//            });
//    }

	 private final ReactiveAuthenticationManager authenticationManager;
	 private final static Logger logger = Logger.getLogger(SecurityContextRepository.class.getName());

	  

	    @Override
	    public Mono<Void> save(ServerWebExchange swe, SecurityContext sc) {
	        throw new UnsupportedOperationException("Not supported yet.");
	    }

	    @Override
	    public Mono<SecurityContext> load(ServerWebExchange swe) {
	    	System.out.println("SecurityContextRepository");
	    	System.out.println("AUTHORIZATION: " + swe.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION));
	        return Mono.justOrEmpty(swe.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION))
	            .filter(authHeader -> authHeader.startsWith("Bearer "))
	            .flatMap(authHeader -> {
	                String authToken = authHeader.substring(7);
	                Authentication auth = new UsernamePasswordAuthenticationToken(authToken, authToken);
	                return this.authenticationManager.authenticate(auth)
	                    .map(SecurityContextImpl::new)
	                    .onErrorResume(BadCredentialsException.class, e -> {
	                    	logger.info(e.getMessage());
	                        return handleTokenExpiration();
	                    });
	            });
	    }

	    private Mono<SecurityContextImpl> handleTokenExpiration() {
	        // Return a 403 Forbidden response when the token is expired
	    	logger.info("Token expired");
	        return Mono.error(new ResponseStatusException(HttpStatus.FORBIDDEN, "Token expired"));
	    }
	
}