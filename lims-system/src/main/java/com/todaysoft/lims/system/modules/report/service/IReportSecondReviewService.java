package com.todaysoft.lims.system.modules.report.service;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.system.modules.report.model.SecondReviewSearcher;
import com.todaysoft.lims.system.modules.report.model.TestingReport;
import com.todaysoft.lims.system.modules.report.model.TestingReportReview;

public interface IReportSecondReviewService
{
    Pagination<TestingReport> paging(SecondReviewSearcher searcher);
    
    void secondReview(TestingReportReview request);
    
    TestingReport getReport(String id);
}
