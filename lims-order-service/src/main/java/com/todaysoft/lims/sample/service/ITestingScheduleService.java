package com.todaysoft.lims.sample.service;

import java.util.List;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.sample.dao.searcher.TestingScheduleSearcher;
import com.todaysoft.lims.sample.entity.SampleReceiveDetail;
import com.todaysoft.lims.sample.entity.TestingSchedule;
import com.todaysoft.lims.sample.entity.TestingScheduleTask;
import com.todaysoft.lims.sample.model.SampleModel;
import com.todaysoft.lims.sample.model.Task;
import com.todaysoft.lims.sample.model.TestingTask;
import com.todaysoft.lims.sample.model.TestingTaskConfig;
import com.todaysoft.lims.sample.model.TodoModel;
import com.todaysoft.lims.sample.model.request.TestingNodeTodoRequest;
import com.todaysoft.lims.sample.model.request.TestingScheduleStartRequest;
import com.todaysoft.lims.sample.model.request.TestingSheetCreateRequest;
import com.todaysoft.lims.sample.model.request.TestingSheetSubmitRequest;

public interface ITestingScheduleService
{
	TestingSchedule get(Integer id);
	
    void start(TestingScheduleStartRequest request);
    
    TestingTaskConfig getTestingTaskConfig(String id);
    
    List<TodoModel> todo(TestingNodeTodoRequest request);

    List<TodoModel> executeTodo(TestingNodeTodoRequest request);

    void assign(TestingSheetCreateRequest request);
    
    void submit(TestingSheetSubmitRequest request);

    void assignDnaQt(TestingSheetCreateRequest request);

    void submitDnaQt(TestingSheetSubmitRequest request);

    void assignWkCreate(TestingSheetCreateRequest request);

    void submitWk(TestingSheetSubmitRequest request);

    void assignWkQt(TestingSheetCreateRequest request);

    void submitWkQt(TestingSheetSubmitRequest request);

    Pagination<TodoModel> processJoin(TestingNodeTodoRequest request);

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


    TestingSchedule create(TestingSchedule request);
    
    List<TestingSchedule> list(TestingScheduleSearcher searcher);
    
    void delete(Integer id);
    
    void modify( TestingSchedule request);
    
    SampleModel analysis(SampleReceiveDetail request);	
    
    List<TestingSchedule> getTestingScheduleList(Integer id);
    
    List<TestingTask> getTestingTaskList(Integer  id);
    
    Integer createTestingScheduleTask(TestingScheduleTask request);

}
