package com.todaysoft.lims.system.modules.report.service.impl;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.system.modules.bcm.model.TestingMethod;
import com.todaysoft.lims.system.modules.report.model.DataTemplate;
import com.todaysoft.lims.system.modules.report.model.DataTemplateColumn;
import com.todaysoft.lims.system.modules.report.service.IDataTemplateService;
import com.todaysoft.lims.system.service.impl.GatewayService;
import com.todaysoft.lims.system.service.impl.RestService;

@Service
public class DataTemplateService extends RestService implements IDataTemplateService
{
    
    @Override
    public Pagination<DataTemplate> paging(DataTemplate searcher)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/dataTemplate/paging");
        ResponseEntity<Pagination<DataTemplate>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<DataTemplate>(searcher), new ParameterizedTypeReference<Pagination<DataTemplate>>()
            {
            });
        return exchange.getBody();
    }
    
    @Override
    public DataTemplate get(String id)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/dataTemplate/{id}");
        return template.getForObject(url, DataTemplate.class, id);
    }
    
    @Override
    public void create(DataTemplate request)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/dataTemplate/create");
        template.postForObject(url, request, String.class);
    }
    
    @Override
    public void modify(DataTemplate request)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/dataTemplate/modify");
        template.postForObject(url, request, String.class);
    }
    
    @Override
    public void delete(String id)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/dataTemplate/{id}");
        template.delete(url, id);
    }
    
    @Override
    public boolean validate(DataTemplate request)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/dataTemplate/validate");
        return template.postForObject(url, request, boolean.class);
    }
    
    @Override
    public List<DataTemplate> dataTemplateList(DataTemplate searcher)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/dataTemplate/selectDataTemplate");
        ResponseEntity<List<DataTemplate>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<DataTemplate>(searcher), new ParameterizedTypeReference<List<DataTemplate>>()
            {
            });
        return exchange.getBody();
    }
    
    @Override
    public List<DataTemplate> dataTemplateList(String testingMethodId)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/dataTemplate/dataTemplateList?testingMethodId=" + testingMethodId);
        ResponseEntity<List<DataTemplate>> exchange = template.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<DataTemplate>>()
        {
        });
        return exchange.getBody();
    }
    
    @Override
    public List<DataTemplateColumn> dataTemplateColumnList(DataTemplateColumn searcher)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/dataTemplate/dataTemplateColumnList");
        ResponseEntity<List<DataTemplateColumn>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<DataTemplateColumn>(searcher), new ParameterizedTypeReference<List<DataTemplateColumn>>()
            {
            });
        return exchange.getBody();
    }
    
    @Override
    public DataTemplateColumn getDataTemplateColumn(String id)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/dataTemplate/dataTemplateColumn/{id}");
        return template.getForObject(url, DataTemplateColumn.class, id);
    }
    
    @Override
    public List<TestingMethod> getTestingMethodList()
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/dataTemplate/testingMethodList");
        ResponseEntity<List<TestingMethod>> exchange = template.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<TestingMethod>>()
        {
        });
        return exchange.getBody();
    }
    
    @Override
    public DataTemplate getDataTemplateMapBySheetId(String sheetId)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/dataTemplate/getDataTemplateMapBySheetId/{sheetId}");
        return template.getForObject(url, DataTemplate.class, sheetId);
    }
    
    @Override
    public boolean validateTestingMethod(String testingMethodId)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/dataTemplate/validateTestingMethod/{testingMethodId}");
        return template.getForObject(url, boolean.class, testingMethodId);
    }
}
