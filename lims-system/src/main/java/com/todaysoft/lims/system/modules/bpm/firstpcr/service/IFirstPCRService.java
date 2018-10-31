package com.todaysoft.lims.system.modules.bpm.firstpcr.service;

import java.util.List;

import com.todaysoft.lims.system.modules.bpm.firstpcr.model.FirstPCRAssignArgs;
import com.todaysoft.lims.system.modules.bpm.firstpcr.model.FirstPCRAssignModel;
import com.todaysoft.lims.system.modules.bpm.firstpcr.model.FirstPCRAssignRequest;
import com.todaysoft.lims.system.modules.bpm.firstpcr.model.FirstPCRAssignableTaskSearcher;
import com.todaysoft.lims.system.modules.bpm.firstpcr.model.FirstPCRSheetModel;
import com.todaysoft.lims.system.modules.bpm.firstpcr.model.FirstPCRSubmitRequest;
import com.todaysoft.lims.system.modules.bpm.firstpcr.model.FirstPCRTask;

public interface IFirstPCRService
{
    
    List<FirstPCRTask> getFirstPCRAssignableList(FirstPCRAssignableTaskSearcher searcher);
    
    FirstPCRAssignModel getFirstPCRAssignModel(FirstPCRAssignArgs args);
    
    void assignFirstPCR(FirstPCRAssignRequest data);
    
    FirstPCRSheetModel getFirstPCRSubmitModel(String id);
    
    void submitFirstPCR(FirstPCRSubmitRequest request);
}
