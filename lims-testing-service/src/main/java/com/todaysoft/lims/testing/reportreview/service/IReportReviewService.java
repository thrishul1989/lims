package com.todaysoft.lims.testing.reportreview.service;

import java.util.List;
import java.util.Map;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.testing.base.entity.TestingReport;
import com.todaysoft.lims.testing.base.entity.TestingReportReview;
import com.todaysoft.lims.testing.reportreview.model.CompletedReportSearcher;
import com.todaysoft.lims.testing.reportreview.model.FirstReviewSearcher;
import com.todaysoft.lims.testing.reportreview.model.ReportReviewModel;
import com.todaysoft.lims.testing.reportreview.model.SecondReviewSearcher;

public interface IReportReviewService
{
    Pagination<TestingReport> pagingFirst(FirstReviewSearcher searcher);
    
    Pagination<TestingReport> pagingSecond(SecondReviewSearcher searcher);
    
    Pagination<TestingReport> pagingCompletedReport(CompletedReportSearcher searcher);
    
    Map<String,String> firstReview(TestingReportReview request);
    
    Map<String,String> secondReview(TestingReportReview request);
    
    void createReview(TestingReportReview request);
    
    TestingReport getReport(String id);
    
    List<ReportReviewModel> getReportReviewModelByReportId(String reportId);
}
