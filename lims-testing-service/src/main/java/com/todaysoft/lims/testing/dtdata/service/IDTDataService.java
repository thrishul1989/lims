package com.todaysoft.lims.testing.dtdata.service;

import java.util.List;

import com.todaysoft.lims.testing.base.model.VariableModel;
import com.todaysoft.lims.testing.dtdata.dao.DTDataAssignableTaskSearcher;
import com.todaysoft.lims.testing.dtdata.model.DTDataAssignArgs;
import com.todaysoft.lims.testing.dtdata.model.DTDataAssignModel;
import com.todaysoft.lims.testing.dtdata.model.DTDataAssignRequest;
import com.todaysoft.lims.testing.dtdata.model.DTDataSheetModel;
import com.todaysoft.lims.testing.dtdata.model.DTDataSubmitRequest;
import com.todaysoft.lims.testing.dtdata.model.DTDataTask;
import com.todaysoft.lims.testing.dtdata.model.DTDataTaskVariables;

public interface IDTDataService
{
    List<DTDataTask> getAssignableList(DTDataAssignableTaskSearcher searcher);
    
    DTDataAssignModel getAssignableModel(DTDataAssignArgs args);
    
    void assign(DTDataAssignRequest request, String token);
    
    DTDataSheetModel getTestingSheet(String id);
    
    void submit(DTDataSubmitRequest request, String token, VariableModel model);
    
    DTDataTaskVariables getTaskRunningVariables(String taskId);
    
    DTDataSheetModel getAnalysModel(String id);
}
