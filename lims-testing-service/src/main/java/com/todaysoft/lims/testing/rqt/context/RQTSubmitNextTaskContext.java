package com.todaysoft.lims.testing.rqt.context;

import java.util.Map;

import com.todaysoft.lims.testing.base.entity.TestingTask;

public class RQTSubmitNextTaskContext
{
    private String temporaryId;
    
    private String inputSampleId;
    
    private String taskSemantic;
    
    private String taskName;
    
    private TestingTask testingTaskEntity;
    
    private Map<String, Object> variables;
    
    public String getTemporaryId()
    {
        return temporaryId;
    }
    
    public void setTemporaryId(String temporaryId)
    {
        this.temporaryId = temporaryId;
    }
    
    public String getInputSampleId()
    {
        return inputSampleId;
    }
    
    public void setInputSampleId(String inputSampleId)
    {
        this.inputSampleId = inputSampleId;
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
    
    public Map<String, Object> getVariables()
    {
        return variables;
    }
    
    public void setVariables(Map<String, Object> variables)
    {
        this.variables = variables;
    }
}
