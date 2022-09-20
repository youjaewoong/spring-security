package com.springsecurity.account;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import lombok.Getter;

import java.util.List;

@Getter
public class UserAccount extends User {
	
	private static final long serialVersionUID = 1L;
	
	public Account account;

	public UserAccount(Account account) {
		super(account.getUsername(), account.getPassword(),
				List.of(new SimpleGrantedAuthority("ROLE_" + account.getRole())));
		this.account = account;
	}
}