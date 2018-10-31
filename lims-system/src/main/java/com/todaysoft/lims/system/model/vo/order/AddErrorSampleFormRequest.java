package com.todaysoft.lims.system.model.vo.order;

import java.math.BigDecimal;

public class AddErrorSampleFormRequest
{
    
    private String id;
    
    private String sampleId;
    
    private String orderId;
    
    private Integer strategy;
    
    private String mainSampleCode; //样本编号
    
    private String sampleType; //样本类型
    
    private BigDecimal sampleSize;//样本量
    
    private String samplingDate;//采样时间
    
    private String remark;
    
    private String errorOperatorId; //异常处理人id
    
    private String errorOperatorName; //异常处理NAME
    
    public String getErrorOperatorId()
    {
        return errorOperatorId;
    }
    
    public void setErrorOperatorId(String errorOperatorId)
    {
        this.errorOperatorId = errorOperatorId;
    }
    
    public String getErrorOperatorName()
    {
        return errorOperatorName;
    }
    
    public void setErrorOperatorName(String errorOperatorName)
    {
        this.errorOperatorName = errorOperatorName;
    }
    
    public String getRemark()
    {
        return remark;
    }
    
    public void setRemark(String remark)
    {
        this.remark = remark;
    }
    
    public Integer getStrategy()
    {
        return strategy;
    }
    
    public void setStrategy(Integer strategy)
    {
        this.strategy = strategy;
    }
    
    public String getMainSampleCode()
    {
        return mainSampleCode;
    }
    
    public void setMainSampleCode(String mainSampleCode)
    {
        this.mainSampleCode = mainSampleCode;
    }
    
    public String getSampleType()
    {
        return sampleType;
    }
    
    public void setSampleType(String sampleType)
    {
        this.sampleType = sampleType;
    }
    
    public BigDecimal getSampleSize()
    {
        return sampleSize;
    }
    
    public void setSampleSize(BigDecimal sampleSize)
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
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getSampleId()
    {
        return sampleId;
    }
    
    public void setSampleId(String sampleId)
    {
        this.sampleId = sampleId;
    }
    
    public String getOrderId()
    {
        return orderId;
    }
    
    public void setOrderId(String orderId)
    {
        this.orderId = orderId;
    }
    
}
