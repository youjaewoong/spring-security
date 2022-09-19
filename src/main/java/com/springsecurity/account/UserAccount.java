package com.springsecurity.account;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;

public class UserAccount extends User {
	
	private static final long serialVersionUID = -4888809422251740267L;
	
	private Account account;

    public UserAccount(Account account) {
        super(account.getUsername(), 
        	  account.getPassword(), 
        	  List.of(new SimpleGrantedAuthority("ROLE_" + account.getRole())));
        
        this.account = account;
    }

    public Account getAccount() {
        return account;
    }
}