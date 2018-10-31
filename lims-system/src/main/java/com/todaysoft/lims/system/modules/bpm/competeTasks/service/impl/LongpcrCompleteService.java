package com.todaysoft.lims.system.modules.bpm.competeTasks.service.impl;

import org.springframework.stereotype.Service;

import com.todaysoft.lims.system.modules.bpm.competeTasks.model.longpcr.LongpcrSheet;
import com.todaysoft.lims.system.modules.bpm.competeTasks.model.longpcr.LongqcSheet;
import com.todaysoft.lims.system.modules.bpm.competeTasks.service.ILongpcrCompleteService;
import com.todaysoft.lims.system.service.impl.GatewayService;
import com.todaysoft.lims.system.service.impl.RestService;

@Service
public class LongpcrCompleteService extends RestService implements ILongpcrCompleteService
{
    
    @Override
    public LongpcrSheet getLongpcrSheet(String id)
    {
        String url = GatewayService.getServiceUrl("/bpm/completeTasks/longpcr/longpcr/sheet/{id}");
        return template.getForObject(url, LongpcrSheet.class, id);
    }
    
    @Override
    public LongqcSheet getLongqcSheet(String id)
    {
        String url = GatewayService.getServiceUrl("/bpm/completeTasks/longpcr/longqc/sheet/{id}");
        return template.getForObject(url, LongqcSheet.class, id);
    }
    
}
