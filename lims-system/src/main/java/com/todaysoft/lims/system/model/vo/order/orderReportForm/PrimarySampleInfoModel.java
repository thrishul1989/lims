package com.todaysoft.lims.system.model.vo.order.orderReportForm;

import java.util.Date;

public class PrimarySampleInfoModel
{
  //---------样本信息------------
    private String primarySampleCode;//样本编号
    private String primarySampleType;//样本类型ID
    private String primarySampleSize;//样本量
    private String primarySamplingDate;//采样时间
    private String SreingprimarySampleStatus;//样本状态
    private String primarySampleAcceptDate;//接收日期
    public String getPrimarySampleCode()
    {
        return primarySampleCode;
    }
    public void setPrimarySampleCode(String primarySampleCode)
    {
        this.primarySampleCode = primarySampleCode;
    }
    public String getPrimarySampleType()
    {
        return primarySampleType;
    }
    public void setPrimarySampleType(String primarySampleType)
    {
        this.primarySampleType = primarySampleType;
    }
    public String getPrimarySampleSize()
    {
        return primarySampleSize;
    }
    public void setPrimarySampleSize(String primarySampleSize)
    {
        this.primarySampleSize = primarySampleSize;
    }
    public String getPrimarySamplingDate()
    {
        return primarySamplingDate;
    }
    public void setPrimarySamplingDate(String primarySamplingDate)
    {
        this.primarySamplingDate = primarySamplingDate;
    }
    public String getSreingprimarySampleStatus()
    {
        return SreingprimarySampleStatus;
    }
    public void setSreingprimarySampleStatus(String sreingprimarySampleStatus)
    {
        SreingprimarySampleStatus = sreingprimarySampleStatus;
    }
    public String getPrimarySampleAcceptDate()
    {
        return primarySampleAcceptDate;
    }
    public void setPrimarySampleAcceptDate(String primarySampleAcceptDate)
    {
        this.primarySampleAcceptDate = primarySampleAcceptDate;
    }
    
}
