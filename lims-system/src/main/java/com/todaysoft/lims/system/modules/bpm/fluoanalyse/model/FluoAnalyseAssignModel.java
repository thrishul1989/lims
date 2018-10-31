package com.todaysoft.lims.system.modules.bpm.fluoanalyse.model;

import java.util.List;

import com.todaysoft.lims.system.modules.bpm.fluotest.model.FluoTestTask;

public class FluoAnalyseAssignModel
{
    private List<FluoAnalyseTask> tasks;

    public List<FluoAnalyseTask> getTasks()
    {
        return tasks;
    }

    public void setTasks(List<FluoAnalyseTask> tasks)
    {
        this.tasks = tasks;
    }
}
