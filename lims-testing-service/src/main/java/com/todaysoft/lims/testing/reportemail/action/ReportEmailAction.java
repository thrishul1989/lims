package com.todaysoft.lims.testing.reportemail.action;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.testing.base.action.RequestHeaders;
import com.todaysoft.lims.testing.base.entity.TestingReportEmail;
import com.todaysoft.lims.testing.ons.IMessageProducer;
import com.todaysoft.lims.testing.reportemail.model.ReportEmailAssignRequest;
import com.todaysoft.lims.testing.reportemail.model.ReportEmailForOrderStatusModel;
import com.todaysoft.lims.testing.reportemail.model.ReportEmailModel;
import com.todaysoft.lims.testing.reportemail.model.ReportEmailSearcher;
import com.todaysoft.lims.testing.reportemail.model.WhetherEmailModel;
import com.todaysoft.lims.testing.reportemail.service.IReportEmailService;

@RestController
@RequestMapping("/report/reportEmail")
public class ReportEmailAction
{
    @Autowired
    private IReportEmailService service;
    
    @Autowired
    private IMessageProducer producer;
    
    @RequestMapping(value = "/assignedList", method = RequestMethod.POST)
    public Pagination<ReportEmailModel> paging(@RequestBody ReportEmailSearcher request)
    {
        Pagination<ReportEmailModel> pagination = service.paging(request);
        return pagination;
    }
    
    @RequestMapping(value = "/assign", method = RequestMethod.POST)
    public Integer assign(@RequestBody ReportEmailAssignRequest request, @RequestHeader(RequestHeaders.MEMBER_TOKEN) String token)
    {
        return service.assign(request, token);
        
    }
    
    @RequestMapping(value = "/whetherCanEmail", method = RequestMethod.POST)
    public WhetherEmailModel whetherCanEmail(@RequestBody WhetherEmailModel request)
    {
        return service.whetherCanEmail(request);
        
    }
    
    @RequestMapping(value = "/getByIds/{reportEmailIds}", method = RequestMethod.POST)
    public List<ReportEmailModel> getByIds(@PathVariable String reportEmailIds)
    {
        List<ReportEmailModel> list = service.getByIds(reportEmailIds);
        return list;
    }
    
    @RequestMapping(value = "/getById/{reportEmailId}", method = RequestMethod.GET)
    public TestingReportEmail getById(@PathVariable String reportEmailId)
    {
        TestingReportEmail model = service.getById(reportEmailId);
        return model;
    }
    
    @RequestMapping(value = "/getByOrderId/{orderId}", method = RequestMethod.POST)
    public List<ReportEmailModel> getByOrderId(@PathVariable String orderId)
    {
        List<ReportEmailModel> list = service.getByOrderId(orderId);
        return list;
    }
    
    @RequestMapping(value = "/reportEmail", method = RequestMethod.POST)
    public void reportEmail(@RequestBody ReportEmailModel request)
    {
        List<Map<String, String>> orderProductIds = service.reportEmail(request);
        if (StringUtils.isNotEmpty(request.getReportEmailIds()))
        {
            for (Map<String, String> m : orderProductIds)
            {
                for (String orderId : m.keySet())
                {
                    String productId = m.get(orderId);
                    service.sendEmailcallBackOrderStatus(orderId, productId, service.REPORT_EMAIL_TAG);
                }
                
            }
            
        }
        producer.sendReportDeliverMessage(orderProductIds);
        
    }
    
    @RequestMapping(value = "/noReport/{reportEmailIds}", method = RequestMethod.DELETE)
    public void noReport(@PathVariable String reportEmailIds)
    {
        List<Map<String, String>> orderProductIds = service.noReport(reportEmailIds);
        if (StringUtils.isNotEmpty(reportEmailIds))
        {
            for (Map<String, String> m : orderProductIds)
            {
                for (String orderId : m.keySet())
                {
                    String productId = m.get(orderId);
                    service.sendEmailcallBackOrderStatus(orderId, productId, service.REPORT_EMAIL_TAG);
                }
                
            }
        }
        producer.sendReportDeliverMessage(orderProductIds);
    }
    
    @RequestMapping(value = "/createReport/{orderId}", method = RequestMethod.DELETE)
    public void createReport(@PathVariable String orderId)
    {
        service.createReport(orderId);
    }
    
    @RequestMapping(value = "/getListForStatusByOrderId/{orderId}", method = RequestMethod.GET)
    public List<ReportEmailForOrderStatusModel> getListForStatusByOrderId(@PathVariable String orderId)
    {
        List<ReportEmailForOrderStatusModel> list = service.getListForStatusByOrderId(orderId);
        return list;
    }
}
