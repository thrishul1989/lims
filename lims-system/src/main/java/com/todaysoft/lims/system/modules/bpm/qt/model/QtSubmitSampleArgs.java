package com.todaysoft.lims.system.modules.bpm.qt.model;

import java.math.BigDecimal;
import java.util.Date;

public class QtSubmitSampleArgs
{
    private String id;
    
    private BigDecimal librarySizeOfQbit;
    
    private BigDecimal librarySizeOfQPCR;
    
    private BigDecimal librarySizeOf2100;
    
    private BigDecimal libraryValueOfQPCR;
    
    private Integer fragmentLength;
    
    private BigDecimal concn;
    
    private Date sequencingTime;
    
    private Integer cluster;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
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
    
    public Date getSequencingTime()
    {
        return sequencingTime;
    }
    
    public void setSequencingTime(Date sequencingTime)
    {
        this.sequencingTime = sequencingTime;
    }
    
    public Integer getCluster()
    {
        return cluster;
    }
    
    public void setCluster(Integer cluster)
    {
        this.cluster = cluster;
    }
}
