package com.todaysoft.lims.system.modules.bpm.competeTasks.service.impl;

import org.springframework.stereotype.Service;

import com.todaysoft.lims.system.modules.bpm.competeTasks.model.fluo.FluoAnalyseSheet;
import com.todaysoft.lims.system.modules.bpm.competeTasks.service.IFluoCompleteService;
import com.todaysoft.lims.system.modules.bpm.fluotest.model.FluoTestSubmitModel;
import com.todaysoft.lims.system.service.impl.GatewayService;
import com.todaysoft.lims.system.service.impl.RestService;

@Service
public class FluoCompleteService extends RestService implements IFluoCompleteService
{
    
    @Override
    public FluoTestSubmitModel getFluoTestSubmitModel(String id)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/fluoTest/sheets/{id}");
        return template.getForObject(url, FluoTestSubmitModel.class, id);
    }
    
    @Override
    public FluoAnalyseSheet getFluoAnalyseSheet(String id)
    {
        String url = GatewayService.getServiceUrl("/bpm/completeTasks/fluo/fluoAnalyse/sheet/{id}");
        return template.getForObject(url, FluoAnalyseSheet.class, id);
    }
    
}
