package com.todaysoft.lims.invoice.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;


import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_TESTING_REPORT")
public class TestingReport extends UuidKeyEntity implements Serializable
{
    
    public static int REPORT_STATUS_WAIT_DATA = 0;//带数据整合
    
    public static int REPORT_STATUS_ABLE_REPORT = 1;//可出报告
    
    public static int REPORT_STATUS_COMPLETED = 2;//已出报告
    
    public static String REPORT_NO = "REPORT-NO";//已出报告
    
    private static final long serialVersionUID = -1549652317268723301L;
    
    private String orderId;
    
    private String orderCode;
    
    private String productId;
    
    private String productCode;
    
    private String productName;
    
    private int status;
    
    private String reportCode;
    
    private String reportName;
    
    private String firstReviewStatus;
    
    private String secondReviewStatus;
    
    private String emailStatus;//报告寄送状态
    
    @Column(name = "ORDER_ID")
    public String getOrderId()
    {
        return orderId;
    }
    
    public void setOrderId(String orderId)
    {
        this.orderId = orderId;
    }
    
    @Column(name = "ORDER_CODE")
    public String getOrderCode()
    {
        return orderCode;
    }
    
    public void setOrderCode(String orderCode)
    {
        this.orderCode = orderCode;
    }
    
    @Column(name = "PRODUCT_ID")
    public String getProductId()
    {
        return productId;
    }
    
    public void setProductId(String productId)
    {
        this.productId = productId;
    }
    
    @Column(name = "PRODUCT_CODE")
    public String getProductCode()
    {
        return productCode;
    }
    
    public void setProductCode(String productCode)
    {
        this.productCode = productCode;
    }
    
    @Column(name = "PRODUCT_NAME")
    public String getProductName()
    {
        return productName;
    }
    
    public void setProductName(String productName)
    {
        this.productName = productName;
    }
    
    @Column(name = "STATUS")
    public int getStatus()
    {
        return status;
    }
    
    public void setStatus(int status)
    {
        this.status = status;
    }
    
    @Column(name = "REPORT_CODE")
    public String getReportCode()
    {
        return reportCode;
    }
    
    public void setReportCode(String reportCode)
    {
        this.reportCode = reportCode;
    }
    
    @Column(name = "REPORT_NAME")
    public String getReportName()
    {
        return reportName;
    }
    
    public void setReportName(String reportName)
    {
        this.reportName = reportName;
    }
    
    @Column(name = "FIRST_REVIEW_STATUS")
    public String getFirstReviewStatus()
    {
        return firstReviewStatus;
    }
    
    public void setFirstReviewStatus(String firstReviewStatus)
    {
        this.firstReviewStatus = firstReviewStatus;
    }
    
    @Column(name = "SECOND_REVIEW_STATUS")
    public String getSecondReviewStatus()
    {
        return secondReviewStatus;
    }
    
    public void setSecondReviewStatus(String secondReviewStatus)
    {
        this.secondReviewStatus = secondReviewStatus;
    }
    
    @Transient
    public String getEmailStatus()
    {
        return emailStatus;
    }

    public void setEmailStatus(String emailStatus)
    {
        this.emailStatus = emailStatus;
    }
}
