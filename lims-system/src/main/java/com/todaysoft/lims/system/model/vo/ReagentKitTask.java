package com.todaysoft.lims.system.model.vo;

public class ReagentKitTask
{
    
    private String id;
    
    private ReagentKit reagentKit;
    
    private String taskConfigId;
    
    private Task task;
    
    public Task getTask()
    {
        return task;
    }
    
    public void setTask(Task task)
    {
        this.task = task;
    }
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public ReagentKit getReagentKit()
    {
        return reagentKit;
    }
    
    public void setReagentKit(ReagentKit reagentKit)
    {
        this.reagentKit = reagentKit;
    }
    
    public String getTaskConfigId()
    {
        return taskConfigId;
    }
    
    public void setTaskConfigId(String taskConfigId)
    {
        this.taskConfigId = taskConfigId;
    }
    
}
