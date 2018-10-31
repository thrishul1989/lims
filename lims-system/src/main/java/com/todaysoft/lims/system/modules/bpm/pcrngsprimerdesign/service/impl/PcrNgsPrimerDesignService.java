package com.todaysoft.lims.system.modules.bpm.pcrngsprimerdesign.service.impl;

import java.util.List;

import com.todaysoft.lims.system.modules.bpm.pcrngsprimerdesign.service.IPcrNgsPrimerDesignService;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.todaysoft.lims.system.modules.bpm.pcrngsprimerdesign.model.*;
import com.todaysoft.lims.system.service.impl.GatewayService;
import com.todaysoft.lims.system.service.impl.RestService;

@Service
public class PcrNgsPrimerDesignService extends RestService implements IPcrNgsPrimerDesignService
{
    
    @Override
    public List<PcrNgsPrimerDesignTask> getPrimerDesignAssignableList(PcrNgsPrimerDesignAssignableTaskSearcher searcher)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/pcrNgs/primerDesign/tasks/assignable");
        ResponseEntity<List<PcrNgsPrimerDesignTask>> exchange =
            template.exchange(url,
                HttpMethod.POST,
                new HttpEntity<PcrNgsPrimerDesignAssignableTaskSearcher>(searcher),
                new ParameterizedTypeReference<List<PcrNgsPrimerDesignTask>>()
                {
                });
        return exchange.getBody();
    }
    
    @Override
    public PcrNgsPrimerDesignAssignModel getPrimerDesignAssignModel(PcrNgsPrimerDesignAssignArgs args)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/pcrNgs/primerDesign/tasks/assigning");
        ResponseEntity<PcrNgsPrimerDesignAssignModel> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<PcrNgsPrimerDesignAssignArgs>(args), new ParameterizedTypeReference<PcrNgsPrimerDesignAssignModel>()
            {
            });
        return exchange.getBody();
    }
    
    @Override
    public void assignPrimerDesign(PcrNgsPrimerDesignAssignRequest request)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/pcrNgs/primerDesign/tasks/assign");
        template.postForLocation(url, request);
    }
    
    @Override
    public PcrNgsPrimerDesignSheetModel getPrimerDesignSubmitModel(String id)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/pcrNgs/primerDesign/sheets/{id}");
        return template.getForObject(url, PcrNgsPrimerDesignSheetModel.class, id);
    }
    
    @Override
    public void submitPrimerDesign(PcrNgsPrimerDesignSubmitRequest request)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/pcrNgs/primerDesign/sheets/submit");
        template.postForLocation(url, request);
    }
}
