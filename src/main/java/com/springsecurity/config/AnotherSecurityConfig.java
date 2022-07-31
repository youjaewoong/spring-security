package com.springsecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@Order(Ordered.LOWEST_PRECEDENCE - 100)
public class AnotherSecurityConfig {
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.antMatcher("/account/**")
                .authorizeRequests()
                .anyRequest().permitAll();
        
        return http.build();
    }
}