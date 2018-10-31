package com.todaysoft.lims.order.model;

import java.math.BigDecimal;
import java.util.Date;

public class BasicSampleModel
{
    
    private String id;
    
    private String orderId;
    
    private String sampleType;
    
    private String sampleCode;
    
    private BigDecimal sampleSize;
    
    private String sampleName;
    
    private Date samplingDate;
    
    private String sampleId; //传给前台显示  ---样本id
    
    private Integer sampleBtype;//传给前台显示并传入后台  ---业务类型：1-非科研主样本 2-非科研附属样本 3-科研样本
    
    private Integer purpose;// '1-检测 2-验证 3-对照',
    
    private Integer samplePackageStatus; //样本收样状态
    
    private String errorDealRemark;//送样取消备注
    
    private Integer sampleErrorStatus;
    
    private String errorReason;
    
    private String familyRelation;
    
    public String getOrderId()
    {
        return orderId;
    }
    
    public void setOrderId(String orderId)
    {
        this.orderId = orderId;
    }
    
    public String getSampleType()
    {
        return sampleType;
    }
    
    public void setSampleType(String sampleType)
    {
        this.sampleType = sampleType;
    }
    
    public String getSampleCode()
    {
        return sampleCode;
    }
    
    public void setSampleCode(String sampleCode)
    {
        this.sampleCode = sampleCode;
    }
    
    public BigDecimal getSampleSize()
    {
        return sampleSize;
    }
    
    public void setSampleSize(BigDecimal sampleSize)
    {
        this.sampleSize = sampleSize;
    }
    
    public String getSampleName()
    {
        return sampleName;
    }
    
    public void setSampleName(String sampleName)
    {
        this.sampleName = sampleName;
    }
    
    public Date getSamplingDate()
    {
        return samplingDate;
    }
    
    public void setSamplingDate(Date samplingDate)
    {
        this.samplingDate = samplingDate;
    }
    
    public String getSampleId()
    {
        return sampleId;
    }
    
    public void setSampleId(String sampleId)
    {
        this.sampleId = sampleId;
    }
    
    public Integer getSampleBtype()
    {
        return sampleBtype;
    }
    
    public void setSampleBtype(Integer sampleBtype)
    {
        this.sampleBtype = sampleBtype;
    }
    
    public Integer getPurpose()
    {
        return purpose;
    }
    
    public void setPurpose(Integer purpose)
    {
        this.purpose = purpose;
    }
    
    public Integer getSamplePackageStatus()
    {
        return samplePackageStatus;
    }
    
    public void setSamplePackageStatus(Integer samplePackageStatus)
    {
        this.samplePackageStatus = samplePackageStatus;
    }
    
    public String getErrorDealRemark()
    {
        return errorDealRemark;
    }
    
    public void setErrorDealRemark(String errorDealRemark)
    {
        this.errorDealRemark = errorDealRemark;
    }
    
    public Integer getSampleErrorStatus()
    {
        return sampleErrorStatus;
    }
    
    public void setSampleErrorStatus(Integer sampleErrorStatus)
    {
        this.sampleErrorStatus = sampleErrorStatus;
    }
    
    public String getErrorReason()
    {
        return errorReason;
    }
    
    public void setErrorReason(String errorReason)
    {
        this.errorReason = errorReason;
    }
    
    public String getFamilyRelation()
    {
        return familyRelation;
    }
    
    public void setFamilyRelation(String familyRelation)
    {
        this.familyRelation = familyRelation;
    }
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
}
