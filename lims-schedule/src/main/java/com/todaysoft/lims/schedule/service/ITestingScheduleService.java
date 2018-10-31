package com.todaysoft.lims.schedule.service;

import com.todaysoft.lims.schedule.entity.OrderPlanTask;
import com.todaysoft.lims.schedule.model.PlanSearchModel;
import com.todaysoft.lims.schedule.service.core.event.ScheduleTaskActiveEvent;


public interface ITestingScheduleService
{
    void updatePlanWithSheetSubmit(String sheetId);
    
    void updateAbnormalSolve(String taskId);
    
    void updateStatusSearchCancel(String scheduleId);
    
    void newUpdatePlanWithSheetSubmit(ScheduleTaskActiveEvent event);
    
    OrderPlanTask getOrderPlanTaskByTaskId(PlanSearchModel model);
    
    void updateAbnormalSolveForNewBiology(String taskId,String semantic);
}
