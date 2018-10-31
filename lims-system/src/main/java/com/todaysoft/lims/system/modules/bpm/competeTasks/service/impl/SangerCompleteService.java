package com.todaysoft.lims.system.modules.bpm.competeTasks.service.impl;

import org.springframework.stereotype.Service;

import com.todaysoft.lims.system.modules.bpm.competeTasks.model.sanger.DataPcrSheet;
import com.todaysoft.lims.system.modules.bpm.competeTasks.model.sanger.FirstPcrSheet;
import com.todaysoft.lims.system.modules.bpm.competeTasks.model.sanger.PrimerDesignSheet;
import com.todaysoft.lims.system.modules.bpm.competeTasks.service.ISangerCompleteService;
import com.todaysoft.lims.system.modules.bpm.secondpcr.model.SecondPCRSheetModel;
import com.todaysoft.lims.system.service.impl.GatewayService;
import com.todaysoft.lims.system.service.impl.RestService;

@Service
public class SangerCompleteService extends RestService implements ISangerCompleteService
{
    @Override
    public PrimerDesignSheet getPrimerDesignSheet(String id)
    {
        String url = GatewayService.getServiceUrl("/bpm/completeTasks/sanger/primerdesign/sheet/{id}");
        return template.getForObject(url, PrimerDesignSheet.class, id);
    }
    
    @Override
    public FirstPcrSheet getFirstPcrSheet(String id)
    {
        String url = GatewayService.getServiceUrl("/bpm/completeTasks/sanger/firstpcr/sheet/{id}");
        return template.getForObject(url, FirstPcrSheet.class, id);
    }
    
    @Override
    public SecondPCRSheetModel getSecondPCRSubmitModel(String id)
    {
        String url = GatewayService.getServiceUrl("/bpm/testing/secondPcr/sheets/{id}");
        return template.getForObject(url, SecondPCRSheetModel.class, id);
    }
    
    @Override
    public DataPcrSheet getDataPcrSheet(String id)
    {
        String url = GatewayService.getServiceUrl("/bpm/completeTasks/sanger/datapcr/sheet/{id}");
        return template.getForObject(url, DataPcrSheet.class, id);
    }
    
}
