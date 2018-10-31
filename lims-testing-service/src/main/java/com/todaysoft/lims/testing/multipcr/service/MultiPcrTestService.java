package com.todaysoft.lims.testing.multipcr.service;

import java.util.List;

import com.todaysoft.lims.testing.multipcr.model.MultiAssignSheet;
import com.todaysoft.lims.testing.multipcr.model.MultiPcrAssignTask;
import com.todaysoft.lims.testing.multipcr.model.MultiPcrAssignableTaskSearcher;
import com.todaysoft.lims.testing.multipcr.model.MultipcrAssignArgs;
import com.todaysoft.lims.testing.multipcr.model.MultipcrAssignModel;
import com.todaysoft.lims.testing.multipcr.model.MultipcrSubmitModel;
import com.todaysoft.lims.testing.multipcr.model.MultipcrSubmitSheet;

public interface MultiPcrTestService
{
    List<MultiPcrAssignTask> getAssignableList(MultiPcrAssignableTaskSearcher searcher);
    
    List<MultipcrAssignModel> MultipcrTestAssignList(MultipcrAssignArgs args);
    
    void assign(MultiAssignSheet request, String token);
    
    void submit(MultipcrSubmitSheet request, String token);
    
    MultipcrSubmitModel getTestingSheet(String id);
    
}
