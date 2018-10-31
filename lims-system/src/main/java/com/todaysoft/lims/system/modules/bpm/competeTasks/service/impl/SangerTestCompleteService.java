package com.todaysoft.lims.system.modules.bpm.competeTasks.service.impl;

import org.springframework.stereotype.Service;

import com.todaysoft.lims.system.modules.bpm.competeTasks.model.sangerTest.DataPcrSangerSheet;
import com.todaysoft.lims.system.modules.bpm.competeTasks.model.sangerTest.FirstPCRSangerSheet;
import com.todaysoft.lims.system.modules.bpm.competeTasks.service.ISangerTestCompleteService;
import com.todaysoft.lims.system.modules.bpm.secondpcrsanger.model.SecondPCRSangerSheetModel;
import com.todaysoft.lims.system.service.impl.GatewayService;
import com.todaysoft.lims.system.service.impl.RestService;

@Service
public class SangerTestCompleteService extends RestService implements ISangerTestCompleteService
{
    @Override
    public FirstPCRSangerSheet getFirstPCRSangerSheet(String id)
    {
        String url = GatewayService.getServiceUrl("/bpm/completeTasks/sangerTest/firstpcrSanger/sheet/{id}");
        return template.getForObject(url, FirstPCRSangerSheet.class, id);
    }
    
    @Override
    public SecondPCRSangerSheetModel getSecondPCRSangerSheetModel(String id)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/secondPcrSanger/sheets/{id}");
        return template.getForObject(url, SecondPCRSangerSheetModel.class, id);
    }
    
    @Override
    public DataPcrSangerSheet getDataPcrSangerSheet(String id)
    {
        String url = GatewayService.getServiceUrl("/bpm/completeTasks/sangerTest/datapcrSanger/sheet/{id}");
        return template.getForObject(url, DataPcrSangerSheet.class, id);
    }
    
}
