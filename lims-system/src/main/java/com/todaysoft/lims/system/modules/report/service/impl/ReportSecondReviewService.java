package com.todaysoft.lims.system.modules.report.service.impl;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.system.modules.report.model.SecondReviewSearcher;
import com.todaysoft.lims.system.modules.report.model.TestingReport;
import com.todaysoft.lims.system.modules.report.model.TestingReportReview;
import com.todaysoft.lims.system.modules.report.service.IReportSecondReviewService;
import com.todaysoft.lims.system.service.impl.GatewayService;
import com.todaysoft.lims.system.service.impl.RestService;

@Service
public class ReportSecondReviewService extends RestService implements IReportSecondReviewService
{
    
    @Override
    public Pagination<TestingReport> paging(SecondReviewSearcher searcher)
    {
        String url = GatewayService.getServiceUrl("/bpm/report/reportReview/pagingSecond");
        ResponseEntity<Pagination<TestingReport>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<SecondReviewSearcher>(searcher), new ParameterizedTypeReference<Pagination<TestingReport>>()
            {
            });
        return exchange.getBody();
    }
    
    @Override
    public void secondReview(TestingReportReview request)
    {
        String url = GatewayService.getServiceUrl("/bpm/report/reportReview/secondReview");
        template.postForObject(url, request, String.class);
    }
    
    @Override
    public TestingReport getReport(String id)
    {
        String url = GatewayService.getServiceUrl("/bpm/report/reportReview/{id}");
        return template.getForObject(url, TestingReport.class, id);
    }
    
}
