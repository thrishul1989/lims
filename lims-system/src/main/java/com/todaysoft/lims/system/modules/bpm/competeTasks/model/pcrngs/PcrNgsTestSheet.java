package com.todaysoft.lims.system.modules.bpm.competeTasks.model.pcrngs;

import java.util.Date;
import java.util.List;

import com.todaysoft.lims.system.modules.bpm.pcrngstest.model.PcrNgsTestSubmitTaskArgs;
import com.todaysoft.lims.system.modules.bpm.pcrngstest.model.PcrNgsTestTask;

public class PcrNgsTestSheet
{
    private String id;
    
    private String code;
    
    private String assignerName;
    
    private Date assignTime;
    
    private String reagentKit;
    
    private String description;
    
    private String reagentKitName;
    
    private List<PcrNgsTestTask> tasks;
    
    private List<PcrNgsTestSubmitTaskArgs> submitTasks;
    
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
    
    public String getReagentKit()
    {
        return reagentKit;
    }
    
    public void setReagentKit(String reagentKit)
    {
        this.reagentKit = reagentKit;
    }
    
    public String getDescription()
    {
        return description;
    }
    
    public void setDescription(String description)
    {
        this.description = description;
    }
    
    public List<PcrNgsTestTask> getTasks()
    {
        return tasks;
    }
    
    public void setTasks(List<PcrNgsTestTask> tasks)
    {
        this.tasks = tasks;
    }
    
    public String getReagentKitName()
    {
        return reagentKitName;
    }
    
    public void setReagentKitName(String reagentKitName)
    {
        this.reagentKitName = reagentKitName;
    }
    
    public List<PcrNgsTestSubmitTaskArgs> getSubmitTasks()
    {
        return submitTasks;
    }
    
    public void setSubmitTasks(List<PcrNgsTestSubmitTaskArgs> submitTasks)
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
