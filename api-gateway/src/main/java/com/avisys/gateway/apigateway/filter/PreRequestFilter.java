package com.avisys.gateway.apigateway.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import com.avisys.gateway.apigateway.config.JWTUtil;

import reactor.core.publisher.Mono;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE) // Ensure this filter is executed first
public class PreRequestFilter implements WebFilter {

	@Autowired
	JWTUtil jwtUtil;
	
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        // Modify the request here if needed
        // Add headers, modify URI, etc.
        // For example:
    	try {
			
		
    	System.out.println("PreRequestFilter");
		ServerHttpRequest request = exchange.getRequest();
        HttpHeaders headers = new HttpHeaders();
        headers.putAll(request.getHeaders());
        String authorizationHeader = headers.getFirst("Authorization");
        System.out.println("Authorization: "+authorizationHeader);
        System.out.println(headers.getFirst("Access-Control-Request-Headers"));
        if (authorizationHeader!=null) {
        	String userId = jwtUtil.getUserId(authorizationHeader.substring(7));
        	 exchange.getRequest()
             .mutate()
             .headers(httpHeaders -> httpHeaders.add("userId", userId) 
            		 );
        	 String userRole = jwtUtil.getUserRole(authorizationHeader.substring(7));
        	 exchange.getRequest()
             .mutate()
             .headers(httpHeaders -> httpHeaders.add("userRole", userRole) 
            		 );

     // Proceed to the next filter in the chain
     return chain.filter(exchange);
		}
        else {
        	// Proceed to the next filter in the chain
            return chain.filter(exchange);
       		}
        
    	} catch (Exception e) {
    		return chain.filter(exchange);
		}
        }
        
       
    
}