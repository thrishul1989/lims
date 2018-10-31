package com.todaysoft.lims.testing.qt.service;

import java.util.List;

import com.todaysoft.lims.testing.qt.dao.QtAssignableTaskSearcher;
import com.todaysoft.lims.testing.qt.model.QtAssignArgs;
import com.todaysoft.lims.testing.qt.model.QtAssignModel;
import com.todaysoft.lims.testing.qt.model.QtAssignRequest;
import com.todaysoft.lims.testing.qt.model.QtSubmitModel;
import com.todaysoft.lims.testing.qt.model.QtSubmitRequest;
import com.todaysoft.lims.testing.qt.model.QtTask;

public interface IQtService
{
    List<QtTask> getAssignableList(QtAssignableTaskSearcher searcher);
    
    QtAssignModel getAssignModel(QtAssignArgs args);
    
    void assign(QtAssignRequest request, String token);
    
    QtSubmitModel getSubmitModel(String id);
    
    void submit(QtSubmitRequest request, String token);
}
