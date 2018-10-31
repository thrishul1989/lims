package com.todaysoft.lims.system.modules.bpm.dt.model;

import java.math.BigDecimal;

public class DTAssignTaskArgs
{
    private String id;
    
    private BigDecimal rqtv;
    
    private BigDecimal relativeVolume;
    
    private BigDecimal globalAdjustVolume;
    
    private BigDecimal specifiedRatio;
    
    private BigDecimal mixVolume;
    
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
