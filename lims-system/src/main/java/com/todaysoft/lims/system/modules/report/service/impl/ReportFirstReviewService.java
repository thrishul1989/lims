package com.todaysoft.lims.system.modules.report.service.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.system.modules.report.model.TestingReport;
import com.todaysoft.lims.system.modules.report.model.TestingReportReview;
import com.todaysoft.lims.system.model.vo.order.OrderPaymentModel;
import com.todaysoft.lims.system.modules.report.model.FirstReviewSearcher;
import com.todaysoft.lims.system.modules.report.model.ReportReviewModel;
import com.todaysoft.lims.system.modules.report.service.IReportFirstReviewService;
import com.todaysoft.lims.system.service.impl.GatewayService;
import com.todaysoft.lims.system.service.impl.RestService;

@Service
public class ReportFirstReviewService extends RestService implements IReportFirstReviewService
{
    
    @Override
    public Pagination<TestingReport> paging(FirstReviewSearcher searcher)
    {
        String url = GatewayService.getServiceUrl("/bpm/report/reportReview/pagingFirst");
        ResponseEntity<Pagination<TestingReport>> exchange =
            template.exchange(url,
                HttpMethod.POST,
                new HttpEntity<FirstReviewSearcher>(searcher),
                new ParameterizedTypeReference<Pagination<TestingReport>>()
                {
                });
        return exchange.getBody();
    }
    
    @Override
    public void firstReview(TestingReportReview request)
    {
        String url = GatewayService.getServiceUrl("/bpm/report/reportReview/firstReview");
        template.postForObject(url, request, String.class);
    }
    
    @Override
    public TestingReport getReport(String id)
    {
        String url = GatewayService.getServiceUrl("/bpm/report/reportReview/{id}");
        return template.getForObject(url, TestingReport.class, id);
    }

    @Override
    public List<ReportReviewModel> getReportReviewModelByReportId(String reportId)
    {
        String url = GatewayService.getServiceUrl("/bpm/report/reportReview/getReportReviewModelByReportId/{reportId}");
        ResponseEntity<List<ReportReviewModel>> exchange =
            template.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<ReportReviewModel>>()
            {
            }, Collections.singletonMap("reportId", reportId));
        return exchange.getBody();
    }
    
}
