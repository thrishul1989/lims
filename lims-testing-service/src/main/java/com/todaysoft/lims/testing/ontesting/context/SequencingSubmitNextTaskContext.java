package com.todaysoft.lims.testing.ontesting.context;

import com.todaysoft.lims.testing.base.entity.TestingSample;
import com.todaysoft.lims.testing.base.entity.TestingTask;

public class SequencingSubmitNextTaskContext
{
    private String taskName;
    
    private String taskSemantic;
    
    private TestingSample inputSample;
    
    private TestingTask testingTaskEntity;
    
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
    
    public TestingSample getInputSample()
    {
        return inputSample;
    }
    
    public void setInputSample(TestingSample inputSample)
    {
        this.inputSample = inputSample;
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
