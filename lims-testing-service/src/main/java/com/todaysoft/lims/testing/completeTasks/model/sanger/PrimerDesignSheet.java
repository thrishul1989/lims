package com.todaysoft.lims.testing.completeTasks.model.sanger;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.todaysoft.lims.testing.primerdesign.model.PrimerDesignSubmitTaskArgs;
import com.todaysoft.lims.testing.primerdesign.model.PrimerDesignTask;

public class PrimerDesignSheet
{
    private String id;
    
    private String code;
    
    private String assignerName;
    
    private Date assignTime;
    
    private String reagentKitName;
    
    private BigDecimal designDatasize;
    
    private String description;
    
    private List<PrimerDesignTask> tasks;
    
    private List<PrimerDesignSubmitTaskArgs> submitTasks;
    
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
    
    public String getReagentKitName()
    {
        return reagentKitName;
    }
    
    public void setReagentKitName(String reagentKitName)
    {
        this.reagentKitName = reagentKitName;
    }
    
    public String getDescription()
    {
        return description;
    }
    
    public void setDescription(String description)
    {
        this.description = description;
    }
    
    public BigDecimal getDesignDatasize()
    {
        return designDatasize;
    }
    
    public void setDesignDatasize(BigDecimal designDatasize)
    {
        this.designDatasize = designDatasize;
    }
    
    public List<PrimerDesignTask> getTasks()
    {
        return tasks;
    }
    
    public void setTasks(List<PrimerDesignTask> tasks)
    {
        this.tasks = tasks;
    }
    
    public List<PrimerDesignSubmitTaskArgs> getSubmitTasks()
    {
        return submitTasks;
    }
    
    public void setSubmitTasks(List<PrimerDesignSubmitTaskArgs> submitTasks)
    {
        this.submitTasks = submitTasks;
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
