package com.todaysoft.lims.testing.dnaqc.model;

import java.util.List;

public class DNAQcAssignSheet
{
    private String qualityControlReagentKitId;
    
    private String qualityControlTesterId;
    
    private String qualityControlMethods;
    
    private String description;
    
    private List<DNAQcAssignSheetTask> tasks;
    
    public String getQualityControlReagentKitId()
    {
        return qualityControlReagentKitId;
    }
    
    public void setQualityControlReagentKitId(String qualityControlReagentKitId)
    {
        this.qualityControlReagentKitId = qualityControlReagentKitId;
    }
    
    public String getQualityControlTesterId()
    {
        return qualityControlTesterId;
    }
    
    public void setQualityControlTesterId(String qualityControlTesterId)
    {
        this.qualityControlTesterId = qualityControlTesterId;
    }
    
    public String getQualityControlMethods()
    {
        return qualityControlMethods;
    }
    
    public void setQualityControlMethods(String qualityControlMethods)
    {
        this.qualityControlMethods = qualityControlMethods;
    }
    
    public String getDescription()
    {
        return description;
    }
    
    public void setDescription(String description)
    {
        this.description = description;
    }
    
    public List<DNAQcAssignSheetTask> getTasks()
    {
        return tasks;
    }
    
    public void setTasks(List<DNAQcAssignSheetTask> tasks)
    {
        this.tasks = tasks;
    }
}
