package com.todaysoft.lims.system.modules.bpm.primerdesign.model;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.util.CollectionUtils;

public class PrimerDesignAssignModel
{
    private String taskCode;

    private List<PrimerDesignTask> tasks;
    
    public List<PrimerDesignTask> getTasks()
    {
        return tasks;
    }
    
    public void setTasks(List<PrimerDesignTask> tasks)
    {
        this.tasks = tasks;
    }

    public String getTaskCode() {
        return taskCode;
    }

    public void setTaskCode(String taskCode) {
        this.taskCode = taskCode;
    }
}
