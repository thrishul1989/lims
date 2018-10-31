package com.todaysoft.lims.testing.firstpcrsanger.service;


import com.todaysoft.lims.testing.firstpcrsanger.dao.FirstPcrSangerAssignableTaskSearcher;
import com.todaysoft.lims.testing.firstpcrsanger.model.*;

import java.util.List;


public interface IFirstPcrSangerService
{
    List<FirstPcrSangerTask> getAssignableList(FirstPcrSangerAssignableTaskSearcher searcher);
    
    FirstPcrSangerAssignModel getAssignableModel(FirstPcrSangerAssignArgs args);
    
    void assign(FirstPcrSangerAssignRequest request, String token);
    
    FirstPcrSangerSheetModel getTestingSheet(String id);
    
    void submit(FirstPcrSangerSubmitRequest request, String token);

}
