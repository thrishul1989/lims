package com.todaysoft.lims.system.modules.bpm.firstpcrsanger.service;

import java.util.List;

import com.todaysoft.lims.system.modules.bpm.firstpcrsanger.model.*;

public interface IFirstPCRSangerService
{
    
    List<FirstPCRSangerTask> getFirstPCRAssignableList(FirstPCRSangerAssignableTaskSearcher searcher);
    
    FirstPCRSangerAssignModel getFirstPCRAssignModel(FirstPCRSangerAssignArgs args);
    
    void assignFirstPCR(FirstPCRSangerAssignRequest data);
    
    FirstPCRSangerSheetModel getFirstPCRSubmitModel(String id);
    
    void submitFirstPCR(FirstPCRSangerSubmitRequest request);
}
