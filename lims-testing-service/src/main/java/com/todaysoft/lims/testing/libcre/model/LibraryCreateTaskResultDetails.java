package com.todaysoft.lims.testing.libcre.model;

import java.math.BigDecimal;

public class LibraryCreateTaskResultDetails
{
    private String testingCode;
    
    private String outputSampleCode;
    
    private BigDecimal outputSampleSize;
    
    public String getTestingCode()
    {
        return testingCode;
    }
    
    public void setTestingCode(String testingCode)
    {
        this.testingCode = testingCode;
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
}
