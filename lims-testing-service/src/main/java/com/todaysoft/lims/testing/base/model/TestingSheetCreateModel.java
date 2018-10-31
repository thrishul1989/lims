package com.todaysoft.lims.testing.base.model;

import java.util.Date;
import java.util.List;

public class TestingSheetCreateModel
{
    private String code;
    
    private String taskSemantic;
    
    private String taskName;
    
    private String description;
    
    private String testerId;
    
    private String testerName;
    
    private String assignerId;
    
    private String assignerName;
    
    private Date assignTime;
    
    private Date createTime;
    
    private Object variables;
    
    private List<String> tasks;
    
    public String getCode()
    {
        return code;
    }
    
    public void setCode(String code)
    {
        this.code = code;
    }
    
    public String getTaskSemantic()
    {
        return taskSemantic;
    }
    
    public void setTaskSemantic(String taskSemantic)
    {
        this.taskSemantic = taskSemantic;
    }
    
    public String getTaskName()
    {
        return taskName;
    }
    
    public void setTaskName(String taskName)
    {
        this.taskName = taskName;
    }
    
    public String getDescription()
    {
        return description;
    }
    
    public void setDescription(String description)
    {
        this.description = description;
    }
    
    public String getTesterId()
    {
        return testerId;
    }
    
    public void setTesterId(String testerId)
    {
        this.testerId = testerId;
    }
    
    public String getTesterName()
    {
        return testerName;
    }
    
    public void setTesterName(String testerName)
    {
        this.testerName = testerName;
    }
    
    public String getAssignerId()
    {
        return assignerId;
    }
    
    public void setAssignerId(String assignerId)
    {
        this.assignerId = assignerId;
    }
    
    public String getAssignerName()
    {
        return assignerName;
    }
    
    public void setAssignerName(String assignerName)
    {
        this.assignerName = assignerName;
    }
    
    public Date getAssignTime()
    {
        return assignTime;
    }
    
    public void setAssignTime(Date assignTime)
    {
        this.assignTime = assignTime;
    }
    
    public Date getCreateTime()
    {
        return createTime;
    }
    
    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }
    
    public Object getVariables()
    {
        return variables;
    }
    
    public void setVariables(Object variables)
    {
        this.variables = variables;
    }
    
    public List<String> getTasks()
    {
        return tasks;
    }
    
    public void setTasks(List<String> tasks)
    {
        this.tasks = tasks;
    }
}
