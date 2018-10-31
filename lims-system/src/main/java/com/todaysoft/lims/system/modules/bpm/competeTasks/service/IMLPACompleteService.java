package com.todaysoft.lims.system.modules.bpm.competeTasks.service;

import com.todaysoft.lims.system.modules.bpm.competeTasks.model.mlpa.MlpaDataSheet;
import com.todaysoft.lims.system.modules.bpm.mlpa.model.MlpaTestSubmitModel;

public interface IMLPACompleteService
{
    MlpaTestSubmitModel getMlpaSubmitModel(String id);
    
    MlpaDataSheet getMlpaDataSheet(String id);
}
