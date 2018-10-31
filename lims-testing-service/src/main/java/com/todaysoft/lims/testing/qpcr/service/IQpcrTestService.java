package com.todaysoft.lims.testing.qpcr.service;

import java.util.List;

import com.todaysoft.lims.testing.base.model.VariableModel;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.todaysoft.lims.testing.base.entity.QpcrVerifyRecord;
import com.todaysoft.lims.testing.dnaext.model.DNAExtractAssignSheet;
import com.todaysoft.lims.testing.firstpcr.model.FirstPcrSubmitRequest;
import com.todaysoft.lims.testing.qpcr.dao.QpcrTestAssignableTaskSearcher;
import com.todaysoft.lims.testing.qpcr.model.QpcrAssignArgs;
import com.todaysoft.lims.testing.qpcr.model.QpcrAssignSheet;
import com.todaysoft.lims.testing.qpcr.model.QpcrSubmitModel;
import com.todaysoft.lims.testing.qpcr.model.QpcrSubmitRequest;
import com.todaysoft.lims.testing.qpcr.model.QpcrTestAssignModel;
import com.todaysoft.lims.testing.qpcr.model.QpcrTestTask;

public interface IQpcrTestService
{
    
    
    List<QpcrTestTask> getAssignableList(QpcrTestAssignableTaskSearcher searcher);
    
    QpcrVerifyRecord get(String id);
    
    QpcrTestAssignModel qpcrTestAssignList( QpcrAssignArgs args);
    
    void assign(QpcrAssignSheet request, String token);
    
    QpcrSubmitModel getTestingSheet( String id);
    
    void submit(QpcrSubmitRequest request, String token,VariableModel model);
}
