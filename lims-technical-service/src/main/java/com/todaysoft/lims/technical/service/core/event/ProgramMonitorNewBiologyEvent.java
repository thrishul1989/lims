package com.todaysoft.lims.technical.service.core.event;

public class ProgramMonitorNewBiologyEvent
{
    private String taskId;
    private String taskSemantic;
    public String getTaskId()
    {
        return taskId;
    }
    public void setTaskId(String taskId)
    {
        this.taskId = taskId;
    }
    public String getTaskSemantic()
    {
        return taskSemantic;
    }
    public void setTaskSemantic(String taskSemantic)
    {
        this.taskSemantic = taskSemantic;
    }
    @Override
    public String toString()
    {
        return "ProgramMonitorNewBiologyEvent [taskId=" + taskId + ", taskSemantic=" + taskSemantic + "]";
    }
    
}
