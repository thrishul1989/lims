package com.todaysoft.lims.system.modules.bpm.firstpcr.service.impl;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.todaysoft.lims.system.modules.bpm.firstpcr.model.FirstPCRAssignArgs;
import com.todaysoft.lims.system.modules.bpm.firstpcr.model.FirstPCRAssignModel;
import com.todaysoft.lims.system.modules.bpm.firstpcr.model.FirstPCRAssignRequest;
import com.todaysoft.lims.system.modules.bpm.firstpcr.model.FirstPCRAssignableTaskSearcher;
import com.todaysoft.lims.system.modules.bpm.firstpcr.model.FirstPCRSheetModel;
import com.todaysoft.lims.system.modules.bpm.firstpcr.model.FirstPCRSubmitRequest;
import com.todaysoft.lims.system.modules.bpm.firstpcr.model.FirstPCRTask;
import com.todaysoft.lims.system.modules.bpm.firstpcr.service.IFirstPCRService;
import com.todaysoft.lims.system.service.impl.GatewayService;
import com.todaysoft.lims.system.service.impl.RestService;

@Service
public class FirstPCRService extends RestService implements IFirstPCRService
{
    
    @Override
    public List<FirstPCRTask> getFirstPCRAssignableList(FirstPCRAssignableTaskSearcher searcher)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/firstPcr/tasks/assignable");
        ResponseEntity<List<FirstPCRTask>> exchange =
            template.exchange(url,
                HttpMethod.POST,
                new HttpEntity<FirstPCRAssignableTaskSearcher>(searcher),
                new ParameterizedTypeReference<List<FirstPCRTask>>()
                {
                });
        return exchange.getBody();
    }
    
    @Override
    public FirstPCRAssignModel getFirstPCRAssignModel(FirstPCRAssignArgs args)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/firstPcr/tasks/assigning");
        ResponseEntity<FirstPCRAssignModel> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<FirstPCRAssignArgs>(args), new ParameterizedTypeReference<FirstPCRAssignModel>()
            {
            });
        return exchange.getBody();
    }
    
    @Override
    public void assignFirstPCR(FirstPCRAssignRequest request)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/firstPcr/tasks/assign");
        template.postForLocation(url, request);
    }
    
    @Override
    public FirstPCRSheetModel getFirstPCRSubmitModel(String id)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/firstPcr/sheets/{id}");
        return template.getForObject(url, FirstPCRSheetModel.class, id);
    }
    
    @Override
    public void submitFirstPCR(FirstPCRSubmitRequest request)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/firstPcr/sheets/submit");
        template.postForLocation(url, request);
    }
}
