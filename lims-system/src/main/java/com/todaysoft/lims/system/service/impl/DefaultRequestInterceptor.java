package com.todaysoft.lims.system.service.impl;

import java.io.IOException;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import com.todaysoft.lims.system.service.security.AccountDetails;

public class DefaultRequestInterceptor implements ClientHttpRequestInterceptor
{
    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
        throws IOException
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      
        if (null != authentication)
        {
            UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken)authentication;
            AccountDetails member = (AccountDetails)token.getPrincipal();
            WebAuthenticationDetails details = (WebAuthenticationDetails)token.getDetails();
            request.getHeaders().set("member-token", member.getToken());
            request.getHeaders().set("remote-address", details.getRemoteAddress());
            
         
        }
        
        return execution.execute(request, body);
    }
}