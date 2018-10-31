package com.todaysoft.lims.system.model.vo;

import java.math.BigDecimal;

public class ReagentInfo
{
    
    private String reagentKitId;
    
    private BigDecimal inputSize;
    
    private BigDecimal inputVolume;
    
    private ReagentKit reagenKit;
    
    public ReagentKit getReagenKit()
    {
        return reagenKit;
    }
    
    public void setReagenKit(ReagentKit reagenKit)
    {
        this.reagenKit = reagenKit;
    }
    
    public String getReagentKitId()
    {
        return reagentKitId;
    }
    
    public void setReagentKitId(String reagentKitId)
    {
        this.reagentKitId = reagentKitId;
    }
    
    public BigDecimal getInputSize()
    {
        return inputSize;
    }
    
    public void setInputSize(BigDecimal inputSize)
    {
        this.inputSize = inputSize;
    }
    
    public BigDecimal getInputVolume()
    {
        return inputVolume;
    }
    
    public void setInputVolume(BigDecimal inputVolume)
    {
        this.inputVolume = inputVolume;
    }
}
