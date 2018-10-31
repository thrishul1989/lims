package com.todaysoft.lims.testing.libcap.model;

import java.math.BigDecimal;
import java.util.Set;

public class LibraryCaptureSampleAttributes
{
    private String probeId;
    
    private String probeName;
    
    private BigDecimal probeDatasize;
    
    private BigDecimal concn;
    
    private Set<CaptureLibraryAttributes> libraries;
    
    public BigDecimal getConcn()
    {
        return concn;
    }
    
    public void setConcn(BigDecimal concn)
    {
        this.concn = concn;
    }
    
    public String getProbeId()
    {
        return probeId;
    }
    
    public void setProbeId(String probeId)
    {
        this.probeId = probeId;
    }
    
    public String getProbeName()
    {
        return probeName;
    }
    
    public void setProbeName(String probeName)
    {
        this.probeName = probeName;
    }
    
    public BigDecimal getProbeDatasize()
    {
        return probeDatasize;
    }
    
    public void setProbeDatasize(BigDecimal probeDatasize)
    {
        this.probeDatasize = probeDatasize;
    }
    
    public Set<CaptureLibraryAttributes> getLibraries()
    {
        return libraries;
    }
    
    public void setLibraries(Set<CaptureLibraryAttributes> libraries)
    {
        this.libraries = libraries;
    }
}
