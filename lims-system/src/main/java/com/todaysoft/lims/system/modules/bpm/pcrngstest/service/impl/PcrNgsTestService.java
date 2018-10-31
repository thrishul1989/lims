package com.todaysoft.lims.system.modules.bpm.pcrngstest.service.impl;

import java.util.List;

import com.todaysoft.lims.system.modules.bpm.pcrngstest.service.IPcrNgsTestService;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.todaysoft.lims.system.modules.bpm.pcrngstest.model.*;
import com.todaysoft.lims.system.service.impl.GatewayService;
import com.todaysoft.lims.system.service.impl.RestService;

@Service
public class PcrNgsTestService extends RestService implements IPcrNgsTestService
{
    
    @Override
    public List<PcrNgsTestTask> getPcrNgsTestAssignableList(PcrNgsTestAssignableTaskSearcher searcher)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/pcrNgsTest/tasks/assignable");
        ResponseEntity<List<PcrNgsTestTask>> exchange =
            template.exchange(url,
                HttpMethod.POST,
                new HttpEntity<PcrNgsTestAssignableTaskSearcher>(searcher),
                new ParameterizedTypeReference<List<PcrNgsTestTask>>()
                {
                });
        return exchange.getBody();
    }
    
    @Override
    public PcrNgsTestAssignModel getPcrNgsTestAssignModel(PcrNgsTestAssignArgs args)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/pcrNgsTest/tasks/assigning");
        ResponseEntity<PcrNgsTestAssignModel> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<PcrNgsTestAssignArgs>(args), new ParameterizedTypeReference<PcrNgsTestAssignModel>()
            {
            });
        return exchange.getBody();
    }
    
    @Override
    public void assignPcrNgsTest(PcrNgsTestAssignRequest request)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/pcrNgsTest/tasks/assign");
        template.postForLocation(url, request);
    }
    
    @Override
    public PcrNgsTestSheetModel getPcrNgsTestSubmitModel(String id)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/pcrNgsTest/sheets/{id}");
        return template.getForObject(url, PcrNgsTestSheetModel.class, id);
    }
    
    @Override
    public void submitPcrNgsTest(PcrNgsTestSubmitRequest request)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/pcrNgsTest/sheets/submit");
        template.postForLocation(url, request);
    }
}
