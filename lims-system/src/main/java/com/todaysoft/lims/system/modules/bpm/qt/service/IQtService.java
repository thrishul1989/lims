package com.todaysoft.lims.system.modules.bpm.qt.service;

import java.util.List;

import com.todaysoft.lims.system.modules.bpm.qt.model.QtAssignArgs;
import com.todaysoft.lims.system.modules.bpm.qt.model.QtAssignModel;
import com.todaysoft.lims.system.modules.bpm.qt.model.QtAssignRequest;
import com.todaysoft.lims.system.modules.bpm.qt.model.QtAssignableTaskSearcher;
import com.todaysoft.lims.system.modules.bpm.qt.model.QtSubmitModel;
import com.todaysoft.lims.system.modules.bpm.qt.model.QtSubmitRequest;
import com.todaysoft.lims.system.modules.bpm.qt.model.QtTask;

public interface IQtService
{
    List<QtTask> getAssignableList(QtAssignableTaskSearcher searcher);
    
    QtAssignModel getAssignModel(QtAssignArgs args);
    
    void assign(QtAssignRequest request);
    
    QtSubmitModel getSubmitModel(String id);
    
    void submit(QtSubmitRequest request);
}
