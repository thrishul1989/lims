package com.todaysoft.lims.testing.libcre.context;

import java.math.BigDecimal;

import com.todaysoft.lims.testing.base.entity.TestingTask;

public class LibraryCreateSheetSubmitTaskContext
{
    private String id;
    
    private String testingCode;
    
    private String libraryIndex;
    
    private String outputSampleTypeId;
    
    private String outputSampleTypeName;
    
    private String outputSampleCode;
    
    private BigDecimal outputSampleSize;
    
    private TestingTask testingTaskEntity;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getTestingCode()
    {
        return testingCode;
    }
    
    public void setTestingCode(String testingCode)
    {
        this.testingCode = testingCode;
    }
    
    public String getLibraryIndex()
    {
        return libraryIndex;
    }
    
    public void setLibraryIndex(String libraryIndex)
    {
        this.libraryIndex = libraryIndex;
    }
    
    public String getOutputSampleTypeId()
    {
        return outputSampleTypeId;
    }
    
    public void setOutputSampleTypeId(String outputSampleTypeId)
    {
        this.outputSampleTypeId = outputSampleTypeId;
    }
    
    public String getOutputSampleTypeName()
    {
        return outputSampleTypeName;
    }
    
    public void setOutputSampleTypeName(String outputSampleTypeName)
    {
        this.outputSampleTypeName = outputSampleTypeName;
    }
    
    public String getOutputSampleCode()
    {
        return outputSampleCode;
    }
    
    public void setOutputSampleCode(String outputSampleCode)
    {
        this.outputSampleCode = outputSampleCode;
    }
    
    public BigDecimal getOutputSampleSize()
    {
        return outputSampleSize;
    }
    
    public void setOutputSampleSize(BigDecimal outputSampleSize)
    {
        this.outputSampleSize = outputSampleSize;
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
