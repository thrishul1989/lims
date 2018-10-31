package com.todaysoft.lims.system.modules.bpm.qt.service.impl;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.todaysoft.lims.system.modules.bpm.qt.model.QtAssignArgs;
import com.todaysoft.lims.system.modules.bpm.qt.model.QtAssignModel;
import com.todaysoft.lims.system.modules.bpm.qt.model.QtAssignRequest;
import com.todaysoft.lims.system.modules.bpm.qt.model.QtAssignableTaskSearcher;
import com.todaysoft.lims.system.modules.bpm.qt.model.QtSubmitModel;
import com.todaysoft.lims.system.modules.bpm.qt.model.QtSubmitRequest;
import com.todaysoft.lims.system.modules.bpm.qt.model.QtTask;
import com.todaysoft.lims.system.modules.bpm.qt.service.IQtService;
import com.todaysoft.lims.system.service.impl.GatewayService;
import com.todaysoft.lims.system.service.impl.RestService;

@Service
public class QtService extends RestService implements IQtService
{
    
    @Override
    public List<QtTask> getAssignableList(QtAssignableTaskSearcher searcher)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/qt/tasks/assignable");
        ResponseEntity<List<QtTask>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<QtAssignableTaskSearcher>(searcher), new ParameterizedTypeReference<List<QtTask>>()
            {
            });
        return exchange.getBody();
    }
    
    @Override
    public QtAssignModel getAssignModel(QtAssignArgs args)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/qt/tasks/assigning");
        return template.postForObject(url, args, QtAssignModel.class);
    }
    
    @Override
    public void assign(QtAssignRequest request)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/qt/tasks/assign");
        template.postForLocation(url, request);
    }
    
    @Override
    public QtSubmitModel getSubmitModel(String id)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/qt/sheets/{id}");
        return template.getForObject(url, QtSubmitModel.class, id);
    }
    
    @Override
    public void submit(QtSubmitRequest request)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/qt/sheets/submit");
        template.postForLocation(url, request);
    }
}
