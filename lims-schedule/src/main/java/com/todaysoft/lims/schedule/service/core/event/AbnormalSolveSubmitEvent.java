package com.todaysoft.lims.schedule.service.core.event;

public class AbnormalSolveSubmitEvent
{
    private String taskId;
    
    private String semantic;

    public String getTaskId()
    {
        return taskId;
    }

    public void setTaskId(String taskId)
    {
        this.taskId = taskId;
    }

    public String getSemantic()
    {
        return semantic;
    }

    public void setSemantic(String semantic)
    {
        this.semantic = semantic;
    }


    
}
