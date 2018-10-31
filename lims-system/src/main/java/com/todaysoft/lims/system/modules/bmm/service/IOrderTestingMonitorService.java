package com.todaysoft.lims.system.modules.bmm.service;

import java.util.List;
import java.util.Map;

import com.todaysoft.lims.order.request.OrderSearchRequest;
import com.todaysoft.lims.order.request.OrderStatusScheduleRequest;
import com.todaysoft.lims.order.response.OrderStatusScheduleModel;
import com.todaysoft.lims.order.response.TestingMonitorOrder;
import com.todaysoft.lims.system.model.vo.Pagination;

public interface IOrderTestingMonitorService
{
    Pagination<TestingMonitorOrder> search(OrderSearchRequest request);
    
    Map<String, List<OrderStatusScheduleModel>> getScheduleListByOrderId(OrderStatusScheduleRequest request);
}
