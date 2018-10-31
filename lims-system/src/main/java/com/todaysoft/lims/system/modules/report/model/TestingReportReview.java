package com.todaysoft.lims.system.modules.report.model;

import java.util.Date;

public class TestingReportReview
{
    public static final String REVIEW_FIRST = "1";
    
    public static final String REVIEW_SECOND = "2";
    
    public static final String RESULT_TODO = "0";
    
    public static final String RESULT_YES = "1";
    
    public static final String RESULT_NO = "2";
    
    private String id;
    
    private TestingReport report;
    
    private String reviewNode;
    
    private String reviewResult;
    
    private String reviewOpinion;
    
    private String reviewerId;
    
    private String reviewerName;
    
    private Date reviewTime;
    
    public TestingReportReview()
    {
        
    }
    
    public TestingReportReview(TestingReport report, String reviewNode, String reviewResult, String reviewOpinion, String reviewerId, String reviewerName,
        Date reviewTime)
    {
        super();
        this.report = report;
        this.reviewNode = reviewNode;
        this.reviewResult = reviewResult;
        this.reviewOpinion = reviewOpinion;
        this.reviewerId = reviewerId;
        this.reviewerName = reviewerName;
        this.reviewTime = reviewTime;
    }
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public TestingReport getReport()
    {
        return report;
    }
    
    public void setReport(TestingReport report)
    {
        this.report = report;
    }
    
    public String getReviewNode()
    {
        return reviewNode;
    }
    
    public void setReviewNode(String reviewNode)
    {
        this.reviewNode = reviewNode;
    }
    
    public String getReviewResult()
    {
        return reviewResult;
    }
    
    public void setReviewResult(String reviewResult)
    {
        this.reviewResult = reviewResult;
    }
    
    public String getReviewOpinion()
    {
        return reviewOpinion;
    }
    
    public void setReviewOpinion(String reviewOpinion)
    {
        this.reviewOpinion = reviewOpinion;
    }
    
    public String getReviewerId()
    {
        return reviewerId;
    }
    
    public void setReviewerId(String reviewerId)
    {
        this.reviewerId = reviewerId;
    }
    
    public String getReviewerName()
    {
        return reviewerName;
    }
    
    public void setReviewerName(String reviewerName)
    {
        this.reviewerName = reviewerName;
    }
    
    public Date getReviewTime()
    {
        return reviewTime;
    }
    
    public void setReviewTime(Date reviewTime)
    {
        this.reviewTime = reviewTime;
    }
}
