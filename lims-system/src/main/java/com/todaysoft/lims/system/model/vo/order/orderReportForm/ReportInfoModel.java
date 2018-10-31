package com.todaysoft.lims.system.model.vo.order.orderReportForm;

import java.util.Date;

public class ReportInfoModel
{
  //---------报告信息-----------
    private String reportNo;//报告编号
    private String reportName;//报告名称
    private String reportSampleName;//样本编号
    private String reportProductName;//检测产品
    private String reportDate;//出报告时间
    private String reportReceiverName;//出报告人
    private String firstCheckTime;//一审完成时间
    private String firstCheckName;//一审审核人
    private String secondCheckTime;//二审完成时间
    private String secondCheckName;//二审完成时间
    private String reportStatus;//状态
    private String transportStatus;//寄送状态
    private String transportContent;//寄送内容
    private String trackType;//快递类别
    private String trackNumber;//快递单号
    private String transportName;//寄送人
    private String transportTime;//寄送时间
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
    public String getReportSampleName()
    {
        return reportSampleName;
    }
    public void setReportSampleName(String reportSampleName)
    {
        this.reportSampleName = reportSampleName;
    }
    public String getReportProductName()
    {
        return reportProductName;
    }
    public void setReportProductName(String reportProductName)
    {
        this.reportProductName = reportProductName;
    }
    public String getReportDate()
    {
        return reportDate;
    }
    public void setReportDate(String reportDate)
    {
        this.reportDate = reportDate;
    }
    public String getReportStatus()
    {
        return reportStatus;
    }
    public void setReportStatus(String reportStatus)
    {
        this.reportStatus = reportStatus;
    }
    public String getReportReceiverName()
    {
        return reportReceiverName;
    }
    public void setReportReceiverName(String reportReceiverName)
    {
        this.reportReceiverName = reportReceiverName;
    }
    public String getFirstCheckTime()
    {
        return firstCheckTime;
    }
    public void setFirstCheckTime(String firstCheckTime)
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
    public String getSecondCheckTime()
    {
        return secondCheckTime;
    }
    public void setSecondCheckTime(String secondCheckTime)
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
    public String getTransportTime()
    {
        return transportTime;
    }
    public void setTransportTime(String transportTime)
    {
        this.transportTime = transportTime;
    }
    
}
