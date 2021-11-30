package com.laison.erp.config.auth;


import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;

public class LoginAuthenticationProvider extends DaoAuthenticationProvider {
    public LoginAuthenticationProvider(UserDetailsService userDetailsService) {
    	setPasswordEncoder(new MyPasswordEncoder());
        // 这个地方一定要对userDetailsService赋值，不然userDetailsService是null (这个坑有点深)
        setUserDetailsService(userDetailsService);
    }
    
      
}