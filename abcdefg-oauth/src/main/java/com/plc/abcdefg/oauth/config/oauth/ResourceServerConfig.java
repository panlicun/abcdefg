package com.plc.abcdefg.oauth.config.oauth;

import com.plc.abcdefg.oauth.authentication.mobile.MobileAuthenticationSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableResourceServer
@Order(3)
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Autowired
    protected AuthenticationSuccessHandler authenticationSuccessHandler;

    @Autowired
    protected AuthenticationFailureHandler authenticationFailureHandler;

    @Autowired
    private MobileAuthenticationSecurityConfig mobileAuthenticationSecurityConfig;

    @Override
    public void configure(HttpSecurity http) throws Exception {
//                http    // 配置登陆页/login并允许访问
//                .formLogin().permitAll()
////                .formLogin().loginPage("http://www.baidu.com")  //登录页面
////                .loginProcessingUrl("http://www.taobao.com")  //提交请求的路径
//                // 登出页
//                .and().logout().logoutUrl("/logout").logoutSuccessUrl("/")
//                // 其余所有请求全部需要鉴权认证
//                .and().authorizeRequests().antMatchers("/**").permitAll()
//                .anyRequest().authenticated()
//
//                // 由于使用的是JWT，我们这里不需要csrf
//                .and().csrf().disable();




        // 所以在我们的app登录的时候我们只要提交的action，不要跳转到登录页
        http.formLogin()
                //登录页面，app用不到
                //.loginPage("/authentication/login")
                //登录提交action，app会用到
                // 用户名登录地址
                .loginProcessingUrl("/form/token")
                .successHandler(authenticationSuccessHandler)
                .failureHandler(authenticationFailureHandler);




//                //成功处理器 返回Token
//                .successHandler(authenticationSuccessHandler)
//                //失败处理器
//                .failureHandler(authenticationFailureHandler);

        http
                // 手机验证码登录
                .apply(mobileAuthenticationSecurityConfig)
                .and()
                .authorizeRequests()
                //手机验证码登录地址
                .antMatchers("/form/token","/mobile/token", "/email/token")
                .permitAll()
//                .and()
//                .authorizeRequests()
//                .antMatchers(
//                        "/register",
//                        "/social/**",
//                        "/**/*.js",
//                        "/**/*.css",
//                        "/**/*.jpg",
//                        "/**/*.png",
//                        "/**/*.woff2",
//                        "/code/image")
//                .permitAll()//以上的请求都不需要认证
                .anyRequest()
                .authenticated()
                .and()
                .csrf().disable();
    }
}
