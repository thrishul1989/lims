package com.todaysoft.lims.maintain.indexes.model;

import java.math.BigDecimal;

public class ProductProbeConfig
{
    private String probeId;
    
    private String probeName;
    
    private BigDecimal probeDatasize;
    
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
}
