package com.todaysoft.lims.testing.libqc.model;


import java.util.Date;
import java.util.List;

public class LibraryQcSubmitModel
{
    private String id;
    
    private String code;
    
    private String assignerName;
    
    private Date assignTime;
    
    private String reagentKitName;

    private String qualityControlMethods;
    
    private String description;
    
    private List<LibraryQcTask> tasks;
    
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
    
    public List<LibraryQcTask> getTasks()
    {
        return tasks;
    }
    
    public void setTasks(List<LibraryQcTask> tasks)
    {
        this.tasks = tasks;
    }

    public String getQualityControlMethods() {
        return qualityControlMethods;
    }

    public void setQualityControlMethods(String qualityControlMethods) {
        this.qualityControlMethods = qualityControlMethods;
    }
}
