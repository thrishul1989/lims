package com.todaysoft.lims.testing.biologyanaly.context;

import com.todaysoft.lims.testing.base.entity.TestingTask;

public class BiologyAnalySubmitNextTaskContext
{
    private String temporaryId;
    
    private String scheduleId;
    
    private String inputSampleId;
    
    private String taskName;
    
    private String taskSemantic;
    
    private TestingTask testingTaskEntity;
    
    public String getTaskName()
    {
        return taskName;
    }
    
    public void setTaskName(String taskName)
    {
        this.taskName = taskName;
    }
    
    public String getTaskSemantic()
    {
        return taskSemantic;
    }
    
    public void setTaskSemantic(String taskSemantic)
    {
        this.taskSemantic = taskSemantic;
    }
    
    public TestingTask getTestingTaskEntity()
    {
        return testingTaskEntity;
    }
    
    public void setTestingTaskEntity(TestingTask testingTaskEntity)
    {
        this.testingTaskEntity = testingTaskEntity;
    }
    
    public String getTemporaryId()
    {
        return temporaryId;
    }
    
    public void setTemporaryId(String temporaryId)
    {
        this.temporaryId = temporaryId;
    }
    
    public String getScheduleId()
    {
        return scheduleId;
    }
    
    public void setScheduleId(String scheduleId)
    {
        this.scheduleId = scheduleId;
    }
    
    public String getInputSampleId()
    {
        return inputSampleId;
    }
    
    public void setInputSampleId(String inputSampleId)
    {
        this.inputSampleId = inputSampleId;
    }
}
