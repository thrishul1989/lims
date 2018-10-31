package com.todaysoft.lims.testing.completeTasks.model.sangerTest;

import java.util.Date;
import java.util.List;

import com.todaysoft.lims.testing.firstpcrsanger.model.FirstPcrSangerSubmitTaskArgs;
import com.todaysoft.lims.testing.firstpcrsanger.model.FirstPcrSangerTask;

public class FirstPCRSangerSheet
{
    private String id;
    
    private String code;
    
    private String assignerName;
    
    private Date assignTime;
    
    private String reagentKit;
    
    private String description;
    
    private String reagentKitName;
    
    private List<FirstPcrSangerTask> tasks;
    
    private String testerName;
    
    private Date submitTime;
    
    private List<FirstPcrSangerSubmitTaskArgs> submitTasks;
    
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
    
    public String getReagentKitName()
    {
        return reagentKitName;
    }
    
    public void setReagentKitName(String reagentKitName)
    {
        this.reagentKitName = reagentKitName;
    }
    
    public List<FirstPcrSangerTask> getTasks()
    {
        return tasks;
    }
    
    public void setTasks(List<FirstPcrSangerTask> tasks)
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
    
    public List<FirstPcrSangerSubmitTaskArgs> getSubmitTasks()
    {
        return submitTasks;
    }
    
    public void setSubmitTasks(List<FirstPcrSangerSubmitTaskArgs> submitTasks)
    {
        this.submitTasks = submitTasks;
    }
}
