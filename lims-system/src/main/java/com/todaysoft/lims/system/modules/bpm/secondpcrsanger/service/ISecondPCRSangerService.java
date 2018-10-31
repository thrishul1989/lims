package com.todaysoft.lims.system.modules.bpm.secondpcrsanger.service;

import java.util.List;

import com.todaysoft.lims.system.modules.bpm.secondpcrsanger.model.*;

public interface ISecondPCRSangerService
{
    
    List<SecondPCRSangerTask> getSecondPCRAssignableList(SecondPCRSangerAssignableTaskSearcher searcher);
    
    SecondPCRSangerAssignModel getSecondPCRAssignModel(SecondPCRSangerAssignArgs args);
    
    void assignSecondPCR(SecondPCRSangerAssignRequest data);
    
    SecondPCRSangerSheetModel getSecondPCRSubmitModel(String id);
    
    void submitSecondPCR(SecondPCRSangerSubmitRequest request);
}
