package com.todaysoft.lims.testing.rqt.model;

import java.math.BigDecimal;

public class RQTSheetVariables
{
    private String reagentKitId;
    
    private BigDecimal designDatasize;
    
    public String getReagentKitId()
    {
        return reagentKitId;
    }
    
    public void setReagentKitId(String reagentKitId)
    {
        this.reagentKitId = reagentKitId;
    }
    
    public BigDecimal getDesignDatasize()
    {
        return designDatasize;
    }
    
    public void setDesignDatasize(BigDecimal designDatasize)
    {
        this.designDatasize = designDatasize;
    }
}
