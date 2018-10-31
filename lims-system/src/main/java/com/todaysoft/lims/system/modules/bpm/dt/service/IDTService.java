package com.todaysoft.lims.system.modules.bpm.dt.service;

import java.util.List;

import com.todaysoft.lims.system.modules.bpm.dt.model.*;

public interface IDTService
{
    List<DTTask> getDTAssignableList(DTAssignableTaskSearcher searcher);

    DTAssignModel getDTAssignModel(DTAssignArgs args);
    
    void assignDT(DTAssignRequest request);
    
    DTSubmitModel getDTSubmitModel(String id);
    
    void submitDT(DTSubmitModel model);
}
