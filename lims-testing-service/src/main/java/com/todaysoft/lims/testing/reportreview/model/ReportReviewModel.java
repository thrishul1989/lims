package com.todaysoft.lims.testing.reportreview.model;

import java.util.Date;

public class ReportReviewModel
{
    private String reviewNode;
    
    private String reviewerName;
    
    private String reviewResult;
    
    private String reviewOpinion;
    
    private Date reviewTime;

    public String getReviewNode()
    {
        return reviewNode;
    }

    public void setReviewNode(String reviewNode)
    {
        this.reviewNode = reviewNode;
    }

    public String getReviewerName()
    {
        return reviewerName;
    }

    public void setReviewerName(String reviewerName)
    {
        this.reviewerName = reviewerName;
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

    public Date getReviewTime()
    {
        return reviewTime;
    }

    public void setReviewTime(Date reviewTime)
    {
        this.reviewTime = reviewTime;
    }
    
}
