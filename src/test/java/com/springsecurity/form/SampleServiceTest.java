package com.springsecurity.form;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit4.SpringRunner;

import com.springsecurity.account.Account;
import com.springsecurity.account.AccountService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SampleServiceTest {

    @Autowired
    SampleService sampleService;

    @Autowired
    AccountService accountService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Test
    //@WithMockUser
    public void dashboard() {
        Account account = new Account();
        account.setRole("ADMIN");
        account.setUsername("gos1004");
        account.setPassword("123");
        accountService.createNew(account);

        UserDetails userDetails = accountService.loadUserByUsername("gos1004");
        
        //user객체의 pw와 현재 pw 비교
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails, "123");
        Authentication authentication = authenticationManager.authenticate(token);
        
        System.out.println(authentication);

        //인증된authentication은 holder에 저장된다
        SecurityContextHolder.getContext().setAuthentication(authentication);
        sampleService.dashboard();
    }

}