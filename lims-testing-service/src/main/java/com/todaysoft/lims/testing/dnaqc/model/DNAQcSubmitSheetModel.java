package com.todaysoft.lims.testing.dnaqc.model;

import java.util.List;

public class DNAQcSubmitSheetModel
{
    private String id;
    
    private List<DNAQcSubmitTaskModel> tasks;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public List<DNAQcSubmitTaskModel> getTasks()
    {
        return tasks;
    }
    
    public void setTasks(List<DNAQcSubmitTaskModel> tasks)
    {
        this.tasks = tasks;
    }
}
