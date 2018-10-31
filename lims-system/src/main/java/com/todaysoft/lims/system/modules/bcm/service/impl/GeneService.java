package com.todaysoft.lims.system.modules.bcm.service.impl;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.model.vo.disease.Gene;
import com.todaysoft.lims.system.modules.bcm.service.IGeneService;
import com.todaysoft.lims.system.modules.bcm.service.request.GenePagingRequest;
import com.todaysoft.lims.system.service.impl.GatewayService;
import com.todaysoft.lims.system.service.impl.RestService;

@Service
public class GeneService extends RestService implements IGeneService
{
    
    @Override
    public Pagination<Gene> paging(GenePagingRequest searcher, int pageNo, int pageSize)
    {
        searcher.setPageNo(pageNo);
        searcher.setPageSize(pageSize);
        
        String url = GatewayService.getServiceUrl("/bcm/gene/paging");
        ResponseEntity<Pagination<Gene>> exchange =
            template.exchange(url,
                HttpMethod.POST,
                new HttpEntity<GenePagingRequest>(searcher),
                new ParameterizedTypeReference<Pagination<Gene>>()
                {
                });
        return exchange.getBody();
    }
    
}
