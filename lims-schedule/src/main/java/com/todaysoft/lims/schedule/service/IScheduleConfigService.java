package com.todaysoft.lims.schedule.service;

import java.util.List;
import java.util.Map;

import com.todaysoft.lims.schedule.entity.OrderPlanTask;
import com.todaysoft.lims.schedule.entity.OrderPlanTaskDependency;
import com.todaysoft.lims.schedule.entity.ScheduleGlobalConfig;
import com.todaysoft.lims.schedule.entity.ScheduleTestingNodeConfig;

public interface IScheduleConfigService
{
    List<ScheduleGlobalConfig> getOrderLevelGlobalConfigs();
    
    List<ScheduleGlobalConfig> getProductLevelGlobalConfigs();
    
    Map<String, ScheduleTestingNodeConfig> getScheduleTestingNodeConfig(String configId);
    
    ScheduleGlobalConfig getGlobalConfig(String eventKey);
    
    OrderPlanTask getPlanTask(String id, String semantic, String testingId);
    
    OrderPlanTask getPlanTaskById(String taskId);
    
    List<OrderPlanTaskDependency> getDependencyByDependencyId(String dependencyTaskId);
    
    List<OrderPlanTaskDependency> getDependencyByTaskId(String taskId);
}
