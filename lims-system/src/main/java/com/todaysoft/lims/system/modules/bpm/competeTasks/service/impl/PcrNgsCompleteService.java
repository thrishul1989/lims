package com.todaysoft.lims.system.modules.bpm.competeTasks.service.impl;

import org.springframework.stereotype.Service;

import com.todaysoft.lims.system.modules.bpm.competeTasks.model.pcrngs.PcrNgsDataSheet;
import com.todaysoft.lims.system.modules.bpm.competeTasks.model.pcrngs.PcrNgsPrimerDesignSheet;
import com.todaysoft.lims.system.modules.bpm.competeTasks.model.pcrngs.PcrNgsTestSheet;
import com.todaysoft.lims.system.modules.bpm.competeTasks.service.IPcrNgsCompleteService;
import com.todaysoft.lims.system.service.impl.GatewayService;
import com.todaysoft.lims.system.service.impl.RestService;

@Service
public class PcrNgsCompleteService extends RestService implements IPcrNgsCompleteService
{
    
    @Override
    public PcrNgsPrimerDesignSheet getPcrNgsPrimerDesignSheet(String id)
    {
        String url = GatewayService.getServiceUrl("/bpm/completeTasks/pcrngs/pcrngsprimerdesign/sheet/{id}");
        return template.getForObject(url, PcrNgsPrimerDesignSheet.class, id);
    }
    
    @Override
    public PcrNgsTestSheet getPcrNgsTestSheet(String id)
    {
        String url = GatewayService.getServiceUrl("/bpm/completeTasks/pcrngs/pcrngstest/sheet/{id}");
        return template.getForObject(url, PcrNgsTestSheet.class, id);
    }
    
    @Override
    public PcrNgsDataSheet getPcrNgsDataSheet(String id)
    {
        String url = GatewayService.getServiceUrl("/bpm/completeTasks/pcrngs/pcrngsdata/sheet/{id}");
        return template.getForObject(url, PcrNgsDataSheet.class, id);
    }
}
