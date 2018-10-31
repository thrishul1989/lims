package com.todaysoft.lims.order.service;

import java.util.List;
import java.util.Map;

import com.todaysoft.lims.order.model.BasicSampleModel;
import com.todaysoft.lims.order.model.BasicSampleRequest;
import com.todaysoft.lims.order.model.CallBackSampleModel;
import com.todaysoft.lims.order.model.reconciliation.AccountStatementRequest;
import com.todaysoft.lims.order.mybatis.model.OrderScheduleModel;
import com.todaysoft.lims.order.request.MaintainReportRequest;
import com.todaysoft.lims.order.request.OrderSearchCommonRequest;
import com.todaysoft.lims.order.request.OrderSearchRequest;
import com.todaysoft.lims.order.request.OrderStatusScheduleRequest;
import com.todaysoft.lims.order.request.SampleSynchronizeRequest;
import com.todaysoft.lims.order.request.TestingReportModel;
import com.todaysoft.lims.order.response.CommonOrderResponse;
import com.todaysoft.lims.order.response.GanalysisSampleInfo;
import com.todaysoft.lims.order.response.OrderSearchResponse;
import com.todaysoft.lims.order.response.ReceiveSampleOrderResponse;
import com.todaysoft.lims.order.response.ScheduleMonitorOrder;
import com.todaysoft.lims.order.response.TestingMonitorOrder;

public interface IOrderService
{
    OrderSearchResponse<ScheduleMonitorOrder> searchScheduleMonitorOrders(OrderSearchRequest request);
    
    OrderSearchResponse<TestingMonitorOrder> searchTestingMonitorOrders(OrderSearchRequest request);
    
    OrderSearchResponse<CommonOrderResponse> searchOrders(OrderSearchCommonRequest request);
    
    Map<String, List<OrderScheduleModel>> searchTestingOrderStatusByOrderId(OrderStatusScheduleRequest request);
    
    OrderSearchResponse<ReceiveSampleOrderResponse> searchReceiveSample(OrderSearchCommonRequest request);
    
    OrderSearchResponse<GanalysisSampleInfo> searchSamplesByCustomerId(SampleSynchronizeRequest request);
    
    void updateLimsSampleStatus(CallBackSampleModel request);
    
    List<TestingReportModel> synchronizeReport(MaintainReportRequest request);
    
    void updateLimsReportStatus(CallBackSampleModel request);
    
    List<BasicSampleModel> seacherBasicSample(BasicSampleRequest request);
    
    Map<String, Object> resolveAccountStatement(AccountStatementRequest data);
}
