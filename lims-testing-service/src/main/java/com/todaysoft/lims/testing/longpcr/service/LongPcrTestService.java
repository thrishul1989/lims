package com.todaysoft.lims.testing.longpcr.service;

import java.util.List;

import com.todaysoft.lims.testing.longpcr.model.LongAssignSheet;
import com.todaysoft.lims.testing.longpcr.model.LongPcrAssignTask;
import com.todaysoft.lims.testing.longpcr.model.LongPcrAssignableTaskSearcher;
import com.todaysoft.lims.testing.longpcr.model.LongpcrAssignArgs;
import com.todaysoft.lims.testing.longpcr.model.LongpcrAssignModel;
import com.todaysoft.lims.testing.longpcr.model.LongpcrSubmitModel;
import com.todaysoft.lims.testing.longpcr.model.LongpcrSubmitRequest;

public interface LongPcrTestService
{
    List<LongPcrAssignTask> getAssignableList(LongPcrAssignableTaskSearcher searcher);
    
    List<LongpcrAssignModel> longpcrTestAssignList(LongpcrAssignArgs args);
    
    void assign(LongAssignSheet request,String token);
    
    
    LongpcrSubmitModel getTestingSheet(String id);
    
    void submit(List<LongpcrSubmitRequest> request,String token);
}
