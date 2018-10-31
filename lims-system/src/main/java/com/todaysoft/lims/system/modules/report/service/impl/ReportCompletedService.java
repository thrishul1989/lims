package com.todaysoft.lims.system.modules.report.service.impl;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.system.modules.report.model.CompletedReportSearcher;
import com.todaysoft.lims.system.modules.report.model.TestingReport;
import com.todaysoft.lims.system.modules.report.service.IReportCompletedService;
import com.todaysoft.lims.system.service.impl.GatewayService;
import com.todaysoft.lims.system.service.impl.RestService;

@Service
public class ReportCompletedService extends RestService implements IReportCompletedService
{
    
    @Override
    public Pagination<TestingReport> paging(CompletedReportSearcher searcher)
    {
        String url = GatewayService.getServiceUrl("/bpm/report/reportReview/pagingCompletedReport");
        ResponseEntity<Pagination<TestingReport>> exchange =
            template.exchange(url,
                HttpMethod.POST,
                new HttpEntity<CompletedReportSearcher>(searcher),
                new ParameterizedTypeReference<Pagination<TestingReport>>()
                {
                });
        return exchange.getBody();
    }
    
    @Override
    public TestingReport getReport(String id)
    {
        String url = GatewayService.getServiceUrl("/bpm/report/reportReview/{id}");
        return template.getForObject(url, TestingReport.class, id);
    }
    
}
