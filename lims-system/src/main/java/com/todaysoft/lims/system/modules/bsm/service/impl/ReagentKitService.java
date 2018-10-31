package com.todaysoft.lims.system.modules.bsm.service.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.model.vo.ReagentKit;
import com.todaysoft.lims.system.modules.bsm.service.IReagentKitService;
import com.todaysoft.lims.system.service.impl.GatewayService;
import com.todaysoft.lims.system.service.impl.RestService;

@Service
public class ReagentKitService extends RestService implements IReagentKitService
{
    
    @Override
    public Pagination<ReagentKit> paging(ReagentKit searcher, int pageNo, int pageSize)
    {
        ReagentKit request = new ReagentKit();
        BeanUtils.copyProperties(searcher, request);
        request.setPageNo(pageNo);
        request.setPageSize(pageSize);
        
        String url = GatewayService.getServiceUrl("/bsm/reagentKit/paging");
        ResponseEntity<Pagination<ReagentKit>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<ReagentKit>(request), new ParameterizedTypeReference<Pagination<ReagentKit>>()
            {
            });
        return exchange.getBody();
    }
    
    @Override
    public void modify(ReagentKit request)
    {
        String url = GatewayService.getServiceUrl("/bsm/reagentKit/modify");
        template.postForObject(url, request, String.class);
        
    }
    
    @Override
    public ReagentKit getReagentKit(String id)
    {
        // TODO Auto-generated method stub
        String url = GatewayService.getServiceUrl("/bsm/reagentKit/{id}");
        return template.getForObject(url, ReagentKit.class, Collections.singletonMap("id", id));
    }
    
    @Override
    public void deleteReagentKit(String id)
    {
        String url = GatewayService.getServiceUrl("/bsm/reagentKit/{id}");
        template.delete(url, Collections.singletonMap("id", id));
        
    }
    
    @Override
    public void createReagentKit(ReagentKit request)
    {
        String url = GatewayService.getServiceUrl("/bsm/reagentKit/create");
        template.postForObject(url, request, String.class);
        
    }
    
    @Override
    public boolean validate(ReagentKit request)
    {
        String url = GatewayService.getServiceUrl("/bsm/reagentKit/validate");
        return template.postForObject(url, request, boolean.class);
    }
    
    @Override
    public Pagination<ReagentKit> selectReagentKit(ReagentKit req, int pageNo, int pageSize)
    {
        ReagentKit request = new ReagentKit();
        BeanUtils.copyProperties(req, request);
        request.setPageNo(pageNo);
        request.setPageSize(pageSize);
        
        return exchange("/bsm/reagentKit/selectReagentKit", request, new ParameterizedTypeReference<Pagination<ReagentKit>>()
        {
        });
    }
    
    @Override
    public List<ReagentKit> list(ReagentKit request)
    {
        String url = GatewayService.getServiceUrl("/bsm/reagentKit/list");
        ResponseEntity<List<ReagentKit>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<ReagentKit>(request), new ParameterizedTypeReference<List<ReagentKit>>()
            {
            });
        return exchange.getBody();
    }

    @Override
    public List<ReagentKit> listByTaskId(ReagentKit reagentKit) {
        String url = GatewayService.getServiceUrl("/bsm/reagentKit/listByTaskId");
        ResponseEntity<List<ReagentKit>> exchange =
                template.exchange(url, HttpMethod.POST, new HttpEntity<ReagentKit>(reagentKit), new ParameterizedTypeReference<List<ReagentKit>>()
                {
                });
        return exchange.getBody();
    }
}
