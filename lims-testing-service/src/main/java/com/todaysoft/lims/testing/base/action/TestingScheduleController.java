package com.todaysoft.lims.testing.base.action;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.todaysoft.lims.testing.base.entity.*;
import com.todaysoft.lims.testing.base.request.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.testing.base.dao.searcher.SampleTestingExportSearch;
import com.todaysoft.lims.testing.base.model.AbnormalSolveModel;
import com.todaysoft.lims.testing.base.model.DealScheduleModel;
import com.todaysoft.lims.testing.base.model.ProductMethodModel;
import com.todaysoft.lims.testing.base.model.ProductScheduleCancelSearcher;
import com.todaysoft.lims.testing.base.model.SampleTestingExportModel;
import com.todaysoft.lims.testing.base.model.ScheduleQuery;
import com.todaysoft.lims.testing.base.model.TaskFailExportModel;
import com.todaysoft.lims.testing.base.model.TaskSheetExportModel;
import com.todaysoft.lims.testing.base.model.TemproaryTestingTask;
import com.todaysoft.lims.testing.base.model.TestTask;
import com.todaysoft.lims.testing.base.model.TestTaskSearcher;
import com.todaysoft.lims.testing.base.model.TestingMethodRequest;
import com.todaysoft.lims.testing.base.model.TestingScheduleRequest;
import com.todaysoft.lims.testing.base.model.TestingStartRecord;
import com.todaysoft.lims.testing.base.model.TestingTaskRequest;
import com.todaysoft.lims.testing.base.model.VariableModel;
import com.todaysoft.lims.testing.base.service.ICommonService;
import com.todaysoft.lims.testing.base.service.IOrderStatusService;
import com.todaysoft.lims.testing.base.service.IProductCancelScheduleService;
import com.todaysoft.lims.testing.base.service.ITestingResolveService;
import com.todaysoft.lims.testing.base.service.ITestingScheduleService;
import com.todaysoft.lims.testing.ons.EventPublisher;
import com.todaysoft.lims.testing.ons.IMessageProducer;
import com.todaysoft.lims.utils.Collections3;

@RestController
@RequestMapping("/bpm/testing")
public class TestingScheduleController
{
    @Autowired
    private ITestingResolveService resolveService;
    
    @Autowired
    private ITestingScheduleService scheduleService;
    
    @Autowired
    private IOrderStatusService orderStatusService;
    
    @Autowired
    private EventPublisher eventPublisher;
    
    @Autowired
    private IMessageProducer producer;
    
    @Autowired
    private ICommonService commonService;
    
    @Autowired
    private IProductCancelScheduleService productCancelScheduleService;
    
    //自动启动,存下来异常原因，不用抛出异常
    @RequestMapping(value = "/order/autoStart", method = RequestMethod.POST)
    public void autoStart(@RequestBody StartOrderTestingRequest request)
    {
        List<TestingStartRecord> records = resolveService.resolve(request, 1);
        if (Collections3.isNotEmpty(records))
        {
            synchronized (this)
            {
                scheduleService.start(records, 1);
            }
        }
    }
    
    //手动启动，抛出异常
    @RequestMapping(value = "/order/start", method = RequestMethod.POST)
    public String start(@RequestBody StartOrderTestingRequest request)
    {
        String result = "success";
        try
        {
            List<TestingStartRecord> records = resolveService.resolve(request, 2);
            synchronized (this)
            {
                scheduleService.start(records, 2);
            }
        }
        catch (Exception e)
        {
            result = "fail";
        }
        return result;
    }
    
    @RequestMapping(value = "/order/{id}/status", method = RequestMethod.GET)
    public Map<String, Object> getOrderStatus(@PathVariable String id)
    {
        Integer status = scheduleService.getOrderStatus(id);
        Map<String, Object> rsp = new HashMap<String, Object>();
        rsp.put("status", status);
        return rsp;
    }
    
    @RequestMapping(value = "/verify/start", method = RequestMethod.POST)
    public void start(@RequestBody StartVerifyTestingRequest request)
    {
        List<TestingStartRecord> records = resolveService.resolve(request);
        scheduleService.start(records, 1);
        
    }
    
    @RequestMapping(value = "/testList", method = RequestMethod.POST)
    public List<TestTask> testList(@RequestBody TestTaskSearcher searcher)
    {
        return scheduleService.testList(searcher);
    }
    
    @RequestMapping(value = "/order/getOnlySchedule", method = RequestMethod.POST)
    public TestingScheduleRequest getOnlySchedule(@RequestBody ScheduleQuery searcher)
    {
        return scheduleService.getOnlySchedule(searcher);
    }
    
    @RequestMapping(value = "/order/getTSHByScheduleID/{scheduleId}", method = RequestMethod.GET)
    public List<TestingScheduleHistory> getTestingScheduleHistoryByScheduleID(@PathVariable String scheduleId)
    {
        return scheduleService.getTestingScheduleHistoryByScheduleID(scheduleId);
    }
    
    @RequestMapping(value = "/order/getTTRById/{testingTaskId}", method = RequestMethod.GET)
    public TestingTaskRequest getTTRById(@PathVariable String testingTaskId)
    {
        return scheduleService.getTTRById(testingTaskId);
    }
    
    @RequestMapping(value = "/order/getTestingSchedules/{scheduleId}", method = RequestMethod.GET)
    public List<TestingScheduleRequest> getTestingSchedules(@PathVariable String scheduleId)
    {
        return scheduleService.getTestingSchedules(scheduleId);
    }
    
    @RequestMapping(value = "order/getTestingMethodById/{id}", method = RequestMethod.GET)
    public TestingMethodRequest getTestingMethodById(@PathVariable String id)
    {
        return scheduleService.getTestingMethodById(id);
    }
    
    @RequestMapping(value = "/order/getScheduleById/{id}", method = RequestMethod.GET)
    public TestingScheduleRequest getScheduleById(@PathVariable String id)
    {
        return scheduleService.getScheduleById(id);
    }
    
    @RequestMapping(value = "/order/getTestingSheetByTaskId/{taskId}", method = RequestMethod.GET)
    public TestingSheet getTestingSheetByTaskId(@PathVariable String taskId)
    {
        return scheduleService.getTestingSheetByTaskId(taskId);
    }
    
    @RequestMapping(value = "/order/getTestingSchedulesByData", method = RequestMethod.POST)
    public List<TestingSchedule> getTestingSchedulesByData(@RequestBody ScheduleQuery request)
    {
        return scheduleService.getTestingSchedulesByData(request);
    }
    
    @RequestMapping(value = "/order/getTestingTaskResultById/{id}", method = RequestMethod.GET)
    public TestingTaskResult getTestingTaskResultById(@PathVariable String id)
    {
        return scheduleService.getTestingTaskResultById(id);
    }
    
    @RequestMapping(value = "/order/getTestingSample/{id}", method = RequestMethod.GET)
    public TestingSample getTestingSample(@PathVariable String id)
    {
        return scheduleService.getTestingSample(id);
    }
    
    @RequestMapping(value = "/order/getReceivedSample/{id}", method = RequestMethod.GET)
    public ReceivedSample getReceivedSample(@PathVariable String id)
    {
        return scheduleService.getReceivedSample(id);
    }
    
    @RequestMapping(value = "/order/getSangerVerifyRecordById/{id}", method = RequestMethod.GET)
    public TestingtechnicalanalyrecordTempory getSangerVerifyRecordById(@PathVariable String id)
    {
        return scheduleService.getSangerVerifyRecordById(id);
    }
    
    @RequestMapping(value = "/order/getMLPAVerifyRecordById/{id}", method = RequestMethod.GET)
    public TestingtechnicalanalyrecordTempory getMLPAVerifyRecordById(@PathVariable String id)
    {
        return scheduleService.getMLPAVerifyRecordById(id);
    }
    
    @RequestMapping(value = "/order/getQPCRVerifyRecordById/{id}", method = RequestMethod.GET)
    public TestingtechnicalanalyrecordTempory getQPCRVerifyRecordById(@PathVariable String id)
    {
        return scheduleService.getQPCRVerifyRecordById(id);
    }
    
    @RequestMapping(value = "/order/getActiveTasks/{sheduleId}", method = RequestMethod.GET)
    public List<TestingScheduleActive> getActiveTasks(@PathVariable String sheduleId)
    {
        return scheduleService.getActiveTasks(sheduleId);
    }
    
    @RequestMapping(value = "/order/modifyShedule", method = RequestMethod.POST)
    public void modifyShedule(@RequestBody DealScheduleModel scheduleModel, @RequestHeader(RequestHeaders.MEMBER_TOKEN) String token)
    {
        scheduleService.modifyShedule(scheduleModel, token);
        if ("cancel".equals(scheduleModel.getOperate()))
        {
            VariableModel model = new VariableModel();
            model.setScheduleIds(scheduleModel.getSheduleId());
            scheduleService.sendReportMessage(model);
            String orderId = scheduleService.scheduleCancelTrigger(scheduleModel.getSheduleId()); //更改订单产品状态，发送消息
            //发送订单取消消息
            if (StringUtils.isNotEmpty(orderId))
            {
                eventPublisher.fireOrderIsCancel(orderId);
            }
            //发送监控节点取消消息 17.10.31
            if (com.todaysoft.lims.utils.StringUtils.isNotEmpty(scheduleModel.getSheduleId()))
            {
                producer.sendStatusSearchCancelMessage(scheduleModel.getSheduleId());
            }
        }
        else
        //暂停、重启 直接更改订单状态
        {
            orderStatusService.pauseOrderStatus(scheduleModel.getSheduleId(), scheduleModel.getOperate()); //更改订单状态
        }
        
    }
    
    @RequestMapping(value = "/order/getSequenceCode/{scheduleId}", method = RequestMethod.GET)
    public String getSequenceCode(@PathVariable String scheduleId)
    {
        return scheduleService.getSequenceCode(scheduleId);
    }
    
    @RequestMapping(value = "/order/cancelOrderSchedule", method = RequestMethod.POST)
    public void cancelOrderSchedule(@RequestBody StartOrderTestingRequest request)
    {
        String orderId = request.getId();
        if (StringUtils.isNotEmpty(orderId))
        {
            scheduleService.cancelOrderSchedule(request);
        }
    }
    
    @RequestMapping(value = "/order/cancelOrderProductSechedulePaging")
    public Pagination<ProductCancelRecord> cancelOrderProductSechedulePaging(@RequestBody ProductScheduleCancelSearcher searcher)
    {
        return productCancelScheduleService.paging(searcher);
    }
    
    /**
     * 订单-产品层面 取消产品流程，并检测 订单是否取消
     * @param request
     */
    @RequestMapping(value = "/order/cancelOrderProductSchedule", method = RequestMethod.POST)
    public void cancelOrderProductSchedule(@RequestBody OrderProductTestingScheduleRequest request, @RequestHeader(RequestHeaders.MEMBER_TOKEN) String token)
    {
        List<TestingSchedule> schedules = scheduleService.cancelOrderProductSchedule(request, token);
        //发送订单取消消息
        if (StringUtils.isNotEmpty(request.getOrderId()))
        {
            eventPublisher.fireOrderIsCancel(request.getOrderId());
        }
        
        if (Collections3.isNotEmpty(schedules))
        {
            VariableModel model = new VariableModel();
            for (TestingSchedule s : schedules)
            {
                //取消流程,查询是否出报告<适用 一个产品取消，另外一个已经完成，可出报告>
                model.setScheduleIds(s.getId());
                scheduleService.sendReportMessage(model);
                //发送监控节点取消消息 
                producer.sendStatusSearchCancelMessage(s.getId());
                
            }
        }
        
    }
    
    @RequestMapping(value = "/order/getProductCancelRecordById/{id}", method = RequestMethod.GET)
    public ProductCancelRecord getProductCancelRecordById(@PathVariable String id)
    {
        return productCancelScheduleService.getProductCancelRecordById(id);
        
    }
    
    @RequestMapping(value = "/order/getRemarkByAbnormal/{taskId}", method = RequestMethod.GET)
    public AbnormalSolveRecord getRemarkByAbnormal(@PathVariable String taskId)
    {
        return scheduleService.getRemarkByAbnormal(taskId);
    }
    
    @RequestMapping(value = "/order/getCancelDetails/{scheduleId}", method = RequestMethod.GET)
    public List<AbnormalSolveModel> getCancelDetails(@PathVariable String scheduleId)
    {
        return scheduleService.getCancelDetails(scheduleId);
    }
    
    // 样本实验统计数据
    @RequestMapping(value = "/getSampleTestingModel", method = RequestMethod.POST)
    public List<SampleTestingExportModel> getSampleTestingExportModel(@RequestBody SampleTestingExportSearch search)
    {
        return scheduleService.getSampleTestingExportModel(search);
    }
    
    // 工作任务统计数据
    @RequestMapping(value = "/getTaskSheetModel", method = RequestMethod.POST)
    public List<TaskSheetExportModel> getTaskSheetModel(@RequestBody SampleTestingExportSearch search)
    {
        return scheduleService.getTaskSheetModel(search);
    }
    
    // 样本成功率统计数据
    @RequestMapping(value = "/getTaskSucessRateExportRecords", method = RequestMethod.POST)
    public List<TaskSheetExportModel> getTaskSucessRateExportRecords(@RequestBody SampleTestingExportSearch search)
    {
        return scheduleService.getTaskSucessRateExportRecords(search);
    }
    
    // 异常任务处理统计数据
    @RequestMapping(value = "/getTaskFailModel", method = RequestMethod.POST)
    public List<TaskFailExportModel> getgetTaskFailExportRecords(@RequestBody SampleTestingExportSearch search)
    {
        return scheduleService.getgetTaskFailExportRecords(search);
    }
    
    @RequestMapping(value = "/order/getTaskStartDate/{scheduleId}", method = RequestMethod.GET)
    public Date getTaskStartDate(@PathVariable String scheduleId)
    {
        return scheduleService.getTaskStartDate(scheduleId);
    }
    
    @RequestMapping(value = "/order/getTemproaryTestingTaskList", method = RequestMethod.POST)
    public List<TemproaryTestingTask> getTemproaryTestingTaskList(@RequestBody TemproaryTestingTaskRequest request)
    {
        return scheduleService.getTemproaryTestingTaskList(request);
    }
    
    @RequestMapping(value = "/getPoolingDivisionSample/{squencingCode}")
    public List<PoolingDivisionSample> getPoolingDivisionSample(@PathVariable String squencingCode)
    {
        return scheduleService.getPoolingDivisionSample(squencingCode);
    }
    
    @RequestMapping(value = "/getTaskCodeBySemantic/{taskSemantic}")
    public String getTaskCodeBySemantic(@PathVariable String taskSemantic)
    {
        return commonService.getTaskCodeBySemantic(taskSemantic);
    }
    
    @RequestMapping(value = "/getProductAndMethod/{sampleCode}/{productCode}/{methodName}")
    public ProductMethodModel getProductAndMethod(@PathVariable String sampleCode, @PathVariable String productCode, @PathVariable String methodName)
    {
        return scheduleService.getProductAndMethod(sampleCode, productCode, methodName);
    }
    
    @RequestMapping(value = "/getProductNameByCode")
    public ProductMethodModel getProductNameByCode(@RequestBody ProductQueryRequest request)
    {
        if (StringUtils.isNotEmpty(request.getProductCode()))
        {
            return scheduleService.getProductNameByCode(request.getProductCode());
        }
        else
        {
            return null;
        }
    }
    
    /**
     * 判断订单流程是否到技术分析
     * @param id
     * @return true :不可追加
     */
    @RequestMapping(value = "/order/{id}/checkOrderTechnicalStatus", method = RequestMethod.GET)
    public Boolean checkOrderTechnicalStatus(@PathVariable String id)
    {
        return scheduleService.checkOrderTechnicalStatus(id);
        
    }

    @RequestMapping(value = "/getReceivedSampleBySampleCode/{sampleCode}", method = RequestMethod.GET)
    public ReceivedSample getReceivedSampleBySampleCode(@PathVariable String sampleCode)
    {
        return scheduleService.getReceivedSampleBySampleCode(sampleCode);
    }

    @RequestMapping(value = "/getScheduleHistoryByTaskAndSchedule")
    public TestingScheduleHistory getScheduleHistoryByTaskAndSchedule(@RequestBody ScheduleHistoryRequest request)
    {
        return scheduleService.getScheduleHistoryByTaskAndSchedule(request);
    }

    @RequestMapping(value = "/getDNATaskByScheduleAndTime")
    public TestingTask getDNATaskByHistory(@RequestBody TestingScheduleHistory history)
    {
        return scheduleService.getDNATaskByHistory(history);
    }

    @RequestMapping(value = "/getSchedulesByTaskId/{taskId}")
    public List<TestingSchedule> getFamilyTaskInfo(@PathVariable String taskId)
    {
        return scheduleService.getScheduleHistorys(taskId);
    }

}
