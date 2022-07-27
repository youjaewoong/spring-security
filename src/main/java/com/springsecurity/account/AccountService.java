package com.springsecurity.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * UserDetailsService 인터페이스를 통한
 * user 정보처리
 *
 */
@Service
public class AccountService implements UserDetailsService {

    @Autowired 
    AccountRepository accountRepository;
    
    //SpringSecurityApplication 에 bean으로 등록된 pw 저장방식 호출
    @Autowired
    PasswordEncoder passwordEncoder;

    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findByUsername(username);
        if (account == null) {
            throw new UsernameNotFoundException(username);
        }
        
        //spring 내부적으로 유저를 인증
        return User.builder()
        		.username(account.getUsername())
        		.password(account.getPassword())
        		.roles(account.getRole())
        		.build();
    }
    
    //incoding 방식 처리
    public Account createNew(Account account) {
        account.encodePassword(passwordEncoder);
        return this.accountRepository.save(account);
    }
}