package com.avisys.gateway.apigateway.config;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.avisys.gateway.apigateway.utils.RolesLoader;

import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfig {

    private AuthenticationManager authenticationManager;
    private SecurityContextRepository securityContextRepository;
    private RolesLoader rolesLoader;

    @Bean("securityFilter")
    @DependsOn({ "rolesLoaderBean", "getRolesLoader" })
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
    	
    	http.cors(cors -> cors.configurationSource(request -> {
            CorsConfiguration configuration = new CorsConfiguration();
            configuration.setAllowedOrigins(Arrays.asList("*"));
            configuration.setAllowedMethods(Arrays.asList("*"));
            configuration.setAllowedHeaders(Arrays.asList("*"));
            return configuration;
        }));
    	
        http.exceptionHandling()
            .authenticationEntryPoint((swe, e) -> Mono.fromRunnable(() -> swe.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED)))
            .accessDeniedHandler((swe, e) -> Mono.fromRunnable(() -> swe.getResponse().setStatusCode(HttpStatus.FORBIDDEN)))
            .and().csrf().disable().formLogin().disable().httpBasic().disable()
            .authenticationManager(authenticationManager).securityContextRepository(securityContextRepository)
            .authorizeExchange().pathMatchers("/auth-service/auth/**").permitAll()
            .and().authorizeExchange().pathMatchers("/restart/**").permitAll()
            .and().authorizeExchange().pathMatchers("/utility/**").permitAll()
            .and().authorizeExchange().pathMatchers("/email/downloadFile/**").permitAll();

        for (Map.Entry<String, String> entry : rolesLoader.getRolesAndUrl().entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
            http.authorizeExchange().pathMatchers(entry.getKey()).hasAnyAuthority(entry.getValue());
        }

        http.authorizeExchange().anyExchange().denyAll();

        return http.build();
    }

//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration corsConfig = new CorsConfiguration();
////        corsConfig.setAllowedOriginPatterns(Collections.singletonList("*"));
//        corsConfig.addAllowedOrigin("*");
//        corsConfig.addAllowedMethod("*");
//        corsConfig.addAllowedHeader("*");
//        
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", corsConfig);
//        
//        return source;
//    }
    
    
    
//    @Bean
//    public CorsWebFilter corsFilter() {
//      org.springframework.web.cors.CorsConfiguration corsConfiguration = new org.springframework.web.cors.CorsConfiguration();
//      
//      corsConfiguration.addAllowedOrigin("*");
//      corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD"));
//      corsConfiguration.addAllowedHeader("origin");
//      corsConfiguration.addAllowedHeader("content-type");
//      corsConfiguration.addAllowedHeader("accept");
//      corsConfiguration.addAllowedHeader("authorization");
//      corsConfiguration.addAllowedHeader("cookie");
//      UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//      source.registerCorsConfiguration("/**", corsConfiguration);
//      return new CorsWebFilter(source);
//    }
}