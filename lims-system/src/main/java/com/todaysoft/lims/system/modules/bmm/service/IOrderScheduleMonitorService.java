package com.todaysoft.lims.system.modules.bmm.service;

import java.util.List;
import java.util.Map;

import com.todaysoft.lims.order.request.OrderSearchRequest;
import com.todaysoft.lims.order.response.ScheduleMonitorOrder;
import com.todaysoft.lims.schedule.response.OrderPostponedDetails;
import com.todaysoft.lims.schedule.response.OrderScheduleMonitorResponse;
import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.modules.bmm.model.OrderSchedulePlanTaskModel;

public interface IOrderScheduleMonitorService
{
    Pagination<ScheduleMonitorOrder> search(OrderSearchRequest request);
    
    OrderScheduleMonitorResponse getOrderScheduleMonitorDetails(String orderId);
    
    List<OrderSchedulePlanTaskModel> getGroupPlanTasks(String groupId);
    
    List<OrderPostponedDetails> getOrderPostponedDetails(String keys);
    
    void updateOrderUrgent(String orderId, Integer ifUrgent, String urgentName);
    
    Map<String, Object> correct(String orderId);
}
