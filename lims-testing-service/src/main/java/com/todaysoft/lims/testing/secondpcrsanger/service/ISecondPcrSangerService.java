package com.todaysoft.lims.testing.secondpcrsanger.service;


import com.todaysoft.lims.testing.secondpcrsanger.dao.SecondPcrSangerAssignableTaskSearcher;
import com.todaysoft.lims.testing.secondpcrsanger.model.*;

import java.util.List;


public interface ISecondPcrSangerService
{
    List<SecondPcrSangerTask> getAssignableList(SecondPcrSangerAssignableTaskSearcher searcher);
    
    SecondPcrSangerAssignModel getAssignableModel(SecondPcrSangerAssignArgs args);
    
    void assign(SecondPcrSangerAssignRequest request, String token);
    
    SecondPcrSangerSheetModel getTestingSheet(String id);
    
    void submit(SecondPcrSangerSubmitRequest request, String token);

    SecondPcrSangerTaskVariables getTaskRunningVariables(String taskId);

}
