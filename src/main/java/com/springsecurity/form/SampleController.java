package com.springsecurity.form;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.springsecurity.account.Account;
import com.springsecurity.account.AccountContext;
import com.springsecurity.account.AccountRepository;
import com.springsecurity.account.UserAccount;
import com.springsecurity.common.CurrentUser;

import java.security.Principal;
import java.util.concurrent.Callable;

@Controller
public class SampleController {

	@Autowired
	SampleService sampleService;

	@Autowired
	AccountRepository accountRepository;

	// @Autowired BookRepository bookRepository;

//    @GetMapping("/")
//    public String index(Model model, Principal principlal) {
//    	if (principlal == null) {
//    		model.addAttribute("message", "Hello Spring Security");
//    	} else {
//    		model.addAttribute("message", principlal.getName());
//    	}
//        return "index";
//    }

//    /**
//     * UserAccount adeptor class로 확장
//     * @param model
//     * @param userAccount
//     * @return
//     */
//	@GetMapping("/")
//	public String index(Model model, @AuthenticationPrincipal UserAccount userAccount) {
//		if (userAccount == null) {
//			model.addAttribute("message", "Hello Spring Security");
//		} else {
//			model.addAttribute("message", userAccount.getUsername());
//		}
//		return "index";
//	}

	/**
	 * expression 사용
	 * 
	 * @param model
	 * @param userAccount
	 * @return
	 */
	@GetMapping("/")
	public String index(Model model, @CurrentUser Account account) {
		
		if (account == null) {
			model.addAttribute("message", "Hello Spring Security");
		} else {
			model.addAttribute("message", account.getUsername());
		}
		return "index";
	}

	@GetMapping("/info")
	public String info(Model model) {
		model.addAttribute("message", "Info");
		return "info";
	}

	@GetMapping("/dashboard")
	public String dashboard(Model model, Principal principal) {
		model.addAttribute("message", "Hello " + principal.getName());
		AccountContext.setAccount(accountRepository.findByUsername(principal.getName()));
		sampleService.dashboard();
		return "dashboard";
	}

	@GetMapping("/admin")
	public String admin(Model model, Principal principal) {
		model.addAttribute("message", "Hello Admin, " + principal.getName());
		return "admin";
	}

	@GetMapping("/user")
	public String user(Model model, Principal principal) {
		model.addAttribute("message", "Hello User, " + principal.getName());
		return "user";
	}

}