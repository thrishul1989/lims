package com.todaysoft.lims.testing.secondpcr.service;


import com.todaysoft.lims.testing.secondpcr.dao.SecondPcrAssignableTaskSearcher;
import com.todaysoft.lims.testing.secondpcr.model.*;

import java.util.List;


public interface ISecondPcrService
{
    List<SecondPcrTask> getAssignableList(SecondPcrAssignableTaskSearcher searcher);
    
    SecondPcrAssignModel getAssignableModel(SecondPcrAssignArgs args);
    
    void assign(SecondPcrAssignRequest request, String token);
    
    SecondPcrSheetModel getTestingSheet(String id);
    
    void submit(SecondPcrSubmitRequest request, String token);

    SecondPcrTaskVariables getTaskRunningVariables(String taskId);

}
