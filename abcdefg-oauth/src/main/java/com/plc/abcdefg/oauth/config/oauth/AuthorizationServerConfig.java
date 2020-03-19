package com.plc.abcdefg.oauth.config.oauth;

import com.alibaba.fastjson.JSON;
import com.plc.abcdefg.oauth.auth.service.AuthService;
import com.plc.abcdefg.oauth.config.error.AbcdefgWebResponseExceptionTranslator;
import com.plc.abcdefg.kernel.model.common.ResponseMsgEnum;
import com.plc.abcdefg.kernel.model.common.ResponseMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import javax.sql.DataSource;

@Configuration
@EnableAuthorizationServer
@Order(3)
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    // TODO: 2019/9/25 整个类需要整改 
    private Logger log = LoggerFactory.getLogger(AuthorizationServerConfig.class);

    @Autowired
    private DataSource dataSource;

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AuthService authService;

    @Value("${jwt.signingKey}")
    private String jwtSigningKey;


    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.allowFormAuthenticationForClients()
                .checkTokenAccess("isAuthenticated()")  // 开启/oauth/check_token验证端口认证权限访问
                .tokenKeyAccess("permitAll()");         // 开启/oauth/token_key验证端口无权限访问
        //当权限不足时返回
        security.accessDeniedHandler((request, response, e) -> {
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            response.getWriter()
                    .write(JSON.toJSONString(new ResponseMsg(ResponseMsgEnum.PERMISSION_DENIED)));
        });
        //当token不正确时返回
        security.authenticationEntryPoint((request, response, e) -> {
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            response.getWriter()
                    .write(JSON.toJSONString(new ResponseMsg(ResponseMsgEnum.TOKEN_ERROR)));
        });
        log.info("AuthorizationServerSecurityConfigurer is complete");
    }

    /**
     * 配置客户端详情信息(Client Details)
     * clientId：（必须的）用来标识客户的Id。
     * secret：（需要值得信任的客户端）客户端安全码，如果有的话。
     * scope：用来限制客户端的访问范围，如果为空（默认）的话，那么客户端拥有全部的访问范围。
     * authorizedGrantTypes：此客户端可以使用的授权类型，默认为空。
     * authorities：此客户端可以使用的权限（基于Spring Security authorities）。
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        //读取数据库配置
        clients.withClientDetails(clientDetails());
        //内存配置
//        clients.inMemory()
//                .withClient("android")
//                .scopes("read")
//                .secret("673d4fe71b5fa74b6c59935d41e93df5eb7ee4271404f784")
//                .authorizedGrantTypes("password", "authorization_code", "refresh_token")
//                .and()
//                .withClient("webapp")
//                .scopes("read")
//                .authorizedGrantTypes("implicit");
        log.info("ClientDetailsServiceConfigurer is complete!");
    }

    /**
     * 配置授权、令牌的访问端点和令牌服务
     * tokenStore：采用redis储存
     * authenticationManager:身份认证管理器, 用于"password"授权模式
     */
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .tokenStore(jwtTokenStore())
                .userDetailsService(authService)  //刷新token的时候需要改类进行比对
                .tokenEnhancer(jwtTokenConverter())
                .authenticationManager(authenticationManager)
                .exceptionTranslator(new AbcdefgWebResponseExceptionTranslator());  //统一异常处理
    }

    @Bean
    public TokenStore jwtTokenStore() {
        return new JwtTokenStore(jwtTokenConverter());
    }

    @Bean
    protected JwtAccessTokenConverter jwtTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey(jwtSigningKey);
        return converter;
    }

    @Bean
    public ClientDetailsService clientDetails() {
        return new JdbcClientDetailsService(dataSource);
    }

}
