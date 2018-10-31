package com.todaysoft.lims.system.modules.bpm.rqt.service;

import java.util.List;
import java.util.Map;

import com.todaysoft.lims.system.modules.bpm.rqt.model.RQTAssignArgs;
import com.todaysoft.lims.system.modules.bpm.rqt.model.RQTAssignModel;
import com.todaysoft.lims.system.modules.bpm.rqt.model.RQTAssignRequest;
import com.todaysoft.lims.system.modules.bpm.rqt.model.RQTAssignableTaskSearcher;
import com.todaysoft.lims.system.modules.bpm.rqt.model.RQTSheetModel;
import com.todaysoft.lims.system.modules.bpm.rqt.model.RQTSubmitRequest;
import com.todaysoft.lims.system.modules.bpm.rqt.model.RQTTask;

public interface IRQTService
{
    Map<String, List<String>> validateIndex(String ids);
    
    List<RQTTask> getRQTAssignableList(RQTAssignableTaskSearcher searcher);
    
    RQTAssignModel getRQTAssignModel(RQTAssignArgs args);
    
    void assignRQT(RQTAssignRequest data);
    
    RQTSheetModel getRQTSubmitModel(String id);
    
    void submitRQT(RQTSubmitRequest request);
}
