package com.todaysoft.lims.system.modules.bmm.service.impl;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.system.modules.bmm.model.SampleBackApply;
import com.todaysoft.lims.system.modules.bmm.model.request.SampleBackApplySearcher;
import com.todaysoft.lims.system.modules.bmm.service.ISampleBackApplyService;
import com.todaysoft.lims.system.service.impl.GatewayService;
import com.todaysoft.lims.system.service.impl.RestService;

@Service
public class SampleBackApplyService extends RestService implements ISampleBackApplyService
{
    
    @Override
    public Pagination<SampleBackApply> paging(SampleBackApplySearcher searcher)
    {
        String url = GatewayService.getServiceUrl("/bmm/sampleBackApply/paging");
        ResponseEntity<Pagination<SampleBackApply>> exchange =
            template.exchange(url,
                HttpMethod.POST,
                new HttpEntity<SampleBackApplySearcher>(searcher),
                new ParameterizedTypeReference<Pagination<SampleBackApply>>()
                {
                });
        
        return exchange.getBody();
    }
    
    @Override
    public SampleBackApply getSampleBackApply(String id)
    {
        String url = GatewayService.getServiceUrl("/bmm/sampleBackApply/getSampleBackApply/{id}");
        return template.getForObject(url, SampleBackApply.class, id);
    }
    
    @Override
    public void create(SampleBackApply request)
    {
        String url = GatewayService.getServiceUrl("/bmm/sampleBackApply/create");
        template.postForLocation(url, request);
        
    }
}
