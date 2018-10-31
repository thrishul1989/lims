package com.todaysoft.lims.system.modules.bpm.rqt.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.todaysoft.lims.system.modules.bpm.rqt.model.RQTAssignArgs;
import com.todaysoft.lims.system.modules.bpm.rqt.model.RQTAssignModel;
import com.todaysoft.lims.system.modules.bpm.rqt.model.RQTAssignRequest;
import com.todaysoft.lims.system.modules.bpm.rqt.model.RQTAssignableTaskSearcher;
import com.todaysoft.lims.system.modules.bpm.rqt.model.RQTSheetModel;
import com.todaysoft.lims.system.modules.bpm.rqt.model.RQTSubmitRequest;
import com.todaysoft.lims.system.modules.bpm.rqt.model.RQTTask;
import com.todaysoft.lims.system.modules.bpm.rqt.service.IRQTService;
import com.todaysoft.lims.system.service.impl.GatewayService;
import com.todaysoft.lims.system.service.impl.RestService;

@Service
public class RQTService extends RestService implements IRQTService
{
    @Override
    public Map<String, List<String>> validateIndex(String ids)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/rqt/validateIndex/{ids}");
        return template.getForObject(url, Map.class, ids);
    }
    
    @Override
    public List<RQTTask> getRQTAssignableList(RQTAssignableTaskSearcher searcher)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/rqt/tasks/assignable");
        ResponseEntity<List<RQTTask>> exchange =
            template.exchange(url,
                HttpMethod.POST,
                new HttpEntity<RQTAssignableTaskSearcher>(searcher),
                new ParameterizedTypeReference<List<RQTTask>>()
                {
                });
        return exchange.getBody();
    }
    
    @Override
    public RQTAssignModel getRQTAssignModel(RQTAssignArgs args)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/rqt/tasks/assigning");
        ResponseEntity<RQTAssignModel> exchange =
            template.exchange(url,
                HttpMethod.POST,
                new HttpEntity<RQTAssignArgs>(args),
                new ParameterizedTypeReference<RQTAssignModel>()
                {
                });
        return exchange.getBody();
    }
    
    @Override
    public void assignRQT(RQTAssignRequest request)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/rqt/tasks/assign");
        template.postForLocation(url, request);
    }
    
    @Override
    public RQTSheetModel getRQTSubmitModel(String id)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/rqt/sheets/{id}");
        return template.getForObject(url, RQTSheetModel.class, id);
    }
    
    @Override
    public void submitRQT(RQTSubmitRequest request)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/rqt/sheets/submit");
        template.postForLocation(url, request);
    }
}
