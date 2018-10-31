package com.todaysoft.lims.testing.libqc.model;

import java.util.List;

public class LibraryQcSubmitRequest
{
    private String id;
    
    private List<LibraryQcSubmitTaskArgs> tasks;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public List<LibraryQcSubmitTaskArgs> getTasks()
    {
        return tasks;
    }
    
    public void setTasks(List<LibraryQcSubmitTaskArgs> tasks)
    {
        this.tasks = tasks;
    }
}
