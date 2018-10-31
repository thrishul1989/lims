package com.todaysoft.lims.system.model.vo.order.orderReportForm;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.todaysoft.lims.persist.UuidKeyEntity;

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
    
    public String getTaskId()
    {
        return taskId;
    }
    public void setTaskId(String taskId)
    {
        this.taskId = taskId;
    }
    
    public ReportSystemOrderInfo getOrderInfo()
    {
        return orderInfo;
    }
    public void setOrderInfo(ReportSystemOrderInfo orderInfo)
    {
        this.orderInfo = orderInfo;
    }
    
    public String getProductName()
    {
        return productName;
    }
    public void setProductName(String productName)
    {
        this.productName = productName;
    }
    
    public String getRefundStatus()
    {
        return refundStatus;
    }
    public void setRefundStatus(String refundStatus)
    {
        this.refundStatus = refundStatus;
    }
    
    public String getProductStatus()
    {
        return productStatus;
    }
    public void setProductStatus(String productStatus)
    {
        this.productStatus = productStatus;
    }
    
    public String getLanes()
    {
        return lanes;
    }
    public void setLanes(String lanes)
    {
        this.lanes = lanes;
    }
    
    public String getProductCode()
    {
        return productCode;
    }
    public void setProductCode(String productCode)
    {
        this.productCode = productCode;
    }
    
    public String getProductPrice()
    {
        return productPrice;
    }
    public void setProductPrice(String productPrice)
    {
        this.productPrice = productPrice;
    }
    
    public String getReplyAmount()
    {
        return replyAmount;
    }
    public void setReplyAmount(String replyAmount)
    {
        this.replyAmount = replyAmount;
    }
    
    public String getReplyReason()
    {
        return replyReason;
    }
    public void setReplyReason(String replyReason)
    {
        this.replyReason = replyReason;
    }
    
    public Date getApplyTime()
    {
        return applyTime;
    }
    public void setApplyTime(Date applyTime)
    {
        this.applyTime = applyTime;
    }
    
    public Date getReplyCheckTime()
    {
        return replyCheckTime;
    }
    public void setReplyCheckTime(Date replyCheckTime)
    {
        this.replyCheckTime = replyCheckTime;
    }
    
    public String getReportNo()
    {
        return reportNo;
    }
    public void setReportNo(String reportNo)
    {
        this.reportNo = reportNo;
    }
    
    public String getReportName()
    {
        return reportName;
    }
    public void setReportName(String reportName)
    {
        this.reportName = reportName;
    }
    
    public Date getReportDate()
    {
        return reportDate;
    }
    public void setReportDate(Date reportDate)
    {
        this.reportDate = reportDate;
    }
    
    public String getReportReceiverName()
    {
        return reportReceiverName;
    }
    public void setReportReceiverName(String reportReceiverName)
    {
        this.reportReceiverName = reportReceiverName;
    }
    
    public Date getFirstCheckTime()
    {
        return firstCheckTime;
    }
    public void setFirstCheckTime(Date firstCheckTime)
    {
        this.firstCheckTime = firstCheckTime;
    }
    
    public String getFirstCheckName()
    {
        return firstCheckName;
    }
    public void setFirstCheckName(String firstCheckName)
    {
        this.firstCheckName = firstCheckName;
    }
    
    public Date getSecondCheckTime()
    {
        return secondCheckTime;
    }
    public void setSecondCheckTime(Date secondCheckTime)
    {
        this.secondCheckTime = secondCheckTime;
    }
    
    public String getSecondCheckName()
    {
        return secondCheckName;
    }
    public void setSecondCheckName(String secondCheckName)
    {
        this.secondCheckName = secondCheckName;
    }
    
    public String getReportStatus()
    {
        return reportStatus;
    }
    public void setReportStatus(String reportStatus)
    {
        this.reportStatus = reportStatus;
    }
    
    public String getTransportStatus()
    {
        return transportStatus;
    }
    public void setTransportStatus(String transportStatus)
    {
        this.transportStatus = transportStatus;
    }
    
    public String getTransportContent()
    {
        return transportContent;
    }
    public void setTransportContent(String transportContent)
    {
        this.transportContent = transportContent;
    }
    
    public String getTrackType()
    {
        return trackType;
    }
    public void setTrackType(String trackType)
    {
        this.trackType = trackType;
    }
    
    public String getTrackNumber()
    {
        return trackNumber;
    }
    public void setTrackNumber(String trackNumber)
    {
        this.trackNumber = trackNumber;
    }
    
    public String getTransportName()
    {
        return transportName;
    }
    public void setTransportName(String transportName)
    {
        this.transportName = transportName;
    }
    
    public Date getTransportTime()
    {
        return transportTime;
    }
    public void setTransportTime(Date transportTime)
    {
        this.transportTime = transportTime;
    }
    
    
}
