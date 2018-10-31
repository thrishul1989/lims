package com.todaysoft.lims.system.modules.bpm.qt.model;

import java.math.BigDecimal;
import java.util.Date;

public class QtAssignReference
{
    private String id;
    
    private String sampleCode;
    
    private Integer fragmentLength;
    
    private BigDecimal concn;
    
    private Integer cluster;
    
    private Date testingTime;
    
    private BigDecimal librarySizeOfQbit;
    
    private BigDecimal librarySizeOfQPCR;
    
    private BigDecimal librarySizeOf2100;
    
    private BigDecimal libraryValueOfQPCR;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getSampleCode()
    {
        return sampleCode;
    }
    
    public void setSampleCode(String sampleCode)
    {
        this.sampleCode = sampleCode;
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
    
    public Integer getCluster()
    {
        return cluster;
    }
    
    public void setCluster(Integer cluster)
    {
        this.cluster = cluster;
    }
    
    public Date getTestingTime()
    {
        return testingTime;
    }
    
    public void setTestingTime(Date testingTime)
    {
        this.testingTime = testingTime;
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
    
    public BigDecimal getLibraryValueOfQPCR()
    {
        return libraryValueOfQPCR;
    }
    
    public void setLibraryValueOfQPCR(BigDecimal libraryValueOfQPCR)
    {
        this.libraryValueOfQPCR = libraryValueOfQPCR;
    }
}
