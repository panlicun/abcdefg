package com.plc.abcdefg.oauth.authentication.mobile;

import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class MobileAuthenticationProvider implements AuthenticationProvider {

    private RedisTemplate<Object, Object> redisTemplate;

    // 注意这里的userdetailservice ，因为SmsCodeAuthenticationProvider类没有@Component
    // 所以这里不能加@Autowire，只能通过外面设置才行
    private UserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 在这里认证用户信息
     * @param authentication
     * @return
     * @throws AuthenticationException
     */
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        MobileAuthenticationToken authenticationToken = (MobileAuthenticationToken) authentication;
//        String mobile = (String) authenticationToken.getPrincipal();
        String mobile = authentication.getName();
        String smsCode = (String) authenticationToken.getCredentials();

        //从redis中获取该手机号的验证码
//        String smsCodeFromRedis = (String) redisTemplate.opsForValue().get(mobile);
        String smsCodeFromRedis = "7777";
        if(!smsCode.equals(smsCodeFromRedis)){
            throw new InternalAuthenticationServiceException("手机验证码不正确");
        }

        UserDetails user = userDetailsService.loadUserByUsername(mobile);
        if (user == null) {
            throw new InternalAuthenticationServiceException("无法获取用户信息");
        }

        MobileAuthenticationToken authenticationResult = new MobileAuthenticationToken(user,null, user.getAuthorities());
        authenticationResult.setDetails(authenticationToken.getDetails());
        return authenticationResult;
    }

    public boolean supports(Class<?> authentication) {
        return MobileAuthenticationToken.class.isAssignableFrom(authentication);
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
}
