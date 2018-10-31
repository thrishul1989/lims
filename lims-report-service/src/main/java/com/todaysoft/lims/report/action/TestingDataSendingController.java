package com.todaysoft.lims.report.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.report.dao.searcher.TestingDataSendSearcher;
import com.todaysoft.lims.report.model.ConcludingReportModel;
import com.todaysoft.lims.report.model.TestingDataSendFormModel;
import com.todaysoft.lims.report.model.TestingDataSendModel;
import com.todaysoft.lims.report.service.IConcludingReportService;
import com.todaysoft.lims.report.service.ITestingDataSendService;

@RestController
@RequestMapping("/report/testingDataSend")
public class TestingDataSendingController
{
    @Autowired
    private IConcludingReportService concludingService;
    
    @Autowired
    private ITestingDataSendService service;
    
    @RequestMapping(value = "/assignedList", method = RequestMethod.POST)
    public Pagination<TestingDataSendModel> paging(@RequestBody TestingDataSendSearcher request)
    {
        Pagination<TestingDataSendModel> pagination = service.paging(request);
        return pagination;
    }
    
    @RequestMapping(value = "/getDetail/{orderId}", method = RequestMethod.GET)
    public List<TestingDataSendFormModel> getDetail(@PathVariable String orderId)
    {
        List<TestingDataSendFormModel> list = service.getDetail(orderId);
        return list;
    }
    
    @RequestMapping(value = "/send/{dataSendIds}", method = RequestMethod.POST)
    public void send(@PathVariable String dataSendIds, @RequestHeader(RequestHeaders.MEMBER_TOKEN) String token)
    {
        List<TestingDataSendModel> res = service.send(dataSendIds, token);
        for (TestingDataSendModel model : res)
        {
            //发送产品完成消息
            concludingService.reportCallBackStatus(model.getOrderId(), model.getProductId(), "DATA_SEND");
        }
    }
    
}
