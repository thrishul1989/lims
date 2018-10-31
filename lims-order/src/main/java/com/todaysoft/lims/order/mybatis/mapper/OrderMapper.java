package com.todaysoft.lims.order.mybatis.mapper;

import java.util.List;

import com.todaysoft.lims.order.mybatis.model.CommonOrderModel;
import com.todaysoft.lims.order.mybatis.model.ScheduleMonitorOrderModel;
import com.todaysoft.lims.order.mybatis.model.TestingMonitorOrderModel;
import com.todaysoft.lims.order.mybatis.parameter.OrderSearcher;
import com.todaysoft.lims.order.mybatis.parameter.ScheduleMonitorOrderSearcher;
import com.todaysoft.lims.order.mybatis.parameter.TestingMonitorOrderSearcher;
import com.todaysoft.lims.order.response.ReceiveSampleOrderResponse;

public interface OrderMapper
{
    int countForScheduleMonitorOrderSearcher(ScheduleMonitorOrderSearcher searcher);
    
    List<ScheduleMonitorOrderModel> searchScheduleMonitorOrders(ScheduleMonitorOrderSearcher searcher);
    
    int countForTestingMonitorOrderSearcher(TestingMonitorOrderSearcher searcher);
    
    List<TestingMonitorOrderModel> searchTestingMonitorOrders(TestingMonitorOrderSearcher searcher);
    
    int countForOrderSearcher(OrderSearcher searcher);
    
    List<CommonOrderModel> searchOrderList(OrderSearcher searcher);
    
    int countForReceiveSample(OrderSearcher searcher);
    
    List<ReceiveSampleOrderResponse> searchReceiveSampleList(OrderSearcher searcher);
    
}
