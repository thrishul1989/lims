package com.todaysoft.lims.testing.ontesting.model;

import java.math.BigDecimal;

public class SequencingSheetVariables
{
    private BigDecimal finalConcn;
    
    private String reagentKitId;
    
    public BigDecimal getFinalConcn()
    {
        return finalConcn;
    }
    
    public void setFinalConcn(BigDecimal finalConcn)
    {
        this.finalConcn = finalConcn;
    }
    
    public String getReagentKitId()
    {
        return reagentKitId;
    }
    
    public void setReagentKitId(String reagentKitId)
    {
        this.reagentKitId = reagentKitId;
    }
}
