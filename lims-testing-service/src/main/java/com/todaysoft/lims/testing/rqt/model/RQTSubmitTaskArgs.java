package com.todaysoft.lims.testing.rqt.model;

import java.math.BigDecimal;

public class RQTSubmitTaskArgs
{
    private String id;
    
    private BigDecimal ctv;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public BigDecimal getCtv()
    {
        return ctv;
    }
    
    public void setCtv(BigDecimal ctv)
    {
        this.ctv = ctv;
    }
}
