package com.todaysoft.lims.testing.cdnaqc.model;

import java.util.List;

public class CDNAQcSubmitSheetModel
{
    private String id;
    
    private List<CDNAQcSubmitTaskModel> tasks;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public List<CDNAQcSubmitTaskModel> getTasks()
    {
        return tasks;
    }
    
    public void setTasks(List<CDNAQcSubmitTaskModel> tasks)
    {
        this.tasks = tasks;
    }
}
