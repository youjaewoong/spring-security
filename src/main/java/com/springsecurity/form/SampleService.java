package com.springsecurity.form;

import java.util.Collection;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class SampleService {

	/**
	 * 메소드 호출전에 권한검사
	 * @Secured("ROLE_USER")
	 * @RolesAllowed("ROLE_USER")
	 * @PreAuthorize("hasRloe("hasRole(USER)") : 파라미터와 검사가능
	 * 
	 * 메소드 호출 후 리턴값을 통한 검사가능
	 * @PostAuthorize
	 */
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    public void dashboard() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        System.out.println("===============");
        System.out.println(authentication);
        System.out.println(userDetails.getUsername());
    }
    
    /**
     * AuthenticationManager는 사용자가 입력한 Authentication객체를 
     * 인증할수 있는 Autheticationprovider를 찾아주고 
     * 해당 provider는 UserdetailsService가 반환한 userdetail를 바탕으로 
     * 비번일치유무로 인증을 하고 일치한다면 Authenticationmanager는 
     * Autheticationrovider가 반환한 Authentication(principal 변수에 userdetail를 담음)를 
     * getmaapin 메서드에 principal매개변수로 넘겨준다.
     */
    public void dashboard_ex() {
    	//SecurityContextHolder 인증된 정보가 저장된다.
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //누구에 해당하는 정보 userDetails type
        Object principal = authentication.getPrincipal();
        
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        boolean authenticated = authentication.isAuthenticated();
    }

//    @Async
//    public void asyncService() {
//        SecurityLogger.log("Async Service");
//        System.out.println("Async service is called.");
//    }
}