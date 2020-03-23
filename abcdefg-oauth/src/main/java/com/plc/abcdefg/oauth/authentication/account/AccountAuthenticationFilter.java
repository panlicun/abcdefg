package com.plc.abcdefg.oauth.authentication.account;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AccountAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    private boolean postOnly = true;

    AccountAuthenticationFilter() {
        super(new AntPathRequestMatcher("/account/token", "POST"));
    }

    /**
     * 添加未认证用户认证信息，然后在provider里面进行正式认证
     *
     * @param httpServletRequest
     * @param httpServletResponse
     * @return
     * @throws AuthenticationException
     * @throws IOException
     * @throws ServletException
     */
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
            throws AuthenticationException, IOException, ServletException {
        if (postOnly && !httpServletRequest.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + httpServletRequest.getMethod());
        }

        String username = httpServletRequest.getParameter("username");
        String password = httpServletRequest.getParameter("password");

        AccountAuthenticationToken authRequest = new AccountAuthenticationToken(username, password);
        setDetails(httpServletRequest, authRequest);
        return this.getAuthenticationManager().authenticate(authRequest);
    }


    private void setDetails(HttpServletRequest request, AccountAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }


}
