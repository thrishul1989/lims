package com.todaysoft.lims.invoice.adapter.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import com.todaysoft.lims.invoice.model.UserModel;

@Component
public class UserAdapter extends AbstractAdapter
{
    private static final String SERVICE_NAME = "lims-user-service";
    
    @Autowired
    private RestTemplate template;
    
    public UserModel getUser(String id)
    {
        String url = getServiceUrl("/smm/user/{id}");
        return template.getForObject(url, UserModel.class, Collections.singletonMap("id", id));
    }
    
    @SuppressWarnings("unchecked")
    public String getInvoiceOperatorId(String name)
    {
        String url = getServiceUrl("/smm/invoiceUser/findUserByName");
        
        Map<String, String> request = new HashMap<String, String>();
        request.put("name", name);
        List<?> records = template.postForObject(url, request, List.class);
        
        if (CollectionUtils.isEmpty(records))
        {
            return null;
        }
        
        Map<String, Object> data = (Map<String, Object>)records.get(0);
        return (String)data.get("id");
    }
    
    @Override
    public String getName()
    {
        return SERVICE_NAME;
    }
    
}
