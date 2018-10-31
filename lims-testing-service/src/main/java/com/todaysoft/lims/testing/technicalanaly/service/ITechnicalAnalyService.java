package com.todaysoft.lims.testing.technicalanaly.service;

import java.util.List;

import com.todaysoft.lims.testing.base.entity.TestingTask;
import com.todaysoft.lims.testing.base.model.VariableModel;
import com.todaysoft.lims.testing.technicalanaly.dao.TechnicalAnalyAssignableTaskSearcher;
import com.todaysoft.lims.testing.technicalanaly.model.AssignVerifyProcessResult;
import com.todaysoft.lims.testing.technicalanaly.model.TechnicalAnalyAssignArgs;
import com.todaysoft.lims.testing.technicalanaly.model.TechnicalAnalyAssignModel;
import com.todaysoft.lims.testing.technicalanaly.model.TechnicalAnalyAssignRequest;
import com.todaysoft.lims.testing.technicalanaly.model.TechnicalAnalySheetSamplesModel;
import com.todaysoft.lims.testing.technicalanaly.model.TechnicalAnalySubmitModel;
import com.todaysoft.lims.testing.technicalanaly.model.TechnicalAnalySubmitRequest;
import com.todaysoft.lims.testing.technicalanaly.model.TechnicalAnalySubmitVerifyRequest;
import com.todaysoft.lims.testing.technicalanaly.model.TechnicalAnalyTask;

public interface ITechnicalAnalyService
{
    List<TechnicalAnalyTask> getAssignableList(TechnicalAnalyAssignableTaskSearcher searcher);
    
    List<TechnicalAnalyTask> getAssignableListDetail(TechnicalAnalyAssignableTaskSearcher searcher);
    
    TechnicalAnalyAssignModel getAssignModel(TechnicalAnalyAssignArgs args);
    
    void assign(TechnicalAnalyAssignRequest request, String token);
    
    TechnicalAnalySubmitModel getSubmitModel(String id);
    
    TechnicalAnalySubmitModel getExportModel(String id);
    
    TechnicalAnalySheetSamplesModel getSamplesModel(String id);
    
    void verify(TechnicalAnalySubmitVerifyRequest request, String token);
    
    List<AssignVerifyProcessResult> submit(TechnicalAnalySubmitRequest request, String token, VariableModel model, List<TestingTask> tasks);
    
    List<TechnicalAnalyTask> getListBySequencingCode(TechnicalAnalyAssignableTaskSearcher searcher);
    
    Object getRecordMethodName(String name);
    
    TestingTask getTestingTaskByChromAndLocation1(String chrom, String location1, String verifyMethod);
}
