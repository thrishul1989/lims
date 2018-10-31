package com.todaysoft.lims.invoice.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.todaysoft.lims.invoice.entity.InvoiceInfo;
import com.todaysoft.lims.invoice.entity.TestingReport;

public class InvoiceWatingSend
{
    private String id;
    
    private String category;//任务类型：1-默认开票 2-申请开票
    
    private String recordKey;//默认开票订单ID/申请开票记录ID
    
    private String institution;//开票机构
    
    private BigDecimal amount;//开票金额
    
    private Integer status;//状态：1-待开票 2-可开票 3-已开票
    
    private List<InvoiceInfo> invoiceInfos;//开票信息
    
    private String recipientName;
    
    private String recipientPhone;
    
    private String recipientAddress;
    
    private String threeCon;//收件三信息
    
    private Date sendTime;
    
    private String trackNumber;
    
    private String transportType;
    
    private String transportName;
    
    private String transportPhone;
    
    private String sendDetail;
    
    private List<TestingReport> reportList;
    
    private String invoiceNos;//发票号,连接
    
    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getRecordKey()
    {
        return recordKey;
    }

    public void setRecordKey(String recordKey)
    {
        this.recordKey = recordKey;
    }

    public String getInstitution()
    {
        return institution;
    }

    public void setInstitution(String institution)
    {
        this.institution = institution;
    }

    public BigDecimal getAmount()
    {
        return amount;
    }

    public void setAmount(BigDecimal amount)
    {
        this.amount = amount;
    }

    public Integer getStatus()
    {
        return status;
    }

    public void setStatus(Integer status)
    {
        this.status = status;
    }

    public String getCategory()
    {
        return category;
    }

    public void setCategory(String category)
    {
        this.category = category;
    }

    public List<InvoiceInfo> getInvoiceInfos()
    {
        return invoiceInfos;
    }

    public void setInvoiceInfos(List<InvoiceInfo> invoiceInfos)
    {
        this.invoiceInfos = invoiceInfos;
    }

    public String getRecipientName()
    {
        return recipientName;
    }

    public void setRecipientName(String recipientName)
    {
        this.recipientName = recipientName;
    }

    public String getRecipientPhone()
    {
        return recipientPhone;
    }

    public void setRecipientPhone(String recipientPhone)
    {
        this.recipientPhone = recipientPhone;
    }

    public String getRecipientAddress()
    {
        return recipientAddress;
    }

    public void setRecipientAddress(String recipientAddress)
    {
        this.recipientAddress = recipientAddress;
    }

    public String getThreeCon()
    {
        return threeCon;
    }

    public void setThreeCon(String threeCon)
    {
        this.threeCon = threeCon;
    }

    public Date getSendTime()
    {
        return sendTime;
    }

    public void setSendTime(Date sendTime)
    {
        this.sendTime = sendTime;
    }

    public String getTrackNumber()
    {
        return trackNumber;
    }

    public void setTrackNumber(String trackNumber)
    {
        this.trackNumber = trackNumber;
    }

    public String getTransportType()
    {
        return transportType;
    }

    public void setTransportType(String transportType)
    {
        this.transportType = transportType;
    }

    public String getTransportName()
    {
        return transportName;
    }

    public void setTransportName(String transportName)
    {
        this.transportName = transportName;
    }

    public String getTransportPhone()
    {
        return transportPhone;
    }

    public void setTransportPhone(String transportPhone)
    {
        this.transportPhone = transportPhone;
    }

    public String getSendDetail()
    {
        return sendDetail;
    }

    public void setSendDetail(String sendDetail)
    {
        this.sendDetail = sendDetail;
    }

    public List<TestingReport> getReportList()
    {
        return reportList;
    }

    public void setReportList(List<TestingReport> reportList)
    {
        this.reportList = reportList;
    }

    public String getInvoiceNos()
    {
        return invoiceNos;
    }

    public void setInvoiceNos(String invoiceNos)
    {
        this.invoiceNos = invoiceNos;
    }
    
}
