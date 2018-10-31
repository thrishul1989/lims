package com.todaysoft.lims.system.modules.bpm.cdnaext.model;

import java.math.BigDecimal;

public class CDNAExtractAssignSheetTask
{
    private String id;
    
    private String testingCode;
    
    private Integer testingCodeIndex;
    
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
    
    public Integer getTestingCodeIndex()
    {
        return testingCodeIndex;
    }
    
    public void setTestingCodeIndex(Integer testingCodeIndex)
    {
        this.testingCodeIndex = testingCodeIndex;
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
