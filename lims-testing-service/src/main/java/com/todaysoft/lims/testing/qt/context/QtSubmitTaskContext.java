package com.todaysoft.lims.testing.qt.context;

import java.math.BigDecimal;

import com.todaysoft.lims.testing.base.entity.TestingTask;

public class QtSubmitTaskContext
{
    private BigDecimal librarySizeOfQbit;
    
    private BigDecimal librarySizeOfQPCR;
    
    private BigDecimal librarySizeOf2100;
    
    private Integer fragmentLength;
    
    private BigDecimal concn;
    
    private TestingTask testingTaskEntity;
    
    public TestingTask getTestingTaskEntity()
    {
        return testingTaskEntity;
    }
    
    public void setTestingTaskEntity(TestingTask testingTaskEntity)
    {
        this.testingTaskEntity = testingTaskEntity;
    }
    
    public BigDecimal getLibrarySizeOfQbit()
    {
        return librarySizeOfQbit;
    }
    
    public void setLibrarySizeOfQbit(BigDecimal librarySizeOfQbit)
    {
        this.librarySizeOfQbit = librarySizeOfQbit;
    }
    
    public BigDecimal getLibrarySizeOfQPCR()
    {
        return librarySizeOfQPCR;
    }
    
    public void setLibrarySizeOfQPCR(BigDecimal librarySizeOfQPCR)
    {
        this.librarySizeOfQPCR = librarySizeOfQPCR;
    }
    
    public BigDecimal getLibrarySizeOf2100()
    {
        return librarySizeOf2100;
    }
    
    public void setLibrarySizeOf2100(BigDecimal librarySizeOf2100)
    {
        this.librarySizeOf2100 = librarySizeOf2100;
    }
    
    public Integer getFragmentLength()
    {
        return fragmentLength;
    }
    
    public void setFragmentLength(Integer fragmentLength)
    {
        this.fragmentLength = fragmentLength;
    }
    
    public BigDecimal getConcn()
    {
        return concn;
    }
    
    public void setConcn(BigDecimal concn)
    {
        this.concn = concn;
    }
}
