package com.todaysoft.lims.testing.ontesting.model;

import java.math.BigDecimal;

public class SequencingSubmitRequest
{
    private String id;
    
    private Integer cluster;
    
    private BigDecimal effectiveRate;
    
    private BigDecimal effectiveSize;
    
    private BigDecimal q30;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public Integer getCluster()
    {
        return cluster;
    }
    
    public void setCluster(Integer cluster)
    {
        this.cluster = cluster;
    }
    
    public BigDecimal getEffectiveRate()
    {
        return effectiveRate;
    }
    
    public void setEffectiveRate(BigDecimal effectiveRate)
    {
        this.effectiveRate = effectiveRate;
    }
    
    public BigDecimal getEffectiveSize()
    {
        return effectiveSize;
    }
    
    public void setEffectiveSize(BigDecimal effectiveSize)
    {
        this.effectiveSize = effectiveSize;
    }
    
    public BigDecimal getQ30()
    {
        return q30;
    }
    
    public void setQ30(BigDecimal q30)
    {
        this.q30 = q30;
    }
}
