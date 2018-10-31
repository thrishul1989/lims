package com.todaysoft.lims.system.modules.bpm.pooling.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.util.CollectionUtils;

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
    
    public BigDecimal getPoolingVolume()
    {
        BigDecimal volume = BigDecimal.ZERO;
        
        if (!CollectionUtils.isEmpty(tasks))
        {
            for (PoolingSubmitTask task : tasks)
            {
                volume = volume.add(null == task.getMixVolume() ? BigDecimal.ZERO : task.getMixVolume());
            }
        }
        
        return volume;
    }
    
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
