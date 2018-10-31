package com.todaysoft.lims.system.service.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.todaysoft.lims.system.model.searcher.ProbeSeacher;
import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.model.vo.Probe;
import com.todaysoft.lims.system.service.IProbeService;

@Service
public class ProbeService extends RestService implements IProbeService
{
    
    @Override
    public Pagination<Probe> paging(ProbeSeacher searcher, int pageNo, int pageSize)
    {
        ProbeSeacher request = new ProbeSeacher();
        BeanUtils.copyProperties(searcher, request);
        request.setPageNo(pageNo);
        request.setPageSize(pageSize);
        
        String url = GatewayService.getServiceUrl("/bsm/probe/paging");
        ResponseEntity<Pagination<Probe>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<ProbeSeacher>(request), new ParameterizedTypeReference<Pagination<Probe>>()
            {
            });
        return exchange.getBody();
    }
    
    @Override
    public void modify(ProbeSeacher request)
    {
        
        String url = GatewayService.getServiceUrl("/bsm/probe/modify");
        template.postForObject(url, request, String.class);
        
    }
    
    @Override
    public Probe getProbe(String id)
    {
        // TODO Auto-generated method stub
        String url = GatewayService.getServiceUrl("/bsm/probe/{id}");
        return template.getForObject(url, Probe.class, Collections.singletonMap("id", id));
    }
    
    @Override
    public void deleteProbe(String id)
    {
        String url = GatewayService.getServiceUrl("/bsm/probe/{id}");
        template.delete(url, Collections.singletonMap("id", id));
        
    }
    
    @Override
    public void createProbe(ProbeSeacher request)
    {
        String url = GatewayService.getServiceUrl("/bsm/probe/create");
        template.postForObject(url, request, String.class);
        
    }
    
    @Override
    public boolean validate(ProbeSeacher request)
    {
        String url = GatewayService.getServiceUrl("/bsm/probe/validate");
        return template.postForObject(url, request, boolean.class);
    }
    
    @Override
    public String getProbeNext()
    {
        String url = GatewayService.getServiceUrl("/bsm/probe/getProbeNext");
        
        return template.postForObject(url, new ProbeSeacher(), String.class);
    }
    
    @Override
    public List<Probe> list(ProbeSeacher searcher)
    {
        ProbeSeacher request = new ProbeSeacher();
        if (null != searcher)
        {
            BeanUtils.copyProperties(searcher, request);
        }
        
        String url = GatewayService.getServiceUrl("/bsm/probe/list");
        ResponseEntity<List<Probe>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<ProbeSeacher>(request), new ParameterizedTypeReference<List<Probe>>()
            {
            });
        return exchange.getBody();
    }
    
}
