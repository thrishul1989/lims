package com.todaysoft.lims.testing.libcre.model;

import java.math.BigDecimal;

public class LibraryAttributes
{
    private String index;
    
    private BigDecimal concn;
    
    private BigDecimal volume;
    
    private BigDecimal ratio2628;
    
    private BigDecimal ratio2623;
    
    private String qualityLevel;

    private BigDecimal fragmentLengthStart;

    private BigDecimal fragmentLengthEnd;
    
    public String getIndex()
    {
        return index;
    }
    
    public void setIndex(String index)
    {
        this.index = index;
    }
    
    public BigDecimal getConcn()
    {
        return concn;
    }
    
    public void setConcn(BigDecimal concn)
    {
        this.concn = concn;
    }
    
    public BigDecimal getVolume()
    {
        return volume;
    }
    
    public void setVolume(BigDecimal volume)
    {
        this.volume = volume;
    }
    
    public BigDecimal getRatio2628()
    {
        return ratio2628;
    }
    
    public void setRatio2628(BigDecimal ratio2628)
    {
        this.ratio2628 = ratio2628;
    }
    
    public BigDecimal getRatio2623()
    {
        return ratio2623;
    }
    
    public void setRatio2623(BigDecimal ratio2623)
    {
        this.ratio2623 = ratio2623;
    }
    
    public String getQualityLevel()
    {
        return qualityLevel;
    }
    
    public void setQualityLevel(String qualityLevel)
    {
        this.qualityLevel = qualityLevel;
    }

    public BigDecimal getFragmentLengthStart() {
        return fragmentLengthStart;
    }

    public void setFragmentLengthStart(BigDecimal fragmentLengthStart) {
        this.fragmentLengthStart = fragmentLengthStart;
    }

    public BigDecimal getFragmentLengthEnd() {
        return fragmentLengthEnd;
    }

    public void setFragmentLengthEnd(BigDecimal fragmentLengthEnd) {
        this.fragmentLengthEnd = fragmentLengthEnd;
    }
}
