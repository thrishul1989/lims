package com.todaysoft.lims.system.modules.bpm.competeTasks.service;

import com.todaysoft.lims.system.modules.bpm.competeTasks.model.pcrngs.PcrNgsDataSheet;
import com.todaysoft.lims.system.modules.bpm.competeTasks.model.pcrngs.PcrNgsPrimerDesignSheet;
import com.todaysoft.lims.system.modules.bpm.competeTasks.model.pcrngs.PcrNgsTestSheet;

public interface IPcrNgsCompleteService
{
    PcrNgsPrimerDesignSheet getPcrNgsPrimerDesignSheet(String id);
    
    PcrNgsTestSheet getPcrNgsTestSheet(String id);
    
    PcrNgsDataSheet getPcrNgsDataSheet(String id);
}
