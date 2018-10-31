package com.todaysoft.lims.system.modules.bpm.qpcr.service;

import java.util.List;

import com.todaysoft.lims.system.modules.bpm.qpcr.model.QpcrAssignArgs;
import com.todaysoft.lims.system.modules.bpm.qpcr.model.QpcrAssignModel;
import com.todaysoft.lims.system.modules.bpm.qpcr.model.QpcrAssignRequest;
import com.todaysoft.lims.system.modules.bpm.qpcr.model.QpcrAssignableTaskSearcher;
import com.todaysoft.lims.system.modules.bpm.qpcr.model.QpcrSubmitModel;
import com.todaysoft.lims.system.modules.bpm.qpcr.model.QpcrSubmitRequest;
import com.todaysoft.lims.system.modules.bpm.qpcr.model.QpcrTask;

public interface IQpcrService
{
    List<QpcrTask> getQpcrAssignableList(QpcrAssignableTaskSearcher searcher);
    
    QpcrAssignModel getQpcrAssignModel(QpcrAssignArgs args);
    
    void assignQpcr(QpcrAssignRequest request);
    
    QpcrSubmitModel getQpcrSubmitModel(String id);
    
    void submitQpcr(QpcrSubmitRequest request);
}
