package com.todaysoft.lims.gateway.service.impl;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.StringUtils;

public class DistributeRequestInterceptor implements ClientHttpRequestInterceptor
{
    private HttpServletRequest sourceRequest;
    
    public DistributeRequestInterceptor(HttpServletRequest request)
    {
        sourceRequest = request;
    }
    
    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
        throws IOException
    {
        // 业务请求头设置
        String token = sourceRequest.getHeader("member-token");
        
        if (!StringUtils.isEmpty(token))
        {
            request.getHeaders().set("member-token", token);
        }
        
        String address = sourceRequest.getHeader("remote-address");
        
        if (!StringUtils.isEmpty(address))
        {
            request.getHeaders().set("remote-address", address);
        }
        
        return execution.execute(request, body);
    }
    
    public HttpServletRequest getSourceRequest()
    {
        return sourceRequest;
    }
}
