package com.todaysoft.lims.system.modules.bpm.multipcr.model;

import java.util.List;

import com.todaysoft.lims.system.modules.bpm.qpcr.model.QpcrTask;

public class MultipcrAssignRequest
{
    
    private String id;
    
    private String pcrTesterId;
    
    private String pcrReagentKit;
    
    private String qcTesterId;
    
    private String qcReagentKit;
    
    private String description;
    
    private List<MultiPcrTask> tasks;
    
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
    
    public List<MultiPcrTask> getTasks()
    {
        return tasks;
    }
    
    public void setTasks(List<MultiPcrTask> tasks)
    {
        this.tasks = tasks;
    }
    
}
