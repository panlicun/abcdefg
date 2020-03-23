package com.plc.abcdefg.oauth.authentication.account;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

public class AccountAuthenticationProvider implements AuthenticationProvider {

    private RedisTemplate<Object, Object> redisTemplate;

    // 注意这里的userdetailservice ，因为SmsCodeAuthenticationProvider类没有@Component
    // 所以这里不能加@Autowire，只能通过外面设置才行
    private UserDetailsService userDetailsService;

    public PasswordEncoder passwordEncoder;

    /**
     * 在这里认证用户信息
     * @param authentication
     * @return
     * @throws AuthenticationException
     */
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        AccountAuthenticationToken authenticationToken = (AccountAuthenticationToken) authentication;
//        String mobile = (String) authenticationToken.getPrincipal();
        String username = authentication.getName();
        String password = (String) authenticationToken.getCredentials();


        UserDetails user = userDetailsService.loadUserByUsername(username);
        if (user == null) {
            throw new InternalAuthenticationServiceException("无法获取用户信息");
        }
        boolean flag = passwordEncoder.matches(password,user.getPassword());
        if(!flag){
            throw new InternalAuthenticationServiceException("帐号或密码错误");
        }
        AccountAuthenticationToken authenticationResult = new AccountAuthenticationToken(user,null, user.getAuthorities());
        authenticationResult.setDetails(authenticationToken.getDetails());
        return authenticationResult;
    }

    public boolean supports(Class<?> authentication) {
        return AccountAuthenticationToken.class.isAssignableFrom(authentication);
    }

    public UserDetailsService getUserDetailsService() {
        return userDetailsService;
    }

    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    public RedisTemplate<Object, Object> getRedisTemplate() {
        return redisTemplate;
    }

    public void setRedisTemplate(RedisTemplate<Object, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public PasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }

    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
}
