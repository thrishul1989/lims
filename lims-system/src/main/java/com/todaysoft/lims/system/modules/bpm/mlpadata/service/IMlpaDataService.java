package com.todaysoft.lims.system.modules.bpm.mlpadata.service;

import java.util.List;

import com.todaysoft.lims.system.modules.bpm.mlpadata.model.MlpaDataAssignArgs;
import com.todaysoft.lims.system.modules.bpm.mlpadata.model.MlpaDataAssignRequest;
import com.todaysoft.lims.system.modules.bpm.mlpadata.model.MlpaDataAssignableTaskSearcher;
import com.todaysoft.lims.system.modules.bpm.mlpadata.model.MlpaDataSheetModel;
import com.todaysoft.lims.system.modules.bpm.mlpadata.model.MlpaDataSubmitSheetModel;
import com.todaysoft.lims.system.modules.bpm.mlpadata.model.MlpaDataTask;

public interface IMlpaDataService
{
    List<MlpaDataTask> getMlpaDataAssignableList(MlpaDataAssignableTaskSearcher searcher);
    
    List<MlpaDataTask> getMlpaDataAssignModel(MlpaDataAssignArgs args);
    
    void assignMlpaData(MlpaDataAssignRequest data);
    
    MlpaDataSheetModel getMlpaDataSubmitModel(String id);
    
    void submitSheet(MlpaDataSubmitSheetModel request);
    
    MlpaDataSheetModel exportAnalySheet(String id);
}
