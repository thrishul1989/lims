package com.todaysoft.lims.order.action;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.lims.order.job.ReconciliationTask;
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
import com.todaysoft.lims.order.service.IOrderService;
import com.todaysoft.lims.order.util.ThreeDes;
import com.todaysoft.lims.order.utils.JsonUtils;

@RestController
@RequestMapping("/orders")
public class OrderController
{
    private static final Log LOG = LogFactory.getLog(OrderController.class);
    
    @Autowired
    private IOrderService service;
    
    @Autowired
    private ReconciliationTask reconciliationTask;
    
    @RequestMapping(value = "/schedule_monitor_search", method = RequestMethod.POST)
    public OrderSearchResponse<ScheduleMonitorOrder> searchScheduleMonitorOrders(@RequestBody OrderSearchRequest request)
    {
        return service.searchScheduleMonitorOrders(request);
    }
    
    @RequestMapping(value = "/testing_monitor_search", method = RequestMethod.POST)
    public OrderSearchResponse<TestingMonitorOrder> searchTestingMonitorOrders(@RequestBody OrderSearchRequest request)
    {
        return service.searchTestingMonitorOrders(request);
    }
    
    @RequestMapping(value = "/order_search", method = RequestMethod.POST)
    public OrderSearchResponse<CommonOrderResponse> searchOrders(@RequestBody OrderSearchCommonRequest request)
    {
        return service.searchOrders(request);
    }
    
    @RequestMapping(value = "/testingOrderStatusByOrderId", method = RequestMethod.POST)
    public Map<String, List<OrderScheduleModel>> searchTestingOrderStatusByOrderId(@RequestBody OrderStatusScheduleRequest request)
    {
        return service.searchTestingOrderStatusByOrderId(request);
    }
    
    @RequestMapping(value = "/sample_receive_search", method = RequestMethod.POST)
    public OrderSearchResponse<ReceiveSampleOrderResponse> searchReceiveSample(@RequestBody OrderSearchCommonRequest request)
    {
        return service.searchReceiveSample(request);
    }
    
    @RequestMapping(value = "/synchronizeSample", method = RequestMethod.POST)
    public OrderSearchResponse<GanalysisSampleInfo> searchSamplesByCustomerId(@RequestBody SampleSynchronizeRequest request)
    {
        return service.searchSamplesByCustomerId(request);
    }
    
    @RequestMapping(value = "/updateLimsSampleStatus", method = RequestMethod.POST)
    public void updateLimsSampleStatus(@RequestBody CallBackSampleModel request)
    {
        service.updateLimsSampleStatus(request);
    }
    
    @RequestMapping(value = "/synchronizeReport", method = RequestMethod.POST)
    public List<TestingReportModel> synchronizeReport(@RequestBody MaintainReportRequest request)
    {
        return service.synchronizeReport(request);
    }
    
    @RequestMapping(value = "/updateLimsReportStatus", method = RequestMethod.POST)
    public void updateLimsReportStatus(@RequestBody CallBackSampleModel request)
    {
        service.updateLimsReportStatus(request);
    }
    
    @RequestMapping(value = "/seacherBasicSample", method = RequestMethod.POST)
    public List<BasicSampleModel> seacherBasicSample(@RequestBody BasicSampleRequest request)
    {
        return service.seacherBasicSample(request);
    }
    
    @RequestMapping(value = "/resolveAccountStatement", method = RequestMethod.POST)
    public String resolveAccountStatement(@RequestBody AccountStatementRequest data)
    {
        Map<String, Object> result = service.resolveAccountStatement(data);
        //成功后触发对账流程
        if (result.get("code").equals("00"))
        {
            LOG.info("成功接收对账数据,系统开始自动对账");
            new Thread(new Runnable()
            {
                @Override
                public void run()
                {
                    reconciliationTask.executeTask();
                }
            }).start();
        }
        
        ThreeDes des = new ThreeDes();
        des.getKey();
        String strEnc = des.getEncString(JsonUtils.asJson(result));
        return strEnc;
    }
    
}
