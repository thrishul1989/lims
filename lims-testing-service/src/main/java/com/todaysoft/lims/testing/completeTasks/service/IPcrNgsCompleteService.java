package com.todaysoft.lims.testing.completeTasks.service;

import com.todaysoft.lims.testing.completeTasks.model.pcrngs.PcrNgsDataSheet;
import com.todaysoft.lims.testing.completeTasks.model.pcrngs.PcrNgsPrimerDesignSheet;
import com.todaysoft.lims.testing.completeTasks.model.pcrngs.PcrNgsTestSheet;

public interface IPcrNgsCompleteService
{
    PcrNgsPrimerDesignSheet getPcrNgsPrimerDesignSheet(String id);
    
    PcrNgsTestSheet getPcrNgsTestSheet(String id);
    
    PcrNgsDataSheet getPcrNgsDataSheet(String id);
}
