package com.todaysoft.lims.sample.service.adapter;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.todaysoft.lims.sample.entity.User;
import com.todaysoft.lims.sample.model.InvoiceUserModel;
import com.todaysoft.lims.sample.model.UserDetailsModel;
import com.todaysoft.lims.sample.model.UserSimpleModel;
import com.todaysoft.lims.sample.model.request.UserListRequest;

@Component
public class UserAdapter extends AbstractAdapter
{
    
    private static final String SERVICE_NAME = "lims-user-service";
    
    @Autowired
    private RestTemplate template;
    
    public UserDetailsModel getUser(String id)
    {
        String url = getServiceUrl("/smm/user/{id}");
        return template.getForObject(url, UserDetailsModel.class, Collections.singletonMap("id", id));
    }
    
    public User get(String username)
    {
        String url = getServiceUrl("/smm/user/username/{username}");
        return template.getForObject(url, User.class, Collections.singletonMap("username", username));
    }
    
    public List<UserSimpleModel> userSelect(UserListRequest request)
    {
        String url = getServiceUrl("/smm/user/userSelect");
        ResponseEntity<List<UserSimpleModel>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<UserListRequest>(request), new ParameterizedTypeReference<List<UserSimpleModel>>()
            {
            });
        return exchange.getBody();
    }
    
    public InvoiceUserModel getInvoiceUser(String id)
    {
        String url = getServiceUrl("/smm/invoiceUser/user/{id}");
        return template.getForObject(url, InvoiceUserModel.class, id);
    }
    
    @Override
    public String getName()
    {
        return SERVICE_NAME;
    }
    
}
