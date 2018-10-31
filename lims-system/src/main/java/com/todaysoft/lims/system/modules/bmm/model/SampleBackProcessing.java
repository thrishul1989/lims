package com.todaysoft.lims.system.modules.bmm.model;

import java.math.BigDecimal;

public class SampleBackProcessing
{
    private String id;
    
    private SampleBackApply sampleBackApply;
    
    private String sampleCode;//样本编号
    
    private BigDecimal volume;//样本体积
    
    private String remark;//备注
    
    private String sampleId;//样本id
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public SampleBackApply getSampleBackApply()
    {
        return sampleBackApply;
    }
    
    public void setSampleBackApply(SampleBackApply sampleBackApply)
    {
        this.sampleBackApply = sampleBackApply;
    }
    
    public String getSampleCode()
    {
        return sampleCode;
    }
    
    public void setSampleCode(String sampleCode)
    {
        this.sampleCode = sampleCode;
    }
    
    public BigDecimal getVolume()
    {
        return volume;
    }
    
    public void setVolume(BigDecimal volume)
    {
        this.volume = volume;
    }
    
    public String getRemark()
    {
        return remark;
    }
    
    public void setRemark(String remark)
    {
        this.remark = remark;
    }
    
    public String getSampleId()
    {
        return sampleId;
    }
    
    public void setSampleId(String sampleId)
    {
        this.sampleId = sampleId;
    }
    
}
