package com.todaysoft.lims.system.modules.bpm.bioanalysis.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.modules.bpm.bioanalysis.model.dataTemplate.DataTemplate;
import com.todaysoft.lims.system.modules.bpm.bioanalysis.model.dataTemplate.DataTemplateModel;
import com.todaysoft.lims.system.modules.bpm.bioanalysis.model.dataTemplate.DataTemplateRequest;
import com.todaysoft.lims.system.modules.bpm.bioanalysis.service.IBioDataTemplateService;
import com.todaysoft.lims.system.service.impl.GatewayService;

@Service
public class BioDataTemplateService implements IBioDataTemplateService
{
    
    @Autowired
    protected RestTemplate template;
    
    @Override
    public Pagination<DataTemplate> searcher(DataTemplateRequest searcher, int pageNo, int pageSize)
    {
        searcher.setPageNo(pageNo);
        searcher.setPageSize(pageSize);
        String url = GatewayService.getServiceUrl("/technicalanaly/dataTemplate/paging");
        ResponseEntity<Pagination<DataTemplate>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<DataTemplateRequest>(searcher), new ParameterizedTypeReference<Pagination<DataTemplate>>()
            {
            });
        return exchange.getBody();
    }
    
    @Override
    public void create(DataTemplateRequest request)
    {
        template.postForObject(GatewayService.getServiceUrl("/technicalanaly/dataTemplate/create"), request, void.class);
    }
    
    @Override
    public boolean validate(DataTemplateRequest request)
    {
        return template.postForObject(GatewayService.getServiceUrl("/technicalanaly/dataTemplate/validate"), request, boolean.class);
    }
    
    @Override
    public DataTemplateModel get(String id)
    {
        DataTemplateRequest request = new DataTemplateRequest();
        request.setId(id);
        return template.postForObject(GatewayService.getServiceUrl("/technicalanaly/dataTemplate/getById"), request, DataTemplateModel.class);
    }
    
    @Override
    public void modify(DataTemplateRequest request)
    {
        template.postForObject(GatewayService.getServiceUrl("/technicalanaly/dataTemplate/update"), request, void.class);
    }
    
    @Override
    public List<DataTemplate> getDataTemplateList(DataTemplateRequest searcher)
    {
        
        ResponseEntity<List<DataTemplate>> exchange =
            template.exchange(GatewayService.getServiceUrl("/technicalanaly/dataTemplate/getDataTemplateList"),
                HttpMethod.POST,
                new HttpEntity<DataTemplateRequest>(searcher),
                new ParameterizedTypeReference<List<DataTemplate>>()
                {
                });
        return exchange.getBody();
    }
    
    @Override
    public void delete(String id)
    {
        DataTemplateRequest request = new DataTemplateRequest();
        request.setId(id);
        template.postForObject(GatewayService.getServiceUrl("/technicalanaly/dataTemplate/delete"), request, void.class);
    }
    
}
