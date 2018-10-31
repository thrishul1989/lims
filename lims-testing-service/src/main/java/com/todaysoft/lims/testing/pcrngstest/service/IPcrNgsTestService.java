package com.todaysoft.lims.testing.pcrngstest.service;


import com.todaysoft.lims.testing.pcrngstest.dao.PcrNgsTestAssignableTaskSearcher;
import com.todaysoft.lims.testing.pcrngstest.model.*;

import java.util.List;


public interface IPcrNgsTestService
{
    List<PcrNgsTestTask> getAssignableList(PcrNgsTestAssignableTaskSearcher searcher);
    
    PcrNgsTestAssignModel getAssignableModel(PcrNgsTestAssignArgs args);
    
    void assign(PcrNgsTestAssignRequest request, String token);
    
    PcrNgsTestSheetModel getTestingSheet(String id);
    
    void submit(PcrNgsTestSubmitRequest request, String token);

}
