package com.todaysoft.lims.system.modules.bpm.service;

import com.todaysoft.lims.system.modules.bpm.model.assign.args.BiAnalysisAssignArgs;
import com.todaysoft.lims.system.modules.bpm.model.assign.args.OnTestingAssignArgs;
import com.todaysoft.lims.system.modules.bpm.model.assign.args.TechnicalAnalysisAssignArgs;
import com.todaysoft.lims.system.modules.bpm.model.assign.model.BiAnalysisAssignModel;
import com.todaysoft.lims.system.modules.bpm.model.assign.model.TechnicalAnalysisAssignModel;

public interface ITestingAssignService
{
//    OnTestingAssignModel getOnTestingAssignModel(OnTestingAssignArgs args);
    
    BiAnalysisAssignModel getBiAnalysisTaskAssignModel(BiAnalysisAssignArgs args);
    
    TechnicalAnalysisAssignModel getTechnicalAnalysisTaskAssignModel(TechnicalAnalysisAssignArgs args);
}
