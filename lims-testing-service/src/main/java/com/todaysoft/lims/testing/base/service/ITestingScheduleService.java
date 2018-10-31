package com.todaysoft.lims.testing.base.service;

import java.util.Date;
import java.util.List;
import java.util.Set;

import com.todaysoft.lims.testing.base.dao.searcher.SampleTestingExportSearch;
import com.todaysoft.lims.testing.base.entity.AbnormalSolveRecord;
import com.todaysoft.lims.testing.base.entity.PoolingDivisionSample;
import com.todaysoft.lims.testing.base.entity.Product;
import com.todaysoft.lims.testing.base.entity.ReceivedSample;
import com.todaysoft.lims.testing.base.entity.SangerVerifyRecord;
import com.todaysoft.lims.testing.base.entity.TestingSample;
import com.todaysoft.lims.testing.base.entity.TestingSangerCount;
import com.todaysoft.lims.testing.base.entity.TestingSangerTestSample;
import com.todaysoft.lims.testing.base.entity.TestingSchedule;
import com.todaysoft.lims.testing.base.entity.TestingScheduleActive;
import com.todaysoft.lims.testing.base.entity.TestingScheduleHistory;
import com.todaysoft.lims.testing.base.entity.TestingSheet;
import com.todaysoft.lims.testing.base.entity.TestingTask;
import com.todaysoft.lims.testing.base.entity.TestingTaskResult;
import com.todaysoft.lims.testing.base.entity.TestingTechnicalAnalyRecord;
import com.todaysoft.lims.testing.base.model.AbnormalSolveModel;
import com.todaysoft.lims.testing.base.model.ConcludingReportModel;
import com.todaysoft.lims.testing.base.model.DataSendingModel;
import com.todaysoft.lims.testing.base.model.DealScheduleModel;
import com.todaysoft.lims.testing.base.model.ProductMethodModel;
import com.todaysoft.lims.testing.base.model.SampleTestingExportModel;
import com.todaysoft.lims.testing.base.model.ScheduleQuery;
import com.todaysoft.lims.testing.base.model.TaskConfig;
import com.todaysoft.lims.testing.base.model.TaskFailExportModel;
import com.todaysoft.lims.testing.base.model.TaskSheetExportModel;
import com.todaysoft.lims.testing.base.model.TemproaryTestingTask;
import com.todaysoft.lims.testing.base.model.TestTask;
import com.todaysoft.lims.testing.base.model.TestTaskSearcher;
import com.todaysoft.lims.testing.base.model.TestingMethodRequest;
import com.todaysoft.lims.testing.base.model.TestingNode;
import com.todaysoft.lims.testing.base.model.TestingScheduleRequest;
import com.todaysoft.lims.testing.base.model.TestingStartRecord;
import com.todaysoft.lims.testing.base.model.TestingTaskRequest;
import com.todaysoft.lims.testing.base.model.VariableModel;
import com.todaysoft.lims.testing.base.request.OrderProductTestingScheduleRequest;
import com.todaysoft.lims.testing.base.request.ScheduleHistoryRequest;
import com.todaysoft.lims.testing.base.request.StartOrderTestingRequest;
import com.todaysoft.lims.testing.base.request.TemproaryTestingTaskRequest;
import com.todaysoft.lims.testing.base.request.TestingScheduleQueryRequest;
import com.todaysoft.lims.testing.base.request.TestingtechnicalanalyrecordTempory;
import com.todaysoft.lims.testing.rqt.model.NgsCreateEvent;

public interface ITestingScheduleService
{
    void start(TestingStartRecord record);
    
    void start(List<TestingStartRecord> records, int type);
    
    void autoStartSchedule(TestingStartRecord record, TestingNode node);
    
    void startExperimentProcess(String testerId, TestingSheet sheet);
    
    List<TestTask> testList(TestTaskSearcher searcher);
    
    List<TestingSchedule> getRelatedSchedules(String taskId);
    
    List<TestingSchedule> getRelatedSchedulesByTestingTask(String taskId);
    
    List<TestingSchedule> getScheduleHistorys(String taskId);
    
    List<TestingSchedule> getRelatedSchedulesBySampleId(String sampleId);
    
    TestingSchedule getById(String id);
    
    List<TestingScheduleActive> getScheduleActives(String scheduleId);
    
    List<TestingScheduleActive> getRunningScheduleActives(String scheduleId);
    
    List<TestingScheduleActive> getScheduleActives(String scheduleId, String node);
    
    TestingScheduleActive getScheduleActive(String scheduleId, String taskId);
    
    TaskConfig getScheduleNextNodeConfig(TestingSchedule schedule, String node);
    
    TestingScheduleRequest getOnlySchedule(ScheduleQuery request);
    
    List<TestingScheduleHistory> getTestingScheduleHistoryByScheduleID(String scheduleID);
    
    TestingTaskRequest getTTRById(String testingTaskId);
    
    SangerVerifyRecord getSangerVerifyRecord(TestingSchedule schedule);
    
    TestingTask getScheduleNodeLastTestingTask(String scheduleId, String node);
    
    TestingTask getScheduleNodeLastTestingTaskComplete(String scheduleId, String node);
    
    void gotoNextNode(TestingSchedule schedule, String node, TestingTask nextTask, Date timestamp);
    
    void gotoNextNode(TestingSchedule schedule, String node, List<TestingTask> nextTasks, Date timestamp);
    
    void gotoError(TestingSchedule schedule, String node);
    
    void restart(TestingSchedule schedule, List<TestingTask> restartTasks, Date timestamp);
    
    void cancel(TestingSchedule schedule, String reason);
    
    void sendNgsCreateMessage(NgsCreateEvent event);
    
    void waitConcurrentNodeForDNAQC(TestingSchedule schedule, TestingSample dnaSample);
    
    void setScheduleActiveName(TestingSchedule schedule);
    
    List<TestingScheduleRequest> getTestingSchedules(String scheduleId);
    
    TestingMethodRequest getTestingMethodById(String id);
    
    TestingScheduleRequest getScheduleById(String id);
    
    TestingSheet getTestingSheetByTaskId(String taskId);
    
    List<TestingSchedule> getTestingSchedulesByData(ScheduleQuery request);
    
    TestingTaskResult getTestingTaskResultById(String id);
    
    TestingSangerCount getTestingSangerCountByScheduleId(String id);
    
    TestingTask getSangerTestPcrOneTestingTaskBySampleId(String sampleId);
    
    TestingSangerTestSample getSangerTestPcrOneBySampleId(String sampleId);
    
    TestingTask getTestingTaskActiveByVerifyKey(String key, String semantic);
    
    TestingSample getTestingSample(String id);
    
    ReceivedSample getReceivedSample(String id);
    
    TestingtechnicalanalyrecordTempory getSangerVerifyRecordById(String id);
    
    String getScheduleActiveName(TestingSchedule testingSchedule, List<TestingScheduleActive> list);
    
    List<TestingScheduleActive> getScheduleActivesByTaskId(String taskId);
    
    List<TestingTask> getSangerTestAbnormalTaskByScheduleId(String scheduleId);
    
    TestingtechnicalanalyrecordTempory getMLPAVerifyRecordById(String id);
    
    TestingtechnicalanalyrecordTempory getQPCRVerifyRecordById(String id);
    
    Integer getOrderStatus(String orderId);
    
    List<TestingScheduleActive> getActiveTasks(String sheduleId);
    
    void modifyShedule(DealScheduleModel scheduleModel, String token);
    
    Product getProductByDataCode(String dataCode);
    
    void sendReportMessage(VariableModel model);
    
    void dataSendingMessage(DataSendingModel model);
    
    String getSequenceCode(String scheduleId);
    
    void sendAbnormalSolveMessage(String taskId);
    
    void sendSheetMessage(String sheetId);
    
    void sendOrderTestingStartMessage(String orderId);
    
    void sendOrderVerifyTestingStartMessage(List<String> scheduleIds, Set<String> orderIds);
    
    List<String> startActional(List<TestingStartRecord> records, int type);
    
    TestingSchedule getTestingScheduleByDataCode(String dataCode, String semantic, int type);
    
    String scheduleCancelTrigger(String scheduleId);
    
    void cancelOrderSchedule(StartOrderTestingRequest request);
    
    AbnormalSolveRecord getRemarkByAbnormal(String taskId);
    
    TestingTechnicalAnalyRecord getAnalRecordByTaskId(String taskId);
    
    void updateReportSample(String scheduleId);
    
    void extraSendReport(ConcludingReportModel concludingReportModel);
    
    void concludingReportMessage(ConcludingReportModel concludingReportModel);
    
    List<AbnormalSolveModel> getCancelDetails(String scheduleId);
    
    List<SampleTestingExportModel> getSampleTestingExportModel(SampleTestingExportSearch search);
    
    Date getTaskStartDate(String scheduleId);
    
    List<TaskSheetExportModel> getTaskSheetModel(SampleTestingExportSearch search);
    
    List<TaskSheetExportModel> getTaskSucessRateExportRecords(SampleTestingExportSearch search);
    
    List<TaskFailExportModel> getgetTaskFailExportRecords(SampleTestingExportSearch search);
    
    String getStrategyByTaskSemantic(TestingTask task, TestingTaskResult taskResult);
    
    List<TemproaryTestingTask> getTemproaryTestingTaskList(TemproaryTestingTaskRequest request);
    
    List<PoolingDivisionSample> getPoolingDivisionSample(String squencingCode);
    
    ProductMethodModel getProductAndMethod(String sampleCode, String productCode, String methodName);
    
    List<TestingScheduleHistory> getResubmitTaskByScheduleAndTaskSemantic(String scheduleId, String taskSemantic);
    
    ReceivedSample getReceivedSampleBySampleCode(String sampleCode);
    
    Boolean checkOrderTechnicalStatus(String id);
    
    ProductMethodModel getProductNameByCode(String productCode);
    
    List<TestingSchedule> cancelOrderProductSchedule(OrderProductTestingScheduleRequest request, String token);
    
    TestingScheduleHistory getScheduleHistoryByTaskAndSchedule(ScheduleHistoryRequest request);
    
    TestingTask getDNATaskByHistory(TestingScheduleHistory history);
    
    void sendAbnormalSolveMessageForNewBiology(String taskId, String semantic);
    
    List<TestingSchedule> searcherTestingScheduleByCondition(TestingScheduleQueryRequest request);
    
    void sendAppendVerityOrderTestingStartForMonitorMessage(String orderId, List<String> scheduleIds);
    
}
