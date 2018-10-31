package com.todaysoft.lims.system.modules.bpm.competeTasks.service;

import com.todaysoft.lims.system.modules.bpm.competeTasks.model.sangerTest.DataPcrSangerSheet;
import com.todaysoft.lims.system.modules.bpm.competeTasks.model.sangerTest.FirstPCRSangerSheet;
import com.todaysoft.lims.system.modules.bpm.secondpcrsanger.model.SecondPCRSangerSheetModel;

public interface ISangerTestCompleteService
{
    FirstPCRSangerSheet getFirstPCRSangerSheet(String id);
    
    SecondPCRSangerSheetModel getSecondPCRSangerSheetModel(String id);
    
    DataPcrSangerSheet getDataPcrSangerSheet(String id);
}
