package com.todaysoft.lims.system.service;

import java.util.List;

import com.todaysoft.lims.system.model.vo.TestingSheetJddlModel;
import com.todaysoft.lims.system.model.vo.testingtask.TestingSheetCreateRequest;
import com.todaysoft.lims.system.model.vo.testingtask.TestingTaskAssign;
import com.todaysoft.lims.system.model.vo.testingtask.TestingTaskCreateRequest;
import com.todaysoft.lims.system.modules.bcm.model.TestingMethod;
import com.todaysoft.lims.system.modules.bpm.samplestock.model.TestingTask;
import com.todaysoft.lims.system.service.adapter.request.testingtask.TestingTaskDetailRequest;

public interface ITestingTaskService
{
    
    TestingTask get(String id);
    
    TestingTaskAssign paging(TestingTaskDetailRequest searcher);
    
    Integer create(TestingTaskCreateRequest request);
    
    Integer createRNA(TestingTaskCreateRequest request);
    
    Integer create(TestingSheetCreateRequest request);
    
    TestingTaskAssign pagingDnaQc(TestingTaskDetailRequest searcher);
    
    TestingTaskAssign pagingLibraryCreate(TestingTaskDetailRequest searcher);
    
    TestingTaskAssign pagingWKQC(TestingTaskDetailRequest searcher);
    
    TestingTaskAssign pagingLibraryCatch(TestingTaskDetailRequest searcher);
    
    TestingTaskAssign pagingXddl(TestingTaskDetailRequest searcher);
    
    TestingTaskAssign pagingJddl(TestingTaskDetailRequest searcher);
    
    TestingTaskAssign pagingOnTest(TestingTaskDetailRequest searcher);
    
    TestingTaskAssign pagingBioInfo(TestingTaskDetailRequest searcher);
    
    TestingTaskAssign pagingTecAnalysis(TestingTaskDetailRequest searcher);
    
    TestingTaskAssign pagingReportCreate(TestingTaskDetailRequest searcher);
    
    TestingTaskAssign pagingReportCheck(TestingTaskDetailRequest searcher);
    
    TestingTaskAssign pagingReportMail(TestingTaskDetailRequest searcher);
    
    Boolean createDNAQC(TestingSheetCreateRequest request);
    
    Boolean createLibrary(TestingSheetCreateRequest request);
    
    Boolean createWKQC(TestingSheetCreateRequest request);
    
    Boolean createLibraryCatch(TestingSheetCreateRequest request);
    
    Boolean createXddl(TestingSheetCreateRequest request);
    
    Boolean createJddl(TestingSheetCreateRequest request);
    
    Boolean createOnTest(TestingSheetCreateRequest request);
    
    Boolean createBioInfo(TestingSheetCreateRequest request);
    
    Boolean createTecAnalysis(TestingSheetCreateRequest request);
    
    Boolean createReportCreate(TestingSheetCreateRequest request);
    
    Boolean createReportCheck(TestingSheetCreateRequest request);
    
    Boolean createReportMail(TestingSheetCreateRequest request);
    
    String getSampleLocation(String containerType);
    
    List<TestingSheetJddlModel> getJddlList();
    
    TestingSheetCreateRequest doSome(TestingSheetCreateRequest request);
    
    boolean validateCatchCode(TestingSheetCreateRequest request);
    
    TestingMethod getTestingMethodBySemantic(String semantic);
}
