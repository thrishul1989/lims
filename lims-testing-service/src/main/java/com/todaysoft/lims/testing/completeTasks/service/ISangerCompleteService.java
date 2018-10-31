package com.todaysoft.lims.testing.completeTasks.service;

import com.todaysoft.lims.testing.completeTasks.model.sanger.DataPcrSheet;
import com.todaysoft.lims.testing.completeTasks.model.sanger.FirstPcrSheet;
import com.todaysoft.lims.testing.completeTasks.model.sanger.PrimerDesignSheet;

public interface ISangerCompleteService
{
    PrimerDesignSheet getPrimerDesignSheet(String id);
    
    FirstPcrSheet getFirstPcrSheet(String id);
    
    DataPcrSheet getDataPcrSheet(String id);
}
