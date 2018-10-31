package com.todaysoft.lims.testing.fluotest.model;

import java.util.List;



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
