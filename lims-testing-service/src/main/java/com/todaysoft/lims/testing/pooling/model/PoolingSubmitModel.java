package com.todaysoft.lims.testing.pooling.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class PoolingSubmitModel
{
    private String id;
    
    private String code;
    
    private String assignerName;
    
    private Date assignTime;
    
    private String description;
    
    private BigDecimal globalRatio;
    
    private List<PoolingSubmitTask> tasks;
    
    private String testerName;
    
    private Date submitTime;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getCode()
    {
        return code;
    }
    
    public void setCode(String code)
    {
        this.code = code;
    }
    
    public String getAssignerName()
    {
        return assignerName;
    }
    
    public void setAssignerName(String assignerName)
    {
        this.assignerName = assignerName;
    }
    
    public Date getAssignTime()
    {
        return assignTime;
    }
    
    public void setAssignTime(Date assignTime)
    {
        this.assignTime = assignTime;
    }
    
    public String getDescription()
    {
        return description;
    }
    
    public void setDescription(String description)
    {
        this.description = description;
    }
    
    public BigDecimal getGlobalRatio()
    {
        return globalRatio;
    }
    
    public void setGlobalRatio(BigDecimal globalRatio)
    {
        this.globalRatio = globalRatio;
    }
    
    public List<PoolingSubmitTask> getTasks()
    {
        return tasks;
    }
    
    public void setTasks(List<PoolingSubmitTask> tasks)
    {
        this.tasks = tasks;
    }
    
    public String getTesterName()
    {
        return testerName;
    }
    
    public void setTesterName(String testerName)
    {
        this.testerName = testerName;
    }
    
    public Date getSubmitTime()
    {
        return submitTime;
    }
    
    public void setSubmitTime(Date submitTime)
    {
        this.submitTime = submitTime;
    }
}
