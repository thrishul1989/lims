package com.todaysoft.lims.system.modules.bpm.pcrngsdata.service;

import java.util.List;

import com.todaysoft.lims.system.modules.bpm.pcrngsdata.model.*;

public interface IPcrNgsDataService
{
    
    List<PcrNgsDataTask> getDataPcrAssignableList(PcrNgsDataAssignableTaskSearcher searcher);
    
    PcrNgsDataAssignModel getDataPcrAssignModel(PcrNgsDataAssignArgs args);
    
    void assignDataPcr(PcrNgsDataAssignRequest data);
    
    PcrNgsDataSheetModel getDataPcrSubmitModel(String id);
    
    void submitDataPcr(PcrNgsDataSubmitRequest request);
}
