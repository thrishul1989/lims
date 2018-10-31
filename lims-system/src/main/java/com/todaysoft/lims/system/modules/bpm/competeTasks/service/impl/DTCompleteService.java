package com.todaysoft.lims.system.modules.bpm.competeTasks.service.impl;

import org.springframework.stereotype.Service;

import com.todaysoft.lims.system.modules.bpm.competeTasks.model.dt.DtDataSheet;
import com.todaysoft.lims.system.modules.bpm.competeTasks.service.IDTCompleteService;
import com.todaysoft.lims.system.modules.bpm.dt.model.DTSubmitModel;
import com.todaysoft.lims.system.service.impl.GatewayService;
import com.todaysoft.lims.system.service.impl.RestService;

@Service
public class DTCompleteService extends RestService implements IDTCompleteService
{
    
    @Override
    public DTSubmitModel getDTSubmitModel(String id)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/dt/sheets/{id}");
        return template.getForObject(url, DTSubmitModel.class, id);
    }
    
    @Override
    public DtDataSheet getDtDataSheet(String id)
    {
        String url = GatewayService.getServiceUrl("/bpm/completeTasks/dt/data/sheet/{id}");
        return template.getForObject(url, DtDataSheet.class, id);
    }
    
}
