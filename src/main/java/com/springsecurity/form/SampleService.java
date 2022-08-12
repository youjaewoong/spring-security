package com.springsecurity.form;

import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SampleService {

    public void dashboard() {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	Object principal = authentication.getPrincipal();
    	Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
    	Object credentials = authentication.getCredentials();
    	boolean autenticated = authentication.isAuthenticated();
    
    	log.info("principla :: {}", principal); //throadLocal로 관리하기 떄문에 매개변수로 안가져와도됨
    	log.info("credentials :: {}", credentials); //null
    	log.info("autenticated :: {}", autenticated); //인증 여부
    	log.info("authorities :: {}", authorities); // 어떤 권한을 가지고있는지 
    }
}