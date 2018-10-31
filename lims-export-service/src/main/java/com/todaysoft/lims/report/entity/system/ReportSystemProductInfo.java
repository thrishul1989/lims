package com.todaysoft.lims.report.entity.system;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "REPORT_SYSTEM_PRODUCT_INFO")
public class ReportSystemProductInfo extends UuidKeyEntity
{
    private static final long serialVersionUID = 1975806243574800094L;
    
    private String taskId;
    //private String orderId;
    private String productName;
    private String refundStatus;
    private String productStatus;
    private String lanes;
    private String productCode;
    private String productPrice;
    private String replyAmount;
    private String replyReason;
    private Date applyTime;
    private Date replyCheckTime;
    private String reportNo;
    private String reportName;
    private Date reportDate;
    private String reportReceiverName;
    private Date firstCheckTime;
    private String firstCheckName;
    private Date secondCheckTime;
    private String secondCheckName;
    private String reportStatus;
    private String transportStatus;
    private String transportContent;
    private String trackType;
    private String trackNumber;
    private String transportName;
    private Date transportTime;
    private ReportSystemOrderInfo orderInfo;
    
    @Column(name = "TASK_ID")
    public String getTaskId()
    {
        return taskId;
    }
    public void setTaskId(String taskId)
    {
        this.taskId = taskId;
    }
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORDER_ID")
    @JsonIgnore
    public ReportSystemOrderInfo getOrderInfo()
    {
        return orderInfo;
    }
    public void setOrderInfo(ReportSystemOrderInfo orderInfo)
    {
        this.orderInfo = orderInfo;
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
    
    @Column(name = "REFUND_STATUS")
    public String getRefundStatus()
    {
        return refundStatus;
    }
    public void setRefundStatus(String refundStatus)
    {
        this.refundStatus = refundStatus;
    }
    
    @Column(name = "PRODUCT_STATUS")
    public String getProductStatus()
    {
        return productStatus;
    }
    public void setProductStatus(String productStatus)
    {
        this.productStatus = productStatus;
    }
    
    @Column(name = "LANES")
    public String getLanes()
    {
        return lanes;
    }
    public void setLanes(String lanes)
    {
        this.lanes = lanes;
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
    
    @Column(name = "PRODUCT_PRICE")
    public String getProductPrice()
    {
        return productPrice;
    }
    public void setProductPrice(String productPrice)
    {
        this.productPrice = productPrice;
    }
    
    @Column(name = "REPLY_AMOUNT")
    public String getReplyAmount()
    {
        return replyAmount;
    }
    public void setReplyAmount(String replyAmount)
    {
        this.replyAmount = replyAmount;
    }
    
    @Column(name = "REPLY_REASON")
    public String getReplyReason()
    {
        return replyReason;
    }
    public void setReplyReason(String replyReason)
    {
        this.replyReason = replyReason;
    }
    
    @Column(name = "APPLY_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getApplyTime()
    {
        return applyTime;
    }
    public void setApplyTime(Date applyTime)
    {
        this.applyTime = applyTime;
    }
    
    @Column(name = "REPLY_CHECK_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getReplyCheckTime()
    {
        return replyCheckTime;
    }
    public void setReplyCheckTime(Date replyCheckTime)
    {
        this.replyCheckTime = replyCheckTime;
    }
    
    @Column(name = "REPORT_NO")
    public String getReportNo()
    {
        return reportNo;
    }
    public void setReportNo(String reportNo)
    {
        this.reportNo = reportNo;
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
    
    @Column(name = "REPORT_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getReportDate()
    {
        return reportDate;
    }
    public void setReportDate(Date reportDate)
    {
        this.reportDate = reportDate;
    }
    
    @Column(name = "REPORT_RECEIVER_NAME")
    public String getReportReceiverName()
    {
        return reportReceiverName;
    }
    public void setReportReceiverName(String reportReceiverName)
    {
        this.reportReceiverName = reportReceiverName;
    }
    
    @Column(name = "FIRST_CHECK_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getFirstCheckTime()
    {
        return firstCheckTime;
    }
    public void setFirstCheckTime(Date firstCheckTime)
    {
        this.firstCheckTime = firstCheckTime;
    }
    
    @Column(name = "FIRST_CHECK_NAME")
    public String getFirstCheckName()
    {
        return firstCheckName;
    }
    public void setFirstCheckName(String firstCheckName)
    {
        this.firstCheckName = firstCheckName;
    }
    
    @Column(name = "SECOND_CHECK_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getSecondCheckTime()
    {
        return secondCheckTime;
    }
    public void setSecondCheckTime(Date secondCheckTime)
    {
        this.secondCheckTime = secondCheckTime;
    }
    
    @Column(name = "SECOND_CHECK_NAME")
    public String getSecondCheckName()
    {
        return secondCheckName;
    }
    public void setSecondCheckName(String secondCheckName)
    {
        this.secondCheckName = secondCheckName;
    }
    
    @Column(name = "REPORT_STATUS")
    public String getReportStatus()
    {
        return reportStatus;
    }
    public void setReportStatus(String reportStatus)
    {
        this.reportStatus = reportStatus;
    }
    
    @Column(name = "TRANSPORT_STATUS")
    public String getTransportStatus()
    {
        return transportStatus;
    }
    public void setTransportStatus(String transportStatus)
    {
        this.transportStatus = transportStatus;
    }
    
    @Column(name = "TRANSPORT_CONTENT")
    public String getTransportContent()
    {
        return transportContent;
    }
    public void setTransportContent(String transportContent)
    {
        this.transportContent = transportContent;
    }
    
    @Column(name = "TRACK_TYPE")
    public String getTrackType()
    {
        return trackType;
    }
    public void setTrackType(String trackType)
    {
        this.trackType = trackType;
    }
    
    @Column(name = "TRACK_NUMBER")
    public String getTrackNumber()
    {
        return trackNumber;
    }
    public void setTrackNumber(String trackNumber)
    {
        this.trackNumber = trackNumber;
    }
    
    @Column(name = "TRANSPORT_NAME")
    public String getTransportName()
    {
        return transportName;
    }
    public void setTransportName(String transportName)
    {
        this.transportName = transportName;
    }
    
    @Column(name = "TRANSPORT_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getTransportTime()
    {
        return transportTime;
    }
    public void setTransportTime(Date transportTime)
    {
        this.transportTime = transportTime;
    }
    
    
}
