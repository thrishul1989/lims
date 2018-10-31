package com.todaysoft.lims.system.modules.bpm.competeTasks.service.impl;

import org.springframework.stereotype.Service;

import com.todaysoft.lims.system.modules.bpm.competeTasks.model.mlpa.MlpaDataSheet;
import com.todaysoft.lims.system.modules.bpm.competeTasks.service.IMLPACompleteService;
import com.todaysoft.lims.system.modules.bpm.mlpa.model.MlpaTestSubmitModel;
import com.todaysoft.lims.system.service.impl.GatewayService;
import com.todaysoft.lims.system.service.impl.RestService;

@Service
public class MLPACompleteService extends RestService implements IMLPACompleteService
{
    
    @Override
    public MlpaTestSubmitModel getMlpaSubmitModel(String id)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/mlpaTest/sheets/{id}");
        return template.getForObject(url, MlpaTestSubmitModel.class, id);
    }
    
    @Override
    public MlpaDataSheet getMlpaDataSheet(String id)
    {
        String url = GatewayService.getServiceUrl("/bpm/completeTasks/mlpa/data/sheet/{id}");
        return template.getForObject(url, MlpaDataSheet.class, id);
    }
    
}
