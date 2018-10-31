package com.todaysoft.lims.system.modules.bpm.multipcrqc.model;

import java.util.List;

import com.todaysoft.lims.system.modules.bpm.dnaqc.model.DNAQcSubmitTaskModel;

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
