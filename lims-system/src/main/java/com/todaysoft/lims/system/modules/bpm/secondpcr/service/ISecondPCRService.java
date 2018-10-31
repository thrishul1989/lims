package com.todaysoft.lims.system.modules.bpm.secondpcr.service;

import java.util.List;

import com.todaysoft.lims.system.modules.bpm.secondpcr.model.*;

public interface ISecondPCRService
{
    
    List<SecondPCRTask> getSecondPCRAssignableList(SecondPCRAssignableTaskSearcher searcher);
    
    SecondPCRAssignModel getSecondPCRAssignModel(SecondPCRAssignArgs args);
    
    void assignSecondPCR(SecondPCRAssignRequest data);
    
    SecondPCRSheetModel getSecondPCRSubmitModel(String id);
    
    void submitSecondPCR(SecondPCRSubmitRequest request);
}
