package com.todaysoft.lims.schedule.service;

import java.util.List;

public interface ISchedulePlanService
{
    void generateOrderSchedulePlan(String id);
    
    void refineOrderSchedulePlan(String id, List<String> scheduleIds);
    
    void updatePlanForOrderSubmit(String id);
    
    void updatePlanForOrderModify(String id);
    
    void updatePlanForOrderCancel(String id);
    
    void updatePlanForOrderTestingStart(String id);
    
    void updatePlanForSampleReceiveFinished(String orderId);
    
    void updatePlanForSampleStorageFinished(String orderId);
    
    void updatePlanForPaymentConfirmFinished(String orderId);
    
    void updatePlanForReportGenerateFinished(String orderId, String productId);
    
    void updatePlanForReportVerifyFinished(String orderId, String productId, String reviewResult);
    
    void updatePlanForReportDeliverFinished(String orderId, String productId);
    
    void updatePlanForTaskFinished(String taskId);
    
    void updatePlanForTaskFinished(String taskId, boolean dependencyAutoStart);
    
    void updatePlanForMonitorJob();
    
    void updatePlanFinish(String id, String semantic, String testingId);
    
    void refineVerifySchedulePlan(List<String> scheduleIds);
    
    void updateVerifyTestingStart(String orderId);
    
    boolean getIsFinished(String orderId, String semantic);
    
    void updateTaskPlannedFinishDate(String orderId);
    
    void updateTaskPlannedFinishDate(List<String> scheduleIds);
}
