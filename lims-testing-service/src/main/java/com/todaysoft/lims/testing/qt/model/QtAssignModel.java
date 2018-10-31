package com.todaysoft.lims.testing.qt.model;

import java.util.Date;
import java.util.List;

public class QtAssignModel
{
    private String id;
    
    private String sampleCode;
    
    private Date createTime;
    
    private List<QtAssignReference> references;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getSampleCode()
    {
        return sampleCode;
    }
    
    public void setSampleCode(String sampleCode)
    {
        this.sampleCode = sampleCode;
    }
    
    public Date getCreateTime()
    {
        return createTime;
    }
    
    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }
    
    public List<QtAssignReference> getReferences()
    {
        return references;
    }
    
    public void setReferences(List<QtAssignReference> references)
    {
        this.references = references;
    }
}
