package com.todaysoft.lims.system.modules.bpm.pooling.model;

import java.math.BigDecimal;
import java.util.List;

public class PoolingAssignRequest
{
    private String id;
    
    private String testerId;
    
    private String description;
    
    private String poolingCode;
    
    private BigDecimal globalRatio;
    
    private List<PoolingAssignTaskArgs> tasks;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getTesterId()
    {
        return testerId;
    }
    
    public void setTesterId(String testerId)
    {
        this.testerId = testerId;
    }
    
    public String getDescription()
    {
        return description;
    }
    
    public void setDescription(String description)
    {
        this.description = description;
    }
    
    public String getPoolingCode()
    {
        return poolingCode;
    }
    
    public void setPoolingCode(String poolingCode)
    {
        this.poolingCode = poolingCode;
    }
    
    public BigDecimal getGlobalRatio()
    {
        return globalRatio;
    }
    
    public void setGlobalRatio(BigDecimal globalRatio)
    {
        this.globalRatio = globalRatio;
    }
    
    public List<PoolingAssignTaskArgs> getTasks()
    {
        return tasks;
    }
    
    public void setTasks(List<PoolingAssignTaskArgs> tasks)
    {
        this.tasks = tasks;
    }
}
