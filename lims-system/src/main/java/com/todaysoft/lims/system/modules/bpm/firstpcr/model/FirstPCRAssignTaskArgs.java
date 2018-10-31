package com.todaysoft.lims.system.modules.bpm.firstpcr.model;

import java.math.BigDecimal;

public class FirstPCRAssignTaskArgs
{
    private String id;

    private BigDecimal sampleInputQuantity;

    private String pcrTestCode;

    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }

    public BigDecimal getSampleInputQuantity() {
        return sampleInputQuantity;
    }

    public void setSampleInputQuantity(BigDecimal sampleInputQuantity) {
        this.sampleInputQuantity = sampleInputQuantity;
    }

    public String getPcrTestCode() {
        return pcrTestCode;
    }

    public void setPcrTestCode(String pcrTestCode) {
        this.pcrTestCode = pcrTestCode;
    }
}
