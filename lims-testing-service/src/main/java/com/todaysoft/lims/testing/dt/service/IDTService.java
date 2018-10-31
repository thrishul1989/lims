package com.todaysoft.lims.testing.dt.service;


import com.todaysoft.lims.testing.dt.dao.DTAssignableTaskSearcher;
import com.todaysoft.lims.testing.dt.model.*;

import java.util.List;


public interface IDTService
{
    List<DTTask> getAssignableList(DTAssignableTaskSearcher searcher);
    
    DTAssignModel getAssignableModel(DTAssignArgs args);
    
    void assign(DTAssignRequest request, String token);
    
    DTSheetModel getTestingSheet(String id);
    
    void submit(String id, String token);

}
