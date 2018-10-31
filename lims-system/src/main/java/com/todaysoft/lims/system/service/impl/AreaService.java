package com.todaysoft.lims.system.service.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.todaysoft.lims.system.model.vo.DataArea;
import com.todaysoft.lims.system.service.IAreaService;

@Service
public class AreaService extends RestService implements IAreaService
{
    
    @Override
    public DataArea get(Integer id)
    {
        String url = GatewayService.getServiceUrl("/bmm/dataarea/get/{id}");
        return template.getForObject(url, DataArea.class, Collections.singletonMap("id", id));
    }
    
    @Override
    public List<DataArea> findRoots()
    {
        String url = GatewayService.getServiceUrl("/bmm/dataarea/findRoots");
        ResponseEntity<List<DataArea>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<DataArea>(new DataArea()), new ParameterizedTypeReference<List<DataArea>>()
            {
            });
        return exchange.getBody();
    }
    
    @Override
    public List<DataArea> findByParentId(Integer parentId)
    {
        String url = GatewayService.getServiceUrl("/bmm/dataarea/findByParentId");
        DataArea searcher = new DataArea();
        searcher.setParentId(parentId);
        ResponseEntity<List<DataArea>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<DataArea>(searcher), new ParameterizedTypeReference<List<DataArea>>()
            {
            });
        return exchange.getBody();
    }
}
