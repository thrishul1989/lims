package com.todaysoft.lims.system.modules.bpm.competeTasks.service;

import com.todaysoft.lims.system.modules.bpm.competeTasks.model.fluo.FluoAnalyseSheet;
import com.todaysoft.lims.system.modules.bpm.fluotest.model.FluoTestSubmitModel;

public interface IFluoCompleteService
{
    FluoTestSubmitModel getFluoTestSubmitModel(String id);
    
    FluoAnalyseSheet getFluoAnalyseSheet(String id);
}
