package com.todaysoft.lims.testing.reportreview.action;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.testing.base.entity.TestingReport;
import com.todaysoft.lims.testing.base.entity.TestingReportReview;
import com.todaysoft.lims.testing.ons.IMessageProducer;
import com.todaysoft.lims.testing.reportreview.model.CompletedReportSearcher;
import com.todaysoft.lims.testing.reportreview.model.FirstReviewSearcher;
import com.todaysoft.lims.testing.reportreview.model.ReportReviewModel;
import com.todaysoft.lims.testing.reportreview.model.SecondReviewSearcher;
import com.todaysoft.lims.testing.reportreview.service.IReportReviewService;

@RestController
@RequestMapping("/bpm/report/reportReview")
public class ReportReviewAction
{
    @Autowired
    private IReportReviewService service;
    
    @Autowired
    private IMessageProducer producer;
    
    @RequestMapping(value = "/pagingFirst", method = RequestMethod.POST)
    public Pagination<TestingReport> pagingFirst(@RequestBody FirstReviewSearcher searcher)
    {
        return service.pagingFirst(searcher);
    }
    
    @RequestMapping(value = "/pagingSecond", method = RequestMethod.POST)
    public Pagination<TestingReport> pagingSecond(@RequestBody SecondReviewSearcher searcher)
    {
        return service.pagingSecond(searcher);
    }
    
    @RequestMapping(value = "/pagingCompletedReport", method = RequestMethod.POST)
    public Pagination<TestingReport> pagingCompletedReport(@RequestBody CompletedReportSearcher searcher)
    {
        return service.pagingCompletedReport(searcher);
    }
    
    @RequestMapping(value = "/firstReview", method = RequestMethod.POST)
    public void firstReview(@RequestBody TestingReportReview request)
    {
        Map<String,String> orderProductId = service.firstReview(request);
        //审核不通过
        if("2".equals(request.getReviewResult()))
        {
            producer.sendReportVerifyMessage(orderProductId,request.getReviewResult());
        }
    }
    
    @RequestMapping(value = "/secondReview", method = RequestMethod.POST)
    public void secondReview(@RequestBody TestingReportReview request)
    {
        Map<String,String> orderProductId = service.secondReview(request);
        producer.sendReportVerifyMessage(orderProductId,request.getReviewResult());
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public TestingReport getReport(@PathVariable String id)
    {
        return service.getReport(id);
    }
    
    
    @RequestMapping(value = "/getReportReviewModelByReportId/{reportId}", method = RequestMethod.GET)
    public List<ReportReviewModel> getReportReviewModelByReportId(@PathVariable String reportId)
    {
        return service.getReportReviewModelByReportId(reportId);
    }
}
