package com.todaysoft.lims.reagent.service.adapter;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.todaysoft.lims.reagent.entity.UserDetailsModel;

@Component
public class UserAdapter extends AbstractAdapter
{
    private static final String SERVICE_NAME = "lims-user-service";
    
    @Autowired
    private RestTemplate template;
    
    public UserDetailsModel getUserDetailsModel(String id)
    {
        String url = getServiceUrl("/smm/user/{id}");
        return template.getForObject(url, UserDetailsModel.class, Collections.singletonMap("id", id));
    }
    
    @Override
    public String getName()
    {
        return SERVICE_NAME;
    }
}
