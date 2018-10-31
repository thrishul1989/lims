package com.todaysoft.lims.testing.multipcrqc.model;

import java.util.List;

import com.todaysoft.lims.testing.base.entity.TestingTask;

public class MultipcrQcAssignSheet
{
    private String id;
    
    private String qcTesterId;
    
    private String qcReagentKit;
    
    private String pcrTesterId;
    
    private String pcrReagentKit;
    
    private String description;
    
    private List<TestingTask> tasks;
    
    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public List<TestingTask> getTasks()
    {
        return tasks;
    }

    public void setTasks(List<TestingTask> tasks)
    {
        this.tasks = tasks;
    }

    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
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
    
}
