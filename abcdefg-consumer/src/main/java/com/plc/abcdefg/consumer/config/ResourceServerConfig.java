//package com.plc.abcdefg.consumer.config;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
//import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
//import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
//import org.springframework.security.oauth2.provider.token.TokenStore;
//import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
//import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
//
//
///**
// * 〈资源认证服务器〉
// *
// * @author Curise
// * @create 2018/12/13
// * @since 1.0.0
// */
////@Configuration
////@EnableResourceServer
//public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
//
//    @Value("${jwt.signingKey}")
//    private String jwtSigningKey;
//
//
//    @Override
//    public void configure(HttpSecurity http) throws Exception {
//
//        http.authorizeRequests()
//                .antMatchers(HttpMethod.OPTIONS).permitAll()
//                .anyRequest().authenticated();
//    }
//
//    @Override
//    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
//        //设置资源ID，需要在授权模块中的resources_ids里添加上这个ID，如果授权模块添加，则必须添加
////        resources
////                .resourceId("test")
////                .tokenStore(jwtTokenStore());
//    }
//
//
//    @Bean
//    public TokenStore jwtTokenStore() {
//        return new JwtTokenStore(jwtTokenConverter());
//    }
//
//    @Bean
//    protected JwtAccessTokenConverter jwtTokenConverter() {
//        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
//        converter.setSigningKey(jwtSigningKey);
//        return converter;
//    }
//
//
//
//
//}
