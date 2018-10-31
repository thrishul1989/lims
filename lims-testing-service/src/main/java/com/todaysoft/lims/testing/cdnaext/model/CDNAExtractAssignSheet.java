package com.todaysoft.lims.testing.cdnaext.model;

import java.util.List;

public class CDNAExtractAssignSheet
{
    private String extractReagentKitId;
    
    private String extractTesterId;
    
    private String qualityControlReagentKitId;
    
    private String qualityControlTesterId;
    
    private String qualityControlMethods;
    
    private String description;
    
    private List<CDNAExtractAssignSheetTask> tasks;
    
    public String getExtractReagentKitId()
    {
        return extractReagentKitId;
    }
    
    public void setExtractReagentKitId(String extractReagentKitId)
    {
        this.extractReagentKitId = extractReagentKitId;
    }
    
    public String getExtractTesterId()
    {
        return extractTesterId;
    }
    
    public void setExtractTesterId(String extractTesterId)
    {
        this.extractTesterId = extractTesterId;
    }
    
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
    
    public List<CDNAExtractAssignSheetTask> getTasks()
    {
        return tasks;
    }
    
    public void setTasks(List<CDNAExtractAssignSheetTask> tasks)
    {
        this.tasks = tasks;
    }
}
