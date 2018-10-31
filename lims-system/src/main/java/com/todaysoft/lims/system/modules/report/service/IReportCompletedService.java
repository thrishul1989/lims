package com.todaysoft.lims.system.modules.report.service;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.system.modules.report.model.CompletedReportSearcher;
import com.todaysoft.lims.system.modules.report.model.TestingReport;

public interface IReportCompletedService
{
    Pagination<TestingReport> paging(CompletedReportSearcher searcher);
    
    TestingReport getReport(String id);
}
