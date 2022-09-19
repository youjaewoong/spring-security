package com.springsecurity.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.expression.SecurityExpressionHandler;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.context.request.async.WebAsyncManagerIntegrationFilter;

import com.springsecurity.account.AccountService;
import com.springsecurity.common.LoggingFilter;

@Configuration
@Order(Ordered.LOWEST_PRECEDENCE - 50)
public class SecurityConfig {

	@Autowired
	AccountService accountService;

	//롤 지정 방법1 accessDecisionManager
	/*
	public AccessDecisionManager accessDecisionManager() {
		RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
		roleHierarchy.setHierarchy("ROLE_ADMIN > ROLE_USER");

		DefaultWebSecurityExpressionHandler handler = new DefaultWebSecurityExpressionHandler();
		handler.setRoleHierarchy(roleHierarchy); //롤 셋팅

		WebExpressionVoter webExpressionVoter = new WebExpressionVoter();
		webExpressionVoter.setExpressionHandler(handler); //롤 추가

		List<AccessDecisionVoter<? extends Object>> voters = Arrays.asList(webExpressionVoter);
		return new AffirmativeBased(voters); //롤 지정
	}
	 */


	//롤 지정 방법2 expressionHandler
	public SecurityExpressionHandler<FilterInvocation> expressionHandler() {
		RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
		roleHierarchy.setHierarchy("ROLE_ADMIN > ROLE_USER");

		DefaultWebSecurityExpressionHandler handler = new DefaultWebSecurityExpressionHandler();
		handler.setRoleHierarchy(roleHierarchy); //롤 셋팅
		return handler; //롤 지정
	}

	/**
	 * authenticationManager Autowired 등록가능하도록 처리
	 */
	@Bean
	public AuthenticationManager authenticationManager(
			AuthenticationConfiguration authenticationConfiguration
			) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		// http.addFilterBefore(new LoggingFilter(), WebAsyncManagerIntegrationFilter.class);

		//filter 등록
		http.addFilterBefore(new LoggingFilter(), WebAsyncManagerIntegrationFilter.class);


		//AccessDecisionManager 를 사용하여 인가를 처라한다.
		http.authorizeRequests()
		.mvcMatchers("/", "/info", "/account/**", "/signup").permitAll()
		.mvcMatchers("/admin").hasRole("ADMIN")
		.mvcMatchers("/user").hasRole("USER")
		.anyRequest().authenticated() //나머지는 기본 인증
		.expressionHandler(expressionHandler());

		http.formLogin()
		.loginPage("/login")
		.permitAll();
		//.usernameParameter("my-username")
		//.passwordParameter("my-password");

		http.rememberMe()
		//.useSecureCookie(true) // https 설정 시 true
		.userDetailsService(accountService)
		.key("remember-me-sample");

		http.httpBasic();//basciAuthenticationFilter를 지원함 password base64로 인코딩 디코딩하면..인증정보 노출
		http.logout().logoutSuccessUrl("/"); //logout filter에서 핸들림함

		//세션 관리 필터: SessionManagementFiter
		//        http.sessionManagement()
		//        		.sessionFixation()
		//        			.changeSessionId()
		//        		.invalidSessionUrl("login") // 유효하지않은 경우 url 이동
		//    			.maximumSessions(1)  //세션 한개만 가능
		//    				.maxSessionsPreventsLogin(false); //기존 로그인을 만료하고 다시 세션 처리 true로 할경우 추가 로그인이 안됨

		//TODO ExceptionTranslatorFilter -> FilterSecurityInterceptor (AccessDecisionManager, AffimitiveBase)
		//TODO AuthenticationException -> AuthenticationEntryPoint
		//TODO AccessDeniedException -> AccessDeniedHandler

		//403 error 발생 시 권한관련
		//        http.exceptionHandling()
		//        	.accessDeniedPage("/access-denied");
		//403 error 발생 시 권한관련 handler 처리
		http.exceptionHandling()
		.accessDeniedHandler(new AccessDeniedHandler() {
			@Override
			public void handle(HttpServletRequest request, HttpServletResponse response,
					AccessDeniedException accessDeniedException) throws IOException, ServletException {
				UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				String username = principal.getUsername();
				System.out.println(username + " is denied to access " + request.getRequestURI());
				response.sendRedirect("/access-denied");
			}
		});


		// http.sessionManagement()
		// 		.sessionCreationPolicy(SessionCreationPolicy.STATELESS); //세션을 사용하지않는 전략

		SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);

		return http.build();
	}


	/*
	 * In MemoryUser setting
    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails user = User.withUsername("user")
                .password("{noop}123") // noop 은 암호화를 하지않았다라는 의미
                .roles("USER")
                .build();
        UserDetails admin = User.withUsername("admin")
                .password("{noop}123")
                .roles("ADMIN")
                .build();
        UserDetails[] userDetails = new UserDetails[2];
        userDetails[0] = user;
        userDetails[1] = admin;
        return new InMemoryUserDetailsManager(userDetails);
    }
	 */

}