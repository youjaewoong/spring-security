package com.springsecurity.account;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AccountController {

    @GetMapping("/access-denied")
    public String createAccount(Principal principal, Model model) {
    	model.addAttribute("name", principal.getName());
        return "access-denied";
    }
}
