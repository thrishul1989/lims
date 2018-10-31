package com.todaysoft.lims.biology.adapter.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.todaysoft.lims.biology.adapter.IDictAdapter;
import com.todaysoft.lims.biology.model.response.DictModel;

import java.util.List;

@Service
public class DictAdapter implements IDictAdapter
{
    
    @Autowired
    private RestTemplate template;
    
    @Override
    public DictModel getDictByText(String category, String text)
    {
        String url = GatewayService.getServiceUrl("/smm/dict/getDictByText?category=" + category + "&text=" + text);
        return template.getForObject(url, DictModel.class, category, text);
    }

    @Override
    public List<DictModel> getDictByCategory(String category) {
        String url = GatewayService.getServiceUrl("/smm/dict/{category}/entries");
        ResponseEntity<List<DictModel>> exchange = template.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<DictModel>>(){}, category);
        return exchange.getBody();
    }
}
