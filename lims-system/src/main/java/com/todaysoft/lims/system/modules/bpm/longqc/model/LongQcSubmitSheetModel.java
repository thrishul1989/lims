package com.todaysoft.lims.system.modules.bpm.longqc.model;

import java.util.List;

import com.todaysoft.lims.system.modules.bpm.dnaqc.model.DNAQcSubmitTaskModel;

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
