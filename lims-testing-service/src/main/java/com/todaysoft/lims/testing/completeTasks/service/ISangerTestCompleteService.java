package com.todaysoft.lims.testing.completeTasks.service;

import com.todaysoft.lims.testing.completeTasks.model.sangerTest.DataPcrSangerSheet;
import com.todaysoft.lims.testing.completeTasks.model.sangerTest.FirstPCRSangerSheet;

public interface ISangerTestCompleteService
{
    FirstPCRSangerSheet getFirstPCRSangerSheet(String id);
    
    DataPcrSangerSheet getDataPcrSangerSheet(String id);
}
