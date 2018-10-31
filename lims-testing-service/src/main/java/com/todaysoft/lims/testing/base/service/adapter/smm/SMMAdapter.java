package com.todaysoft.lims.testing.base.service.adapter.smm;

import java.io.IOException;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.todaysoft.lims.testing.base.action.RequestHeaders;

@Component
public class SMMAdapter
{
    private static final String SERVICE_NAME = "lims-user-service";
    
    @Autowired
    private RestTemplate template;
    
    public UserMinimalModel getLoginUser(final String token)
    {
        String url = "http://" + SERVICE_NAME + "/smm/authorize/user";
        template.setInterceptors(Collections.singletonList(new ClientHttpRequestInterceptor()
        {
            @Override
            public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
                throws IOException
            {
                request.getHeaders().set(RequestHeaders.MEMBER_TOKEN, token);
                return execution.execute(request, body);
            }
            
        }));
        
        return template.getForObject(url, UserMinimalModel.class);
    }
    
    public UserMinimalModel getUserByID(String id)
    {
        String url = "http://" + SERVICE_NAME + "/smm/user/{id}/minimal";
        return template.getForObject(url, UserMinimalModel.class, id);
    }
}
