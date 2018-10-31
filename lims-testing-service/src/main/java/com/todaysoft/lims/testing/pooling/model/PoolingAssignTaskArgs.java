package com.todaysoft.lims.testing.pooling.model;

import java.math.BigDecimal;

public class PoolingAssignTaskArgs
{
    private String id;
    
    private BigDecimal rqtv;
    
    private BigDecimal relativeVolume;
    
    private BigDecimal globalAdjustVolume;
    
    private BigDecimal specifiedRatio;
    
    private BigDecimal mixVolume;
    
    private BigDecimal ctv;
    
    public BigDecimal getCtv()
    {
        return ctv;
    }
    
    public void setCtv(BigDecimal ctv)
    {
        this.ctv = ctv;
    }
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public BigDecimal getRqtv()
    {
        return rqtv;
    }
    
    public void setRqtv(BigDecimal rqtv)
    {
        this.rqtv = rqtv;
    }
    
    public BigDecimal getRelativeVolume()
    {
        return relativeVolume;
    }
    
    public void setRelativeVolume(BigDecimal relativeVolume)
    {
        this.relativeVolume = relativeVolume;
    }
    
    public BigDecimal getGlobalAdjustVolume()
    {
        return globalAdjustVolume;
    }
    
    public void setGlobalAdjustVolume(BigDecimal globalAdjustVolume)
    {
        this.globalAdjustVolume = globalAdjustVolume;
    }
    
    public BigDecimal getSpecifiedRatio()
    {
        return specifiedRatio;
    }
    
    public void setSpecifiedRatio(BigDecimal specifiedRatio)
    {
        this.specifiedRatio = specifiedRatio;
    }
    
    public BigDecimal getMixVolume()
    {
        return mixVolume;
    }
    
    public void setMixVolume(BigDecimal mixVolume)
    {
        this.mixVolume = mixVolume;
    }
}
