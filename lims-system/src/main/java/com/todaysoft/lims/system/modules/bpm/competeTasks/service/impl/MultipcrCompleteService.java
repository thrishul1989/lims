package com.todaysoft.lims.system.modules.bpm.competeTasks.service.impl;

import org.springframework.stereotype.Service;

import com.todaysoft.lims.system.modules.bpm.competeTasks.model.multipcr.MultipcrQcSheet;
import com.todaysoft.lims.system.modules.bpm.competeTasks.service.IMultipcrCompleteService;
import com.todaysoft.lims.system.modules.bpm.multipcr.model.MultipcrSubmitModel;
import com.todaysoft.lims.system.service.impl.GatewayService;
import com.todaysoft.lims.system.service.impl.RestService;

@Service
public class MultipcrCompleteService extends RestService implements IMultipcrCompleteService
{
    
    @Override
    public MultipcrSubmitModel getMultipcrSubmitModel(String id)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/multipcr/sheets/{id}");
        return template.getForObject(url, MultipcrSubmitModel.class, id);
    }
    
    @Override
    public MultipcrQcSheet getMultipcrQcSheet(String id)
    {
        String url = GatewayService.getServiceUrl("/bpm/completeTasks/multipcr/sheet/{id}");
        return template.getForObject(url, MultipcrQcSheet.class, id);
    }
    
}
