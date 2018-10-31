package com.todaysoft.lims.testing.firstpcr.service;


import com.todaysoft.lims.testing.firstpcr.dao.FirstPcrAssignableTaskSearcher;
import com.todaysoft.lims.testing.firstpcr.model.*;

import java.util.List;


public interface IFirstPcrService
{
    List<FirstPcrTask> getAssignableList(FirstPcrAssignableTaskSearcher searcher);
    
    FirstPcrAssignModel getAssignableModel(FirstPcrAssignArgs args);
    
    void assign(FirstPcrAssignRequest request, String token);
    
    FirstPcrSheetModel getTestingSheet(String id);
    
    void submit(FirstPcrSubmitRequest request, String token);

}
