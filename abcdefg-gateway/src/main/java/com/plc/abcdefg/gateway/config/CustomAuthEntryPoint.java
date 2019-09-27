package com.plc.abcdefg.gateway.config;

import com.alibaba.fastjson.JSONObject;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 用来解决匿名用户访问无权限资源时的异常
 */
@Component
public class CustomAuthEntryPoint implements AuthenticationEntryPoint {

    @Override
    // TODO: 2019/9/25 这块需要详细修改
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        httpServletResponse.setCharacterEncoding("utf-8");
        httpServletResponse.setContentType("text/javascript;charset=utf-8");
        httpServletResponse.getWriter().print(JSONObject.toJSONString("没有访问权限!"));
    }
}
