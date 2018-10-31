package com.todaysoft.lims.system.modules.bpm.primerdesign.service.impl;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.todaysoft.lims.system.modules.bpm.primerdesign.model.PrimerDesignAssignArgs;
import com.todaysoft.lims.system.modules.bpm.primerdesign.model.PrimerDesignAssignModel;
import com.todaysoft.lims.system.modules.bpm.primerdesign.model.PrimerDesignAssignRequest;
import com.todaysoft.lims.system.modules.bpm.primerdesign.model.PrimerDesignAssignableTaskSearcher;
import com.todaysoft.lims.system.modules.bpm.primerdesign.model.PrimerDesignSheetModel;
import com.todaysoft.lims.system.modules.bpm.primerdesign.model.PrimerDesignSubmitRequest;
import com.todaysoft.lims.system.modules.bpm.primerdesign.model.PrimerDesignTask;
import com.todaysoft.lims.system.modules.bpm.primerdesign.service.IPrimerDesignService;
import com.todaysoft.lims.system.service.impl.GatewayService;
import com.todaysoft.lims.system.service.impl.RestService;

@Service
public class PrimerDesignService extends RestService implements IPrimerDesignService
{
    
    @Override
    public List<PrimerDesignTask> getPrimerDesignAssignableList(PrimerDesignAssignableTaskSearcher searcher)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/primerDesign/tasks/assignable");
        ResponseEntity<List<PrimerDesignTask>> exchange =
            template.exchange(url,
                HttpMethod.POST,
                new HttpEntity<PrimerDesignAssignableTaskSearcher>(searcher),
                new ParameterizedTypeReference<List<PrimerDesignTask>>()
                {
                });
        return exchange.getBody();
    }
    
    @Override
    public PrimerDesignAssignModel getPrimerDesignAssignModel(PrimerDesignAssignArgs args)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/primerDesign/tasks/assigning");
        ResponseEntity<PrimerDesignAssignModel> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<PrimerDesignAssignArgs>(args), new ParameterizedTypeReference<PrimerDesignAssignModel>()
            {
            });
        return exchange.getBody();
    }
    
    @Override
    public void assignPrimerDesign(PrimerDesignAssignRequest request)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/primerDesign/tasks/assign");
        template.postForLocation(url, request);
    }
    
    @Override
    public PrimerDesignSheetModel getPrimerDesignSubmitModel(String id)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/primerDesign/sheets/{id}");
        return template.getForObject(url, PrimerDesignSheetModel.class, id);
    }
    
    @Override
    public void submitPrimerDesign(PrimerDesignSubmitRequest request)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/primerDesign/sheets/submit");
        template.postForLocation(url, request);
    }
}
