package com.springsecurity.account;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AccountController {

    @Autowired
    AccountService accountService;

     // 회원가입 TEST
    @ResponseBody
    @GetMapping("/account/{role}/{username}/{password}")
    public Account createAccount(@ModelAttribute Account account) {
        return accountService.createNew(account);
    }
    
        
    @GetMapping("/access-denied")
    public String createAccount(Principal principal, Model model) {
    	model.addAttribute("name", principal.getName());
        return "access-denied";
    }
}
