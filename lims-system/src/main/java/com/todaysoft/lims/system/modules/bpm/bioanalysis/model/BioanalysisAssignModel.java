package com.todaysoft.lims.system.modules.bpm.bioanalysis.model;

import java.util.List;

public class BioanalysisAssignModel
{
    private String id;
    
    private String code;
    
    private String testerId;
    
    private String description;
    
    private List<BioanalysisAssignTask> tasks;
    
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
    
    public List<BioanalysisAssignTask> getTasks()
    {
        return tasks;
    }
    
    public void setTasks(List<BioanalysisAssignTask> tasks)
    {
        this.tasks = tasks;
    }
    
    public String getTesterId()
    {
        return testerId;
    }
    
    public void setTesterId(String testerId)
    {
        this.testerId = testerId;
    }
    
    public String getDescription()
    {
        return description;
    }
    
    public void setDescription(String description)
    {
        this.description = description;
    }
}
