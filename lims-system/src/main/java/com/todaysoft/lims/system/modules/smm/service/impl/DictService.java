package com.todaysoft.lims.system.modules.smm.service.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.modules.smm.model.Dict;
import com.todaysoft.lims.system.modules.smm.model.DictSearcher;
import com.todaysoft.lims.system.modules.smm.service.IDictService;
import com.todaysoft.lims.system.modules.smm.service.request.DictPagingRequest;
import com.todaysoft.lims.system.service.impl.GatewayService;
import com.todaysoft.lims.system.service.impl.RestService;

@Service
public class DictService extends RestService implements IDictService
{
    @Override
    public Pagination<Dict> paging(DictSearcher searcher, int pageNo, int pageSize)
    {
        DictPagingRequest request = new DictPagingRequest();
        BeanUtils.copyProperties(searcher, request);
        request.setPageNo(pageNo);
        request.setPageSize(pageSize);
        String url = GatewayService.getServiceUrl("/smm/dict/paging");
        ResponseEntity<Pagination<Dict>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<DictPagingRequest>(request), new ParameterizedTypeReference<Pagination<Dict>>()
            {
            });
        return exchange.getBody();
    }
    
    @Override
    public List<Dict> getEntries(String category)
    {
        String url = GatewayService.getServiceUrl("/smm/dict/{category}/entries");
        ResponseEntity<List<Dict>> exchange = template.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<Dict>>()
        {
        }, category);
        return exchange.getBody();
    }
    
    @Override
    public Dict getEntry(String category, String value)
    {
        if (value.indexOf("/") != -1)
        {
            value = value.replace("/", "_");
        }
        
        String url = GatewayService.getServiceUrl("/smm/dict/{category}/entries/{value}");
        return template.getForObject(url, Dict.class, category, value);
    }
    
    @Override
    public Dict get(String id)
    {
        String url = GatewayService.getServiceUrl("/smm/dict/{id}");
        return template.getForObject(url, Dict.class, Collections.singletonMap("id", id));
    }
    
    @Override
    public Dict getDict(String category, String value)
    {
        String url = GatewayService.getServiceUrl("/smm/dict/getDict/{category}/{value}");
        return template.getForObject(url, Dict.class, category, value);
    }
    
    @Override
    public void modify(Dict request)
    {
        String url = GatewayService.getServiceUrl("/smm/dict/modify");
        template.postForObject(url, request, Dict.class);
    }
    
    @Override
    public Dict getDictByText(String category, String text)
    {
        String url = GatewayService.getServiceUrl("/smm/dict/getDictByText?category=" + category + "&text=" + text);
        return template.getForObject(url, Dict.class, category, text);
    }
}
