package com.todaysoft.lims.system.modules.bpm.pcrngstest.service;

import java.util.List;

import com.todaysoft.lims.system.modules.bpm.pcrngstest.model.*;

public interface IPcrNgsTestService
{
    
    List<PcrNgsTestTask> getPcrNgsTestAssignableList(PcrNgsTestAssignableTaskSearcher searcher);
    
    PcrNgsTestAssignModel getPcrNgsTestAssignModel(PcrNgsTestAssignArgs args);
    
    void assignPcrNgsTest(PcrNgsTestAssignRequest data);
    
    PcrNgsTestSheetModel getPcrNgsTestSubmitModel(String id);
    
    void submitPcrNgsTest(PcrNgsTestSubmitRequest request);
}
