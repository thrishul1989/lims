package com.todaysoft.lims.system.modules.bpm.competeTasks.model.sangerTest;

import java.util.Date;
import java.util.List;

import com.todaysoft.lims.system.modules.bpm.firstpcrsanger.model.FirstPCRSangerSubmitTaskArgs;
import com.todaysoft.lims.system.modules.bpm.firstpcrsanger.model.FirstPCRSangerTask;

public class FirstPCRSangerSheet
{
    private String id;
    
    private String code;
    
    private String assignerName;
    
    private Date assignTime;
    
    private String reagentKit;
    
    private String description;
    
    private String reagentKitName;
    
    private List<FirstPCRSangerTask> tasks;
    
    private String testerName;
    
    private Date submitTime;
    
    private List<FirstPCRSangerSubmitTaskArgs> submitTasks;
    
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
    
    public List<FirstPCRSangerTask> getTasks()
    {
        return tasks;
    }
    
    public void setTasks(List<FirstPCRSangerTask> tasks)
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
    
    public List<FirstPCRSangerSubmitTaskArgs> getSubmitTasks()
    {
        return submitTasks;
    }
    
    public void setSubmitTasks(List<FirstPCRSangerSubmitTaskArgs> submitTasks)
    {
        this.submitTasks = submitTasks;
    }
}
