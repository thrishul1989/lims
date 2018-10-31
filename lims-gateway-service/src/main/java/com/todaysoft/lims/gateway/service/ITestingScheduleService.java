package com.todaysoft.lims.gateway.service;


import java.util.List;

import com.todaysoft.lims.gateway.model.Pagination;
import com.todaysoft.lims.gateway.model.TestingSchedule;
import com.todaysoft.lims.gateway.model.TestingTaskConfig;
import com.todaysoft.lims.gateway.model.TodoModel;
import com.todaysoft.lims.gateway.model.request.TestingNodeTodoRequest;
import com.todaysoft.lims.gateway.model.request.TestingScheduleStartRequest;
import com.todaysoft.lims.gateway.model.request.TestingSheetCreateRequest;
import com.todaysoft.lims.gateway.model.request.TestingSheetSubmitRequest;

public interface ITestingScheduleService
{
	TestingSchedule get(Integer id);
	
    void start(TestingScheduleStartRequest request);
    
    TestingTaskConfig getTestingTaskConfig(String id);
    
    List<TodoModel> todo(TestingNodeTodoRequest request);
    
    void assign(TestingSheetCreateRequest request);
    
    void submit(TestingSheetSubmitRequest request);

    Pagination<TodoModel> processJoin(TestingNodeTodoRequest request);

    List<TodoModel> executeTodo(TestingNodeTodoRequest request);

    void assignDnaQt(TestingSheetCreateRequest request);

    void submitDnaQt(TestingSheetSubmitRequest request);

    void assignWkCreate(TestingSheetCreateRequest request);

    void submitWk(TestingSheetSubmitRequest request);

    void assignWkQt(TestingSheetCreateRequest request);

    void submitWkQt(TestingSheetSubmitRequest request);

    void assignWKCatch(TestingSheetCreateRequest request);

    void submitWKCatch(TestingSheetSubmitRequest request);

    void assignXddl(TestingSheetCreateRequest request);

    void submitXddl(TestingSheetSubmitRequest request);

    void assignAbsoluteQ(TestingSheetCreateRequest request);

    void submitAbsoluteQ(TestingSheetSubmitRequest request);

    void assignOntest(TestingSheetCreateRequest request);

    void submitOntest(TestingSheetSubmitRequest request);
    
    void assignBioInfo(TestingSheetCreateRequest request);
    
    void submitBioInfo(TestingSheetSubmitRequest request);
    
    void assignTecAnalysis(TestingSheetCreateRequest request);
    
    void submitTecAnalysis(TestingSheetSubmitRequest request);
    
    void assignReportCreate(TestingSheetCreateRequest request);
    
    void submitReportCreate(TestingSheetSubmitRequest request);
    
    void assignReportCheck(TestingSheetCreateRequest request);
    
    void submitReportCheck(TestingSheetSubmitRequest request);
    
    void assignReportMail(TestingSheetCreateRequest request);
    
    void submitReportMail(TestingSheetSubmitRequest request);
    
    TestingSheetSubmitRequest dataProcess(TestingSheetSubmitRequest request);
    
    boolean validateCatchCode(TestingSheetCreateRequest request);
}
