package com.todaysoft.lims.system.modules.bpm.cycleConfig.service.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.system.model.vo.TaskConfig;
import com.todaysoft.lims.system.modules.bcm.model.TestingMethod;
import com.todaysoft.lims.system.modules.bpm.cycleConfig.model.GlobalConfigSearcher;
import com.todaysoft.lims.system.modules.bpm.cycleConfig.model.TestingConfigSearcher;
import com.todaysoft.lims.system.modules.bpm.cycleConfig.model.WarningGlobalConfigModel;
import com.todaysoft.lims.system.modules.bpm.cycleConfig.model.WarningTestingConfigModel;
import com.todaysoft.lims.system.modules.bpm.cycleConfig.service.ICycleConfigService;
import com.todaysoft.lims.system.modules.bpm.cycleConfig.service.request.GlobalConfigRequest;
import com.todaysoft.lims.system.modules.bpm.cycleConfig.service.request.TestingConfigRequest;
import com.todaysoft.lims.system.modules.report.model.ScheduleTestingConfig;
import com.todaysoft.lims.system.service.impl.GatewayService;
import com.todaysoft.lims.system.service.impl.RestService;

@Service
public class CycleConfigService extends RestService implements ICycleConfigService
{
    
    @Override
    public Pagination<WarningGlobalConfigModel> globalPagining(GlobalConfigSearcher searcher)
    {
        String url = GatewayService.getServiceUrl("/bpm/cycleConfig/globalPaging");
        ResponseEntity<Pagination<WarningGlobalConfigModel>> exchange =
            template.exchange(url,
                HttpMethod.POST,
                new HttpEntity<GlobalConfigSearcher>(searcher),
                new ParameterizedTypeReference<Pagination<WarningGlobalConfigModel>>()
                {
                });
        return exchange.getBody();
    }
    
    @Override
    public Pagination<WarningTestingConfigModel> testingPagining(TestingConfigSearcher searcher)
    {
        String url = GatewayService.getServiceUrl("/bpm/cycleConfig/testingPaging");
        ResponseEntity<Pagination<WarningTestingConfigModel>> exchange =
            template.exchange(url,
                HttpMethod.POST,
                new HttpEntity<TestingConfigSearcher>(searcher),
                new ParameterizedTypeReference<Pagination<WarningTestingConfigModel>>()
                {
                });
        return exchange.getBody();
    }
    
    @Override
    public void modify(GlobalConfigRequest request)
    {
        post("/bpm/cycleConfig/modify", request, String.class);
        
    }
    
    @Override
    public List<TestingMethod> getTestingMethodList()
    {
        String url = GatewayService.getServiceUrl("/bpm/cycleConfig/testingMethodList");
        ResponseEntity<List<TestingMethod>> exchange = template.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<TestingMethod>>()
        {
        });
        return exchange.getBody();
    }
    
    @Override
    public List<TaskConfig> getTaskConfigBySemantic(TestingConfigRequest request)
    {
        String url = GatewayService.getServiceUrl("/bpm/cycleConfig/taskConfig");
        ResponseEntity<List<TaskConfig>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<TestingConfigRequest>(request), new ParameterizedTypeReference<List<TaskConfig>>()
            {
            });
        return exchange.getBody();
    }
    
    @Override
    public void create(TestingConfigRequest request)
    {
        String url = GatewayService.getServiceUrl("/bpm/cycleConfig/create");
        template.postForObject(url, request, String.class);
        
    }
    
    @Override
    public boolean validate(TestingConfigSearcher request)
    {
        String url = GatewayService.getServiceUrl("/bpm/cycleConfig/validate");
        boolean res = template.postForObject(url, request, boolean.class);
        return res;
    }
    
    @Override
    public ScheduleTestingConfig getTestingConfigById(String id)
    {
        String url = GatewayService.getServiceUrl("/bpm/cycleConfig/getTestingConfigById/{id}");
        return template.getForObject(url, ScheduleTestingConfig.class, Collections.singletonMap("id", id));
    }
    
    @Override
    public WarningTestingConfigModel getById(String id)
    {
        String url = GatewayService.getServiceUrl("/bpm/cycleConfig/getTestingConfigById/{id}");
        return template.getForObject(url, WarningTestingConfigModel.class, Collections.singletonMap("id", id));
    }
    
    @Override
    public Integer delete(String id)
    {
        String url = GatewayService.getServiceUrl("/bpm/cycleConfig/deleteById/{id}");
        return template.getForObject(url, Integer.class, id);
    }
    
    @Override
    public List<WarningTestingConfigModel> getScheduleTestingConfigList(TestingConfigRequest request)
    {
        String url = GatewayService.getServiceUrl("/bpm/cycleConfig/scheduleTestingConfigList");
        ResponseEntity<List<WarningTestingConfigModel>> exchange =
            template.exchange(url,
                HttpMethod.POST,
                new HttpEntity<TestingConfigRequest>(request),
                new ParameterizedTypeReference<List<WarningTestingConfigModel>>()
                {
                });
        return exchange.getBody();
    }
    
    public List<TestingMethod> getTestingMethodListIncludeVerity()
    {
        String url = GatewayService.getServiceUrl("/bpm/cycleConfig/getTestingMethodListIncludeVerity");
        ResponseEntity<List<TestingMethod>> exchange = template.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<TestingMethod>>()
        {
        });
        return exchange.getBody();
    }
    
}
