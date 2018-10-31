package com.todaysoft.lims.system.modules.bpm.libcap.model;

import java.util.List;

public class LibraryCaptureSubmitRequest
{
    private String id;
    
    private List<LibraryCaptureSubmitTaskArgs> tasks;
    
    private List<LibraryCaptureSubmitGroupArgs> groups;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public List<LibraryCaptureSubmitTaskArgs> getTasks()
    {
        return tasks;
    }
    
    public void setTasks(List<LibraryCaptureSubmitTaskArgs> tasks)
    {
        this.tasks = tasks;
    }
    
    public List<LibraryCaptureSubmitGroupArgs> getGroups()
    {
        return groups;
    }
    
    public void setGroups(List<LibraryCaptureSubmitGroupArgs> groups)
    {
        this.groups = groups;
    }
}
