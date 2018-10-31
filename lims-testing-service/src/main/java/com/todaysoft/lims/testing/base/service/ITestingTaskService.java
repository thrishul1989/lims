package com.todaysoft.lims.testing.base.service;

import java.util.List;
import java.util.Set;

import com.todaysoft.lims.testing.base.entity.*;
import com.todaysoft.lims.testing.base.model.BatchWrapTestingTaskContext;
import com.todaysoft.lims.testing.base.model.SangerVerifyRecordModel;
import com.todaysoft.lims.testing.base.model.TaskSubmitModel;
import com.todaysoft.lims.testing.base.model.TestingStartRecord;
import com.todaysoft.lims.testing.base.service.impl.TaskVariables;
import com.todaysoft.lims.testing.technicalanaly.model.TechnicalAnalyTask;

public interface ITestingTaskService
{
    TestingTask get(String id);
    
    void modify(TestingTask request);
    
    String getVariables(String id);
    
    void submit(TaskSubmitModel data);
    
    <T extends TaskVariables> T obtainVariables(String id, Class<T> clazz);
    
    void updateVariables(String id, Object variables);
    
    Order getRelatedOrder(String taskId);
    
    List<Product> getRelatedProducts(String taskId);
    
    List<TestingMethod> getRelatedTestingMethods(String taskId);
    
    List<TestingTask> getRelatedTasks(String inputSampleId, String semantic);
    
    TestingTask getUncompletedTestingTask(String inputSampleId, String semantic);
    
    TestingMethod getTestingMethodBySemantic(String semantic);
    
    List<Probe> getProbeListByProductAndTestMethod(String productId, String testingMethodId);
    
    List<Primer> getPrimerListByProductCode(String productCode);
    
    void asSuccess(TestingTask task, Object details, TestingSample outputSample);
    
    BatchWrapTestingTaskContext getBatchWrapTestingTaskContext(Set<String> keys);
    
    BatchWrapTestingTaskContext getBatchWrapTestingTaskContext(Set<String> keys, String[] excludes, String[] includes);
    
    BatchWrapTestingTaskContext getBatchWrapTestingTaskContextWithExcludes(Set<String> keys, String... excludes);
    
    BatchWrapTestingTaskContext getBatchWrapTestingTaskContextWithIncludes(Set<String> keys, String... includes);
    
    void updateTaskRedundantColumn(List<TestingTask> tasks, Integer flag);//0创建 1 更新字段
    
    void startScheduleNoActional(List<TestingStartRecord> records, int type);
    
    TechnicalAnalyTask wrapForTec(TestingTask entity);

    BiologyAnnotationTask getBio(String id);

    ReceivedSample getSample(String sampleCode);

    TechnicalAnalysisTask getTech(String id);

    Product getPro(String code);

    TechnicalSheet getSheetByTask(String taskId);

    SangerVerifyRecordModel getSangerRecordByVerifyKey(String verifyKey);

}
