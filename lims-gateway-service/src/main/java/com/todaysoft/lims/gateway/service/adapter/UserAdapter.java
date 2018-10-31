package com.todaysoft.lims.gateway.service.adapter;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.todaysoft.lims.gateway.model.UserDetailsModel;

@Component
public class UserAdapter extends AbstractAdapter
{
    private static final String SERVICE_NAME = "lims-user-service";
    
    @HystrixCommand(fallbackMethod = "fallback")
    public List<UserDetailsModel> getContactUsers(List<Integer> userIds)
    {
        String url = getServiceUrl("/user/contact_users");
        ResponseEntity<List<UserDetailsModel>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<List<Integer>>(userIds), new ParameterizedTypeReference<List<UserDetailsModel>>()
            {
            });
        return exchange.getBody();
    }
    
    @Override
    public String getName()
    {
        return SERVICE_NAME;
    }
}
