package com.todaysoft.lims.testing.qt.model;

import java.math.BigDecimal;

public class QtSampleAttributes
{
    private BigDecimal concn;
    
    private Integer fragmentLength;
    
    public BigDecimal getConcn()
    {
        return concn;
    }
    
    public void setConcn(BigDecimal concn)
    {
        this.concn = concn;
    }
    
    public Integer getFragmentLength()
    {
        return fragmentLength;
    }
    
    public void setFragmentLength(Integer fragmentLength)
    {
        this.fragmentLength = fragmentLength;
    }
}
