package com.todaysoft.lims.testing.ontesting.service;

import com.todaysoft.lims.testing.ontesting.dao.SequencingAssignableTaskSearcher;
import com.todaysoft.lims.testing.ontesting.model.*;

import java.util.List;


public interface ISequencingService
{
    List<SequencingTask> getAssignableList(SequencingAssignableTaskSearcher searcher);
    
    SequencingAssignModel getAssignModel(String id);
    
    void assign(SequencingAssignRequest request, String token);
    
    SequencingTaskVariables getTaskRunningVariables(String taskId);
    
    SequencingSubmitModel getSubmitModel(String id);
    
    void submit(SequencingSubmitRequest request, String token);
}
