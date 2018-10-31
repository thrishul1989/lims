package com.todaysoft.lims.system.modules.bpm.firstpcrsanger.service.impl;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.todaysoft.lims.system.modules.bpm.firstpcrsanger.model.*;
import com.todaysoft.lims.system.modules.bpm.firstpcrsanger.service.IFirstPCRSangerService;
import com.todaysoft.lims.system.service.impl.GatewayService;
import com.todaysoft.lims.system.service.impl.RestService;

@Service
public class FirstPCRSangerService extends RestService implements IFirstPCRSangerService
{
    
    @Override
    public List<FirstPCRSangerTask> getFirstPCRAssignableList(FirstPCRSangerAssignableTaskSearcher searcher)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/firstPcrSanger/tasks/assignable");
        ResponseEntity<List<FirstPCRSangerTask>> exchange =
            template.exchange(url,
                HttpMethod.POST,
                new HttpEntity<FirstPCRSangerAssignableTaskSearcher>(searcher),
                new ParameterizedTypeReference<List<FirstPCRSangerTask>>()
                {
                });
        return exchange.getBody();
    }
    
    @Override
    public FirstPCRSangerAssignModel getFirstPCRAssignModel(FirstPCRSangerAssignArgs args)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/firstPcrSanger/tasks/assigning");
        ResponseEntity<FirstPCRSangerAssignModel> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<FirstPCRSangerAssignArgs>(args), new ParameterizedTypeReference<FirstPCRSangerAssignModel>()
            {
            });
        return exchange.getBody();
    }
    
    @Override
    public void assignFirstPCR(FirstPCRSangerAssignRequest request)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/firstPcrSanger/tasks/assign");
        template.postForLocation(url, request);
    }
    
    @Override
    public FirstPCRSangerSheetModel getFirstPCRSubmitModel(String id)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/firstPcrSanger/sheets/{id}");
        return template.getForObject(url, FirstPCRSangerSheetModel.class, id);
    }
    
    @Override
    public void submitFirstPCR(FirstPCRSangerSubmitRequest request)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/firstPcrSanger/sheets/submit");
        template.postForLocation(url, request);
    }
}
