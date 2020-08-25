package com.plc.abcdefg.gateway.filters;

import com.alibaba.fastjson.JSON;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.plc.abcdefg.kernel.model.common.ResponseMsg;
import com.plc.abcdefg.kernel.model.common.ResponseMsgEnum;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TokenFilter extends ZuulFilter {

    private String accountUrl = "/oauth/account/token";
    private String mobileUrl = "/oauth/mobile/token";

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return FilterConstants.DEBUG_FILTER_ORDER;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest httpServletRequest = requestContext.getRequest();
        String requestUri = httpServletRequest.getRequestURI();
        if (!accountUrl.equals(requestUri) && !mobileUrl.equals(requestUri)){
            //检查token
            String header = httpServletRequest.getHeader("Authorization");
            if(header == null){
                HttpServletResponse response = requestContext.getResponse();
                response.setHeader("Content-Type", "application/json;charset=UTF-8");
                requestContext.setSendZuulResponse(false); //终止转发，返回响应报文
                requestContext.setResponseBody(JSON.toJSONString(new ResponseMsg(ResponseMsgEnum.TOKEN_ERROR)));
            }
        }
        return null;
    }
}
