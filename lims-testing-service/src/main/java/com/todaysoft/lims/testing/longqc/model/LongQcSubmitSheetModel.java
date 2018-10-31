package com.todaysoft.lims.testing.longqc.model;

import java.util.List;

public class LongQcSubmitSheetModel
{
    private String id;
    
    private List<LongQcSubmitTaskModel> tasks;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public List<LongQcSubmitTaskModel> getTasks()
    {
        return tasks;
    }
    
    public void setTasks(List<LongQcSubmitTaskModel> tasks)
    {
        this.tasks = tasks;
    }
    
}
