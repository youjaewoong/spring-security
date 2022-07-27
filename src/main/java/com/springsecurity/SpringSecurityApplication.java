package com.springsecurity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class SpringSecurityApplication {

//  noop 은 쓰지않는다.	
//	@Bean
//	public PasswordEncoder passwordEncoder() {
//		return NoOpPasswordEncoder.getInstance();
//	}
	
	//다양한 paswword incodeing 지원
	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
	
	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityApplication.class, args);
	}

}
