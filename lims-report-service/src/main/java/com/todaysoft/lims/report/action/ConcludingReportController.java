package com.todaysoft.lims.report.action;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.report.model.ConcludingReportFormModel;
import com.todaysoft.lims.report.model.ConcludingReportModel;
import com.todaysoft.lims.report.model.ConcludingReportSearcher;
import com.todaysoft.lims.report.model.ConcludingReportUploadModel;
import com.todaysoft.lims.report.service.IConcludingReportService;

@RestController
@RequestMapping("/report/concludingReport")
public class ConcludingReportController
{
    @Autowired
    private IConcludingReportService service;
    
    @RequestMapping(value = "/assignedList", method = RequestMethod.POST)
    public Pagination<ConcludingReportModel> paging(@RequestBody ConcludingReportSearcher request)
    {
        
        Pagination<ConcludingReportModel> pagination = service.paging(request);
        return pagination;
    }
    
    @RequestMapping(value = "/getDetail/{orderId}", method = RequestMethod.GET)
    public ConcludingReportFormModel getDetail(@PathVariable String orderId)
    {
        ConcludingReportFormModel res = service.getDetail(orderId);
        return res;
    }
    
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public void upload(@RequestBody ConcludingReportUploadModel request, @RequestHeader(RequestHeaders.MEMBER_TOKEN) String token)
    {
        List<ConcludingReportModel> res = service.upload(request, token);
        for (ConcludingReportModel model : res)
        {
            //发送产品完成消息
            service.reportCallBackStatus(model.getOrderId(), model.getProductId(), "CONLUDING_REPORT");
        }
        
    }
    
    @RequestMapping(value = "/deleteReport/{fileCode}", method = RequestMethod.DELETE)
    public void deleteReport(@PathVariable String fileCode)
    {
        service.deleteReport(fileCode);
        
    }
    
}
