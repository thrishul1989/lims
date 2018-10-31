package com.todaysoft.lims.system.modules.bpm.mlpa.service;

import java.util.List;

import com.todaysoft.lims.system.modules.bpm.mlpa.model.MlpaTestAssignArgs;
import com.todaysoft.lims.system.modules.bpm.mlpa.model.MlpaTestAssignModel;
import com.todaysoft.lims.system.modules.bpm.mlpa.model.MlpaTestAssignRequest;
import com.todaysoft.lims.system.modules.bpm.mlpa.model.MlpaTestAssignableTaskSearcher;
import com.todaysoft.lims.system.modules.bpm.mlpa.model.MlpaTestSubmitModel;
import com.todaysoft.lims.system.modules.bpm.mlpa.model.MlpaTestTask;

public interface IMlpaService
{
    List<MlpaTestTask> getMlpaAssignableList(MlpaTestAssignableTaskSearcher searcher);

    MlpaTestAssignModel getMlpaAssignModel(MlpaTestAssignArgs args);
    
    void assignMlpa(MlpaTestAssignRequest request);
    
    MlpaTestSubmitModel getMlpaSubmitModel(String id);
    
    void submitMlpa(MlpaTestSubmitModel model);
}
