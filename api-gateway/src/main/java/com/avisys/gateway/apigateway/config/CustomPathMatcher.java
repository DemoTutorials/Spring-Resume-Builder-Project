package com.avisys.gateway.apigateway.config;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

public class CustomPathMatcher
//implements ServerWebExchangeMatcher
{

//	private final String pattern;
//
//	public CustomPathMatcher(String pattern) {
//		this.pattern = pattern;
//	}
//
//	@Override
//	public Mono<MatchResult> matches(ServerWebExchange exchange) {
//		ServerHttpRequest request = exchange.getRequest();
//		String requestPath = request.getPath().value();
//
//		// Implement your custom path matching logic here
//		// For simplicity, we'll use AntPathMatcher
////        boolean matches = new AntPathMatcher().match(pattern, requestPath);
//
//		boolean matches = macth(pattern, requestPath);
//		
//		return matches ? MatchResult.match() : MatchResult.notMatch();
//	}
//
//	private boolean macth(String pattern2, String requestPath) {
//		String[] patternArr = pattern2.split("/");
//		String[] pathArr = requestPath.split("/");
//
//		if (patternArr.length != pathArr.length) {
//
//			return false;
//		}
//
//		for (int i = 0; i < patternArr.length; i++) {
//
//			if (patternArr[i].equals(pathArr[i])) {
//
//			} else if (patternArr[i].startsWith("{") && patternArr[i].endsWith("}")) {
//
//			} else {
//
//				return false;
//			}
//
//		}
//		return true;
//	}

}
