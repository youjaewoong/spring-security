package com.springsecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@Order(Ordered.LOWEST_PRECEDENCE - 50)
public class SecurityConfig {

//    @Autowired
//    AccountService accountService;

	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
       // http.addFilterBefore(new LoggingFilter(), WebAsyncManagerIntegrationFilter.class);

        http.antMatcher("/**")
        		.authorizeRequests()
                .mvcMatchers("/", "/info", "/account/**").permitAll()
                .mvcMatchers("/admin").hasRole("ADMIN")
                .mvcMatchers("/user").hasRole("USER")
                .anyRequest().authenticated(); //나머지는 기본 인증

        http.formLogin();

//        http.rememberMe()
//                .userDetailsService(accountService)
//                .key("remember-me-sample");

        http.httpBasic();
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