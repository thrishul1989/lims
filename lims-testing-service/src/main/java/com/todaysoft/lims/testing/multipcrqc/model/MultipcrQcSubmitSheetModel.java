package com.todaysoft.lims.testing.multipcrqc.model;

import java.util.List;

public class MultipcrQcSubmitSheetModel
{
    private String id;
    
    private List<MultipcrQcSubmitTaskModel> tasks;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public List<MultipcrQcSubmitTaskModel> getTasks()
    {
        return tasks;
    }
    
    public void setTasks(List<MultipcrQcSubmitTaskModel> tasks)
    {
        this.tasks = tasks;
    }
    
}
