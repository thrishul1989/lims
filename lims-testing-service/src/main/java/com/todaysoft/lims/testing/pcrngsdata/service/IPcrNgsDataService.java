package com.todaysoft.lims.testing.pcrngsdata.service;


import com.todaysoft.lims.testing.base.model.VariableModel;
import com.todaysoft.lims.testing.pcrngsdata.dao.PcrNgsDataAssignableTaskSearcher;
import com.todaysoft.lims.testing.pcrngsdata.model.*;

import java.util.List;


public interface IPcrNgsDataService
{
    List<PcrNgsDataTask> getAssignableList(PcrNgsDataAssignableTaskSearcher searcher);
    
    PcrNgsDataAssignModel getAssignableModel(PcrNgsDataAssignArgs args);
    
    void assign(PcrNgsDataAssignRequest request, String token);
    
    PcrNgsDataSheetModel getTestingSheet(String id);
    
    void submit(PcrNgsDataSubmitRequest request, String token,VariableModel model);

    PcrNgsDataTaskVariables getTaskRunningVariables(String taskId);

}
