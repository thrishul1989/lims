package com.todaysoft.lims.system.modules.bpm.competeTasks.service.impl;

import org.springframework.stereotype.Service;

import com.todaysoft.lims.system.modules.bpm.competeTasks.model.qpcr.QpcrSheet;
import com.todaysoft.lims.system.modules.bpm.competeTasks.service.IQpcrCompleteService;
import com.todaysoft.lims.system.service.impl.GatewayService;
import com.todaysoft.lims.system.service.impl.RestService;

@Service
public class QpcrCompleteService extends RestService implements IQpcrCompleteService
{
    
    @Override
    public QpcrSheet getQpcrSheet(String id)
    {
        String url = GatewayService.getServiceUrl("/bpm/completeTasks/qpcr/sheet/{id}");
        return template.getForObject(url, QpcrSheet.class, id);
    }
    
}
