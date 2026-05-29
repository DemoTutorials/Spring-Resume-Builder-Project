package com.avisys.gateway.apigateway.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import com.avisys.gateway.apigateway.config.JWTUtil;

@Component
public class AuthonticationFilter 
extends AbstractGatewayFilterFactory<AuthonticationFilter.Config> 
{

	@Autowired
	JWTUtil jwtUtil;
	
	@Autowired
	private RouteValidator routeValidator;


	public AuthonticationFilter() {
		super(Config.class);
	}

	@Override
	public GatewayFilter apply(Config config) {
		return ((exchange, chain) -> {
			System.out.println("AuthonticationFilter");
			ServerHttpRequest request = exchange.getRequest();
            HttpHeaders headers = new HttpHeaders();
            headers.putAll(request.getHeaders());
            String authorizationHeader = headers.getFirst("Authorization");
            headers.add("userId", jwtUtil.getUserId(authorizationHeader.substring(7)));
            ServerHttpRequest modifiedRequest = request.mutate().headers(httpHeaders -> httpHeaders.addAll(headers)).build();
            return chain.filter(exchange.mutate().request(modifiedRequest).build());
			


//			if (routeValidator.isSecured.test(exchange.getRequest())) {
//				
//				System.out.println("custome filter run");
//
//				ServerHttpRequest request = exchange.getRequest();
//            
//				System.out.println(request.getRemoteAddress());
//				System.out.println(request.getHeaders());
//				System.out.println(request.getMethod());
//				System.out.println(request.getBody());
//				System.out.println(request.getPath());
//				System.out.println(request.getRemoteAddress());
//				System.out.println(request.getURI());
//				System.out.println();
//				System.out.println();
//			
//			}

		});

	}

	public static class Config {

	}

	
	
	
}
