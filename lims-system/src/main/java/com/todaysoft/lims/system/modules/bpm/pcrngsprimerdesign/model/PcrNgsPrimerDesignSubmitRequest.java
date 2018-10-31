package com.todaysoft.lims.system.modules.bpm.pcrngsprimerdesign.model;

import java.util.List;

public class PcrNgsPrimerDesignSubmitRequest
{
    private String id;
    
    private List<PcrNgsPrimerDesignSubmitTaskArgs> tasks;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public List<PcrNgsPrimerDesignSubmitTaskArgs> getTasks()
    {
        return tasks;
    }
    
    public void setTasks(List<PcrNgsPrimerDesignSubmitTaskArgs> tasks)
    {
        this.tasks = tasks;
    }
}
