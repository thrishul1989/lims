package com.todaysoft.lims.system.modules.bpm.dnaext.model;

import java.util.List;


public class DNAExtractAssignSheet
{
    private String extractReagentKitId;
    
    private String extractTesterId;
    
    private String qualityControlReagentKitId;
    
    private String qualityControlTesterId;
    
    private String qualityControlMethods;
    
    private String description;
    
    private List<DNAExtractAssignSheetTask> tasks;
    
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
    
    public List<DNAExtractAssignSheetTask> getTasks()
    {
        return tasks;
    }
    
    public void setTasks(List<DNAExtractAssignSheetTask> tasks)
    {
        this.tasks = tasks;
    }
}
