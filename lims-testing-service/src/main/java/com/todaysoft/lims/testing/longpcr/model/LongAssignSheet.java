package com.todaysoft.lims.testing.longpcr.model;

import java.util.List;

import com.todaysoft.lims.testing.base.entity.TestingLongpcrTask;

public class LongAssignSheet
{
    private String id;
    
    private String pcrTesterId;
    
    private String pcrReagentKit;
    
    private String creTesterId;
    
    private String creReagentKit;
    
    private String qcTesterId;
    
    private String qcReagentKit;
    
    private String description;
    
    private List<TestingLongpcrTask> tasks;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getPcrTesterId()
    {
        return pcrTesterId;
    }
    
    public void setPcrTesterId(String pcrTesterId)
    {
        this.pcrTesterId = pcrTesterId;
    }
    
    public String getPcrReagentKit()
    {
        return pcrReagentKit;
    }
    
    public void setPcrReagentKit(String pcrReagentKit)
    {
        this.pcrReagentKit = pcrReagentKit;
    }
    
    public String getCreTesterId()
    {
        return creTesterId;
    }
    
    public void setCreTesterId(String creTesterId)
    {
        this.creTesterId = creTesterId;
    }
    
    public String getCreReagentKit()
    {
        return creReagentKit;
    }
    
    public void setCreReagentKit(String creReagentKit)
    {
        this.creReagentKit = creReagentKit;
    }
    
    public String getQcTesterId()
    {
        return qcTesterId;
    }
    
    public void setQcTesterId(String qcTesterId)
    {
        this.qcTesterId = qcTesterId;
    }
    
    public String getQcReagentKit()
    {
        return qcReagentKit;
    }
    
    public void setQcReagentKit(String qcReagentKit)
    {
        this.qcReagentKit = qcReagentKit;
    }
    
    public String getDescription()
    {
        return description;
    }
    
    public void setDescription(String description)
    {
        this.description = description;
    }
    
    public List<TestingLongpcrTask> getTasks()
    {
        return tasks;
    }
    
    public void setTasks(List<TestingLongpcrTask> tasks)
    {
        this.tasks = tasks;
    }
}
