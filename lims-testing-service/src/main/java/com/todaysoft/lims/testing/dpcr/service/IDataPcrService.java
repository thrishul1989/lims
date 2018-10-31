package com.todaysoft.lims.testing.dpcr.service;


import com.todaysoft.lims.testing.base.model.VariableModel;
import com.todaysoft.lims.testing.dpcr.dao.DataPcrAssignableTaskSearcher;
import com.todaysoft.lims.testing.dpcr.model.*;

import java.util.List;


public interface IDataPcrService
{
    List<DataPcrTask> getAssignableList(DataPcrAssignableTaskSearcher searcher,int searchType);
    
    DataPcrAssignModel getAssignableModel(DataPcrAssignArgs args);
    
    void assign(DataPcrAssignRequest request, String token);
    
    DataPcrSheetModel getTestingSheet(String id);
    
    void submit(DataPcrSubmitRequest request, String token,VariableModel model);

    DataPcrTaskVariables getTaskRunningVariables(String taskId);

}
