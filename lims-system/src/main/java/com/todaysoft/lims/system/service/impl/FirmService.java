package com.todaysoft.lims.system.service.impl;

import java.util.Collections;

import org.springframework.beans.BeanUtils;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.todaysoft.lims.system.model.vo.Firm;
import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.service.IFirmService;

@Service
public class FirmService extends RestService implements IFirmService
{
    
    @Override
    public Pagination<Firm> paging(Firm searcher, int pageNo, int pageSize)
    {
        Firm request = new Firm();
        BeanUtils.copyProperties(searcher, request);
        request.setPageNo(pageNo);
        request.setPageSize(pageSize);
        
        String url = GatewayService.getServiceUrl("/firm/paging");
        ResponseEntity<Pagination<Firm>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<Firm>(request), new ParameterizedTypeReference<Pagination<Firm>>()
            {
            });
        return exchange.getBody();
    }
    
    @Override
    public void modify(Firm request)
    {
        
        String url = GatewayService.getServiceUrl("/firm/modify");
        template.postForObject(url, request, String.class);
        
    }
    
    @Override
    public Firm get(Integer id)
    {
        // TODO Auto-generated method stub
        String url = GatewayService.getServiceUrl("/firm/{id}");
        return template.getForObject(url, Firm.class, Collections.singletonMap("id", id));
    }
    
    @Override
    public void delete(Integer id)
    {
        String url = GatewayService.getServiceUrl("/firm/{id}");
        template.delete(url, Collections.singletonMap("id", id));
        
    }
    
    @Override
    public void create(Firm request)
    {
        String url = GatewayService.getServiceUrl("/firm/create");
        template.postForObject(url, request, String.class);
        
    }
    
    @Override
    public boolean validate(Firm request)
    {
        String url = GatewayService.getServiceUrl("/firm/validate");
        return template.postForObject(url, request, boolean.class);
    }
    
    @Override
    public Pagination<Firm> selectFirm(Firm key, int pageNo, int pageSize)
    {
        Firm request = new Firm();
        BeanUtils.copyProperties(key, request);
        request.setPageNo(pageNo);
        request.setPageSize(pageSize);
        
        return exchange("/firm/selectFirm", request, new ParameterizedTypeReference<Pagination<Firm>>()
        {
        });
    }
    
    @Override
    public void enableFirm(Firm request)
    {
        String url = GatewayService.getServiceUrl("/firm/enableFirm");
        template.postForObject(url, request, String.class);
        
    }
    
}
