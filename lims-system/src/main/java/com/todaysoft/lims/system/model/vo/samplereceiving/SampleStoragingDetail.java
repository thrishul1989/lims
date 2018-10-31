package com.todaysoft.lims.system.model.vo.samplereceiving;

import com.todaysoft.lims.persist.UuidKeyEntity;

public class SampleStoragingDetail extends UuidKeyEntity
{
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private String sampleCode;//    '样本编号',
    
    private String location;//位置
    
    private String sampleType;//    样本类型
    
    private String sampleName;//样本名称
    
    public String getSampleType()
    {
        return sampleType;
    }
    
    public void setSampleType(String sampleType)
    {
        this.sampleType = sampleType;
    }
    
    public String getSampleName()
    {
        return sampleName;
    }
    
    public void setSampleName(String sampleName)
    {
        this.sampleName = sampleName;
    }
    
    private SampleStoraging sampleStoraging;
    
    public String getSampleCode()
    {
        return sampleCode;
    }
    
    public void setSampleCode(String sampleCode)
    {
        this.sampleCode = sampleCode;
    }
    
    public String getLocation()
    {
        return location;
    }
    
    public void setLocation(String location)
    {
        this.location = location;
    }
    
    public SampleStoraging getSampleStoraging()
    {
        return sampleStoraging;
    }
    
    public void setSampleStoraging(SampleStoraging sampleStoraging)
    {
        this.sampleStoraging = sampleStoraging;
    }
    
}
