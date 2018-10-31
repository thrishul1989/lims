package com.todaysoft.lims.testing.mlpatest.service;


import com.todaysoft.lims.testing.base.entity.TestingMlpaTask;
import com.todaysoft.lims.testing.mlpatest.dao.MlpaTestAssignableTaskSearcher;
import com.todaysoft.lims.testing.mlpatest.model.*;

import java.util.List;


public interface IMlpaTestService
{
    List<MlpaTestTask> getAssignableList(MlpaTestAssignableTaskSearcher searcher);
    
    MlpaTestAssignModel getAssignableModel(MlpaTestAssignArgs args);
    
    void assign(MlpaTestAssignRequest request, String token);
    
    MlpaTestSheetModel getTestingSheet(String id);
    
    void submit(String id, String token);

}
