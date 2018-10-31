package com.todaysoft.lims.testing.libcap.context;

import com.todaysoft.lims.testing.base.entity.TestingTask;

public class LibraryCaptureSubmitNextTaskContext
{
    private LibraryCaptureSubmitGroupContext groupContext;
    
    private String taskSemantic;
    
    private String taskName;
    
    private TestingTask testingTaskEntity;
    
    public String getTemporaryId()
    {
        return groupContext.getGroup().getId() + "_" + taskSemantic;
    }
    
    public LibraryCaptureSubmitGroupContext getGroupContext()
    {
        return groupContext;
    }
    
    public void setGroupContext(LibraryCaptureSubmitGroupContext groupContext)
    {
        this.groupContext = groupContext;
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
    
    public TestingTask getTestingTaskEntity()
    {
        return testingTaskEntity;
    }
    
    public void setTestingTaskEntity(TestingTask testingTaskEntity)
    {
        this.testingTaskEntity = testingTaskEntity;
    }
}
