package com.todaysoft.lims.testing.dnaqc.context;

import com.todaysoft.lims.testing.base.entity.Primer;
import com.todaysoft.lims.testing.base.entity.TestingTask;

public class DNAQcSubmitNextTaskContext
{
    private String temporaryId;
    
    private String inputSampleId;
    
    private String taskSemantic;
    
    private String taskName;
    
    private TestingTask testingTaskEntity;

    private Primer primerVar;
    
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

    public Primer getPrimerVar() {
        return primerVar;
    }

    public void setPrimerVar(Primer primerVar) {
        this.primerVar = primerVar;
    }
}
