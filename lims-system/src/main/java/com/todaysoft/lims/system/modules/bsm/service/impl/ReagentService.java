package com.todaysoft.lims.system.modules.bsm.service.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.model.vo.Reagent;
import com.todaysoft.lims.system.modules.bsm.model.ReagentSeacher;
import com.todaysoft.lims.system.modules.bsm.service.IReagentService;
import com.todaysoft.lims.system.modules.bsm.service.request.ReagentCodeUniqueRequest;
import com.todaysoft.lims.system.modules.bsm.service.request.ReagentCreateRequest;
import com.todaysoft.lims.system.modules.bsm.service.request.ReagentListRequest;
import com.todaysoft.lims.system.modules.bsm.service.request.ReagentModifyRequest;
import com.todaysoft.lims.system.service.impl.GatewayService;
import com.todaysoft.lims.system.service.impl.RestService;

@Service
public class ReagentService extends RestService implements IReagentService
{
    @Override
    public Pagination<Reagent> paging(ReagentSeacher seacher, int pageNo, int pageSize)
    {
        ReagentListRequest request = new ReagentListRequest();
        BeanUtils.copyProperties(seacher, request);
        request.setPageNo(pageNo);
        request.setPageSize(pageSize);
        String url = GatewayService.getServiceUrl("/bsm/reagent/paging");
        ResponseEntity<Pagination<Reagent>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<ReagentListRequest>(request), new ParameterizedTypeReference<Pagination<Reagent>>()
            {
            });
        return exchange.getBody();
    }
    
    @Override
    public List<Reagent> list(ReagentSeacher seacher)
    {
        ReagentListRequest request = new ReagentListRequest();
        BeanUtils.copyProperties(seacher, request);
        String url = GatewayService.getServiceUrl("/bsm/reagent/list");
        ResponseEntity<List<Reagent>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<ReagentListRequest>(request), new ParameterizedTypeReference<List<Reagent>>()
            {
            });
        return exchange.getBody();
    }
    
    @Override
    public Reagent get(String id)
    {
        String url = GatewayService.getServiceUrl("/bsm/reagent/{id}");
        return template.getForObject(url, Reagent.class, id);
    }
    
    @Override
    public boolean unique(String id, String code)
    {
        ReagentCodeUniqueRequest request = new ReagentCodeUniqueRequest();
        request.setId(id);
        request.setCode(code);
        return template.postForObject(GatewayService.getServiceUrl("/bsm/reagent/unique"), request, boolean.class);
    }
    
    @Override
    public String create(ReagentCreateRequest request)
    {
        String url = GatewayService.getServiceUrl("/bsm/reagent/create");
        return template.postForObject(url, request, String.class);
    }
    
    @Override
    public void modify(ReagentModifyRequest request)
    {
        String url = GatewayService.getServiceUrl("/bsm/reagent/modify");
        template.postForLocation(url, request);
    }
    
    @Override
    public void delete(String id)
    {
        String url = GatewayService.getServiceUrl("/bsm/reagent/{id}");
        template.delete(url, id);
    }
    
}
