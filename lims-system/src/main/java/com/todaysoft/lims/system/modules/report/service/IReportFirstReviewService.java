package com.todaysoft.lims.system.modules.report.service;

import java.util.List;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.system.modules.report.model.FirstReviewSearcher;
import com.todaysoft.lims.system.modules.report.model.ReportReviewModel;
import com.todaysoft.lims.system.modules.report.model.TestingReport;
import com.todaysoft.lims.system.modules.report.model.TestingReportReview;

public interface IReportFirstReviewService
{
    Pagination<TestingReport> paging(FirstReviewSearcher searcher);
    
    void firstReview(TestingReportReview request);
    
    TestingReport getReport(String id);
    
    List<ReportReviewModel> getReportReviewModelByReportId(String reportId);
}
