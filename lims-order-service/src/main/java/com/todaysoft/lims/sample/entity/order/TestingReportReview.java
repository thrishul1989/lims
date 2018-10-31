package com.todaysoft.lims.sample.entity.order;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_TESTING_REPORT_REVIEW")
public class TestingReportReview extends UuidKeyEntity
{
    
    /**
     * 
     */
    private static final long serialVersionUID = 4938254967774825084L;
    
    public static final String RESULT_TODO = "0";
    
    public static final String RESULT_YES = "1";
    
    public static final String RESULT_NO = "2";
    
    private TestingReport report;
    
    private String reviewNode;
    
    private String reviewResult;
    
    private String reviewOpinion;
    
    private String reviewerId;
    
    private String reviewerName;
    
    private Date reviewTime;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "REPORT_ID")
    @JsonBackReference
    public TestingReport getReport()
    {
        return report;
    }
    
    public void setReport(TestingReport report)
    {
        this.report = report;
    }
    
    @Column(name = "REVIEW_NODE")
    public String getReviewNode()
    {
        return reviewNode;
    }
    
    public void setReviewNode(String reviewNode)
    {
        this.reviewNode = reviewNode;
    }
    
    @Column(name = "REVIEW_RESULT")
    public String getReviewResult()
    {
        return reviewResult;
    }
    
    public void setReviewResult(String reviewResult)
    {
        this.reviewResult = reviewResult;
    }
    
    @Column(name = "REVIEW_OPINION")
    public String getReviewOpinion()
    {
        return reviewOpinion;
    }
    
    public void setReviewOpinion(String reviewOpinion)
    {
        this.reviewOpinion = reviewOpinion;
    }
    
    @Column(name = "REVIEWER_ID")
    public String getReviewerId()
    {
        return reviewerId;
    }
    
    public void setReviewerId(String reviewerId)
    {
        this.reviewerId = reviewerId;
    }
    
    @Column(name = "REVIEWER_NAME")
    public String getReviewerName()
    {
        return reviewerName;
    }
    
    public void setReviewerName(String reviewerName)
    {
        this.reviewerName = reviewerName;
    }
    
    @Column(name = "REVIEW_TIME")
    public Date getReviewTime()
    {
        return reviewTime;
    }
    
    public void setReviewTime(Date reviewTime)
    {
        this.reviewTime = reviewTime;
    }
}
