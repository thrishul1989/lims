package com.todaysoft.lims.system.modules.bpm.dt.service.impl;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.todaysoft.lims.system.modules.bpm.dt.model.*;
import com.todaysoft.lims.system.modules.bpm.dt.service.IDTService;
import com.todaysoft.lims.system.service.impl.GatewayService;
import com.todaysoft.lims.system.service.impl.RestService;

@Service
public class DTService extends RestService implements IDTService
{
    @Override
    public List<DTTask> getDTAssignableList(DTAssignableTaskSearcher searcher)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/dt/tasks/assignable");
        ResponseEntity<List<DTTask>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<DTAssignableTaskSearcher>(searcher), new ParameterizedTypeReference<List<DTTask>>()
            {
            });
        return exchange.getBody();
    }
    
    @Override
    public DTAssignModel getDTAssignModel(DTAssignArgs args)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/dt/tasks/assigning");
        ResponseEntity<DTAssignModel> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<DTAssignArgs>(args), new ParameterizedTypeReference<DTAssignModel>()
            {
            });
        return exchange.getBody();
    }
    
    @Override
    public void assignDT(DTAssignRequest request)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/dt/tasks/assign");
        template.postForLocation(url, request);
    }
    
    @Override
    public DTSubmitModel getDTSubmitModel(String id)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/dt/sheets/{id}");
        return template.getForObject(url, DTSubmitModel.class, id);
    }
    
    @Override
    public void submitDT(DTSubmitModel model)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/dt/sheets/submit");
        template.postForLocation(url, model);
    }
}
