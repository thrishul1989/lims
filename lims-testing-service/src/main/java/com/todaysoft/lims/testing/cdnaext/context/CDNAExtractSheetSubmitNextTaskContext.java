package com.todaysoft.lims.testing.cdnaext.context;

import java.math.BigDecimal;

import com.todaysoft.lims.testing.base.entity.TestingTask;
import com.todaysoft.lims.testing.base.entity.TestingTaskRunVariable;

public class CDNAExtractSheetSubmitNextTaskContext
{
    private String sampleTypeId;
    
    private String sampleTypeName;
    
    private String sampleCode;
    
    private BigDecimal sampleSize;
    
    private String taskSemantic;
    
    private String taskName;
    
    private String testingCode;
    
    private TestingTask testingTaskEntity;
    
    private TestingTaskRunVariable testingTaskVariablesEntity;
    
    public String getSampleTypeId()
    {
        return sampleTypeId;
    }
    
    public void setSampleTypeId(String sampleTypeId)
    {
        this.sampleTypeId = sampleTypeId;
    }
    
    public String getSampleTypeName()
    {
        return sampleTypeName;
    }
    
    public void setSampleTypeName(String sampleTypeName)
    {
        this.sampleTypeName = sampleTypeName;
    }
    
    public String getSampleCode()
    {
        return sampleCode;
    }
    
    public void setSampleCode(String sampleCode)
    {
        this.sampleCode = sampleCode;
    }
    
    public BigDecimal getSampleSize()
    {
        return sampleSize;
    }
    
    public void setSampleSize(BigDecimal sampleSize)
    {
        this.sampleSize = sampleSize;
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
    
    public String getTestingCode()
    {
        return testingCode;
    }
    
    public void setTestingCode(String testingCode)
    {
        this.testingCode = testingCode;
    }
    
    public TestingTask getTestingTaskEntity()
    {
        return testingTaskEntity;
    }
    
    public void setTestingTaskEntity(TestingTask testingTaskEntity)
    {
        this.testingTaskEntity = testingTaskEntity;
    }
    
    public TestingTaskRunVariable getTestingTaskVariablesEntity()
    {
        return testingTaskVariablesEntity;
    }
    
    public void setTestingTaskVariablesEntity(TestingTaskRunVariable testingTaskVariablesEntity)
    {
        this.testingTaskVariablesEntity = testingTaskVariablesEntity;
    }
}
