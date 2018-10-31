package com.todaysoft.lims.system.modules.bpm.pooling.service.impl;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.todaysoft.lims.system.modules.bpm.pooling.model.PoolingAssignModel;
import com.todaysoft.lims.system.modules.bpm.pooling.model.PoolingAssignRequest;
import com.todaysoft.lims.system.modules.bpm.pooling.model.PoolingAssignableTaskSearcher;
import com.todaysoft.lims.system.modules.bpm.pooling.model.PoolingSubmitModel;
import com.todaysoft.lims.system.modules.bpm.pooling.model.PoolingSubmitRequest;
import com.todaysoft.lims.system.modules.bpm.pooling.model.PoolingTask;
import com.todaysoft.lims.system.modules.bpm.pooling.service.IPoolingService;
import com.todaysoft.lims.system.service.impl.GatewayService;
import com.todaysoft.lims.system.service.impl.RestService;

@Service
public class PoolingService extends RestService implements IPoolingService
{
    //混样
    @Override
    public List<PoolingTask> getPoolingAssignableList(PoolingAssignableTaskSearcher searcher)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/pooling/tasks/assignable");
        ResponseEntity<List<PoolingTask>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<PoolingAssignableTaskSearcher>(searcher), new ParameterizedTypeReference<List<PoolingTask>>()
            {
            });
        return exchange.getBody();
    }
    
    @Override
    public PoolingAssignModel getPoolingAssignModel(String id)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/pooling/tasks/assigning/{id}");
        return template.getForObject(url, PoolingAssignModel.class, id);
    }
    
    @Override
    public boolean isCodeUnique(String code)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/pooling/unique/{code}");
        Boolean rsp = template.getForObject(url, Boolean.class, code);
        return null != rsp && rsp;
    }
    
    @Override
    public void assignPooling(PoolingAssignRequest request)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/pooling/tasks/assign");
        template.postForLocation(url, request);
    }
    
    @Override
    public PoolingSubmitModel getPoolingSubmitModel(String id)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/pooling/sheets/{id}");
        return template.getForObject(url, PoolingSubmitModel.class, id);
    }
    
    @Override
    public void submitPooling(PoolingSubmitRequest request)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/pooling/sheets/submit");
        template.postForLocation(url, request);
    }
}
