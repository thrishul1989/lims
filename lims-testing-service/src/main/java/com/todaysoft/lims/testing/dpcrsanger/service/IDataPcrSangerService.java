package com.todaysoft.lims.testing.dpcrsanger.service;

import java.util.List;

import com.todaysoft.lims.testing.base.model.VariableModel;
import com.todaysoft.lims.testing.dpcrsanger.dao.DataPcrSangerAssignableTaskSearcher;
import com.todaysoft.lims.testing.dpcrsanger.model.DataPcrSangerAssignArgs;
import com.todaysoft.lims.testing.dpcrsanger.model.DataPcrSangerAssignModel;
import com.todaysoft.lims.testing.dpcrsanger.model.DataPcrSangerAssignRequest;
import com.todaysoft.lims.testing.dpcrsanger.model.DataPcrSangerSheetModel;
import com.todaysoft.lims.testing.dpcrsanger.model.DataPcrSangerSubmitRequest;
import com.todaysoft.lims.testing.dpcrsanger.model.DataPcrSangerTask;
import com.todaysoft.lims.testing.dpcrsanger.model.DataPcrTaskVariables;

public interface IDataPcrSangerService
{
    List<DataPcrSangerTask> getAssignableList(DataPcrSangerAssignableTaskSearcher searcher, int searchType);
    
    DataPcrSangerAssignModel getAssignableModel(DataPcrSangerAssignArgs args);
    
    void assign(DataPcrSangerAssignRequest request, String token);
    
    DataPcrSangerSheetModel getTestingSheet(String id);
    
    void submit(DataPcrSangerSubmitRequest request, String token, VariableModel model);
    
    DataPcrTaskVariables getTaskRunningVariables(String taskId);
    
    DataPcrSangerSheetModel getAnalysModel(String id);
}
