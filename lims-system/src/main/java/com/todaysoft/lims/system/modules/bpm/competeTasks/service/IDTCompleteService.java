package com.todaysoft.lims.system.modules.bpm.competeTasks.service;

import com.todaysoft.lims.system.modules.bpm.competeTasks.model.dt.DtDataSheet;
import com.todaysoft.lims.system.modules.bpm.dt.model.DTSubmitModel;

public interface IDTCompleteService
{
    DTSubmitModel getDTSubmitModel(String id);
    
    DtDataSheet getDtDataSheet(String id);
}
