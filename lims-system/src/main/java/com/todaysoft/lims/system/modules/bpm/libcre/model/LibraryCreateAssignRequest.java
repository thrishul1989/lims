package com.todaysoft.lims.system.modules.bpm.libcre.model;

import java.util.List;

public class LibraryCreateAssignRequest
{
    private String createReagentKitId;
    
    private String createTesterId;
    
    private String qualityControlReagentKitId;
    
    private String qualityControlTesterId;
    
    private String qualityControlMethods;
    
    private String description;
    
    private List<LibraryCreateAssignTaskArgs> tasks;
    
    public String getCreateReagentKitId()
    {
        return createReagentKitId;
    }
    
    public void setCreateReagentKitId(String createReagentKitId)
    {
        this.createReagentKitId = createReagentKitId;
    }
    
    public String getCreateTesterId()
    {
        return createTesterId;
    }
    
    public void setCreateTesterId(String createTesterId)
    {
        this.createTesterId = createTesterId;
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
    
    public List<LibraryCreateAssignTaskArgs> getTasks()
    {
        return tasks;
    }
    
    public void setTasks(List<LibraryCreateAssignTaskArgs> tasks)
    {
        this.tasks = tasks;
    }
}
