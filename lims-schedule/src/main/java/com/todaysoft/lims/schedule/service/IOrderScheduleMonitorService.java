package com.todaysoft.lims.schedule.service;

import java.util.List;
import java.util.Set;

import com.todaysoft.lims.schedule.response.OrderPostponedDetails;
import com.todaysoft.lims.schedule.response.OrderScheduleMonitorPlanTask;
import com.todaysoft.lims.schedule.response.OrderScheduleMonitorResponse;

public interface IOrderScheduleMonitorService
{
    OrderScheduleMonitorResponse getOrderScheduleMonitorDetails(String id);
    
    List<OrderScheduleMonitorPlanTask> getGroupPlanTasks(String groupId);
    
    List<OrderPostponedDetails> getOrderPostponedDetails(Set<String> orderIds);
    
    void updateOrderUrgent(String orderId, Integer ifUrgent, String urgentName);
}
