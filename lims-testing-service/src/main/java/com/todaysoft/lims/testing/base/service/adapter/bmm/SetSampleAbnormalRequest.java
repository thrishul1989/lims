package com.todaysoft.lims.testing.base.service.adapter.bmm;


public class SetSampleAbnormalRequest
{
    private String id;
    
    private String businessType;
    
    private String abnormalType;
    
    private String abnormalRemark;
    
    private String errorOperatorId;
    
    private String errorOperatorName;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getBusinessType()
    {
        return businessType;
    }
    
    public void setBusinessType(String businessType)
    {
        this.businessType = businessType;
    }
    
    public String getAbnormalType()
    {
        return abnormalType;
    }
    
    public void setAbnormalType(String abnormalType)
    {
        this.abnormalType = abnormalType;
    }
    
    public String getAbnormalRemark()
    {
        return abnormalRemark;
    }
    
    public void setAbnormalRemark(String abnormalRemark)
    {
        this.abnormalRemark = abnormalRemark;
    }
    
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
    
}
