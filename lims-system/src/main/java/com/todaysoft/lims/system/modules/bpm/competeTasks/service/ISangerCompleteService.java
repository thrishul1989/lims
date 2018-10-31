package com.todaysoft.lims.system.modules.bpm.competeTasks.service;

import com.todaysoft.lims.system.modules.bpm.competeTasks.model.sanger.DataPcrSheet;
import com.todaysoft.lims.system.modules.bpm.competeTasks.model.sanger.FirstPcrSheet;
import com.todaysoft.lims.system.modules.bpm.competeTasks.model.sanger.PrimerDesignSheet;
import com.todaysoft.lims.system.modules.bpm.secondpcr.model.SecondPCRSheetModel;

public interface ISangerCompleteService
{
    PrimerDesignSheet getPrimerDesignSheet(String id);
    
    FirstPcrSheet getFirstPcrSheet(String id);
    
    SecondPCRSheetModel getSecondPCRSubmitModel(String id);
    
    DataPcrSheet getDataPcrSheet(String id);
}
