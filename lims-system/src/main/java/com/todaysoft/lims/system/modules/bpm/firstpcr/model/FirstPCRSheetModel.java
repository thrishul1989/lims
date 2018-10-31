package com.todaysoft.lims.system.modules.bpm.firstpcr.model;

import java.util.Date;
import java.util.List;

public class FirstPCRSheetModel
{
    private String id;
    
    private String code;
    
    private String assignerName;
    
    private Date assignTime;
    
    private String reagentKit;
    
    private String description;

    private String reagentKitName;
    
    private List<FirstPCRTask> tasks;
    
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
    
    public List<FirstPCRTask> getTasks()
    {
        return tasks;
    }
    
    public void setTasks(List<FirstPCRTask> tasks)
    {
        this.tasks = tasks;
    }

    public String getReagentKitName() {
        return reagentKitName;
    }

    public void setReagentKitName(String reagentKitName) {
        this.reagentKitName = reagentKitName;
    }
}
