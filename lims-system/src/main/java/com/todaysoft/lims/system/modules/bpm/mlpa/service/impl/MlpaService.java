package com.todaysoft.lims.system.modules.bpm.mlpa.service.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.todaysoft.lims.system.modules.bpm.mlpa.model.MlpaTestAssignArgs;
import com.todaysoft.lims.system.modules.bpm.mlpa.model.MlpaTestAssignModel;
import com.todaysoft.lims.system.modules.bpm.mlpa.model.MlpaTestAssignRequest;
import com.todaysoft.lims.system.modules.bpm.mlpa.model.MlpaTestAssignableTaskSearcher;
import com.todaysoft.lims.system.modules.bpm.mlpa.model.MlpaTestSubmitModel;
import com.todaysoft.lims.system.modules.bpm.mlpa.model.MlpaTestTask;
import com.todaysoft.lims.system.modules.bpm.mlpa.service.IMlpaService;
import com.todaysoft.lims.system.service.impl.GatewayService;
import com.todaysoft.lims.system.service.impl.RestService;

@Service
public class MlpaService extends RestService implements IMlpaService
{
    @Override
    public List<MlpaTestTask> getMlpaAssignableList(MlpaTestAssignableTaskSearcher searcher)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/mlpaTest/tasks/assignable");
        ResponseEntity<List<MlpaTestTask>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<MlpaTestAssignableTaskSearcher>(searcher), new ParameterizedTypeReference<List<MlpaTestTask>>()
            {
            });
        return exchange.getBody();
    }
    
    @Override
    public MlpaTestAssignModel getMlpaAssignModel(MlpaTestAssignArgs args)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/mlpaTest/tasks/assigning");
        ResponseEntity<MlpaTestAssignModel> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<MlpaTestAssignArgs>(args), new ParameterizedTypeReference<MlpaTestAssignModel>()
            {
            });
        return exchange.getBody();
    }
    
    @Override
    public void assignMlpa(MlpaTestAssignRequest request)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/mlpaTest/tasks/assign");
        template.postForLocation(url, request);
    }
    
    @Override
    public MlpaTestSubmitModel getMlpaSubmitModel(String id)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/mlpaTest/sheets/{id}");
        return template.getForObject(url, MlpaTestSubmitModel.class, id);
    }
    
    @Override
    public void submitMlpa(MlpaTestSubmitModel model)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/mlpaTest/sheets/submit");
        template.postForLocation(url, model);
    }
}
