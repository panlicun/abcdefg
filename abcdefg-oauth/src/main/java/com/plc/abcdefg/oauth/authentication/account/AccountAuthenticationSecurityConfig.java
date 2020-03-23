package com.plc.abcdefg.oauth.authentication.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class AccountAuthenticationSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandler;

    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

    @Resource(name = "accountAuthService")
    private UserDetailsService accountAuthService;

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void configure(HttpSecurity http) throws Exception {

        AccountAuthenticationFilter accountAuthenticationFilter = new AccountAuthenticationFilter();
        accountAuthenticationFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
        accountAuthenticationFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler);
        accountAuthenticationFilter.setAuthenticationFailureHandler(authenticationFailureHandler);

        AccountAuthenticationProvider accountAuthenticationProvider = new AccountAuthenticationProvider();
        accountAuthenticationProvider.setUserDetailsService(accountAuthService);
        accountAuthenticationProvider.setRedisTemplate(redisTemplate);
        accountAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        http.authenticationProvider(accountAuthenticationProvider)
                .addFilterAfter(accountAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
