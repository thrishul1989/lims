package com.todaysoft.lims.testing.dnaext.model;

import java.math.BigDecimal;

public class DNAExtractAssignSheetTask
{
    private String id;
    
    private String testingCode;
    
    private BigDecimal sampleInputSize;
    
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
    
    public BigDecimal getSampleInputSize()
    {
        return sampleInputSize;
    }
    
    public void setSampleInputSize(BigDecimal sampleInputSize)
    {
        this.sampleInputSize = sampleInputSize;
    }
}
