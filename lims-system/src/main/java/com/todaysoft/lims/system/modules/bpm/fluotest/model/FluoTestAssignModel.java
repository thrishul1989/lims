package com.todaysoft.lims.system.modules.bpm.fluotest.model;

import java.util.List;

import com.todaysoft.lims.system.modules.bpm.longpcr.model.LongPcrTask;

public class FluoTestAssignModel
{
    public List<FluoTestTask> getTasks()
    {
        return tasks;
    }

    public void setTasks(List<FluoTestTask> tasks)
    {
        this.tasks = tasks;
    }

    private List<FluoTestTask> tasks;
}
