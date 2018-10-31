package com.todaysoft.lims.system.model.vo.order.orderReportForm;

import java.util.Date;

public class SubsidiarySampleInfoModel
{
  //--------家属样本信息-------------
    private String sampleCode;//样本编号
    private String sampleType;//样本类型ID
    private String sampleSize;//样本量
    private String samplingDate;//采样时间
    private String ownerName;//样本提供者姓名
    private String ownerAge;//样本提供者年龄
    private String familyRelation;//家属关系
    private String purpose;//样本用途
    private String ownerPhenotype;//家属症状
    private String sampleStatus;//样本状态
    private String packDate;//打包时间
    private String sampleAcceptDate;//接收日期
    public String getSampleCode()
    {
        return sampleCode;
    }
    public void setSampleCode(String sampleCode)
    {
        this.sampleCode = sampleCode;
    }
    public String getSampleType()
    {
        return sampleType;
    }
    public void setSampleType(String sampleType)
    {
        this.sampleType = sampleType;
    }
    public String getSampleSize()
    {
        return sampleSize;
    }
    public void setSampleSize(String sampleSize)
    {
        this.sampleSize = sampleSize;
    }
    public String getSamplingDate()
    {
        return samplingDate;
    }
    public void setSamplingDate(String samplingDate)
    {
        this.samplingDate = samplingDate;
    }
    public String getOwnerName()
    {
        return ownerName;
    }
    public void setOwnerName(String ownerName)
    {
        this.ownerName = ownerName;
    }
    public String getOwnerAge()
    {
        return ownerAge;
    }
    public void setOwnerAge(String ownerAge)
    {
        this.ownerAge = ownerAge;
    }
    public String getFamilyRelation()
    {
        return familyRelation;
    }
    public void setFamilyRelation(String familyRelation)
    {
        this.familyRelation = familyRelation;
    }
    public String getPurpose()
    {
        return purpose;
    }
    public void setPurpose(String purpose)
    {
        this.purpose = purpose;
    }
    public String getOwnerPhenotype()
    {
        return ownerPhenotype;
    }
    public void setOwnerPhenotype(String ownerPhenotype)
    {
        this.ownerPhenotype = ownerPhenotype;
    }
    public String getSampleStatus()
    {
        return sampleStatus;
    }
    public void setSampleStatus(String sampleStatus)
    {
        this.sampleStatus = sampleStatus;
    }
    public String getPackDate()
    {
        return packDate;
    }
    public void setPackDate(String packDate)
    {
        this.packDate = packDate;
    }
    public String getSampleAcceptDate()
    {
        return sampleAcceptDate;
    }
    public void setSampleAcceptDate(String sampleAcceptDate)
    {
        this.sampleAcceptDate = sampleAcceptDate;
    }
    
    
}
