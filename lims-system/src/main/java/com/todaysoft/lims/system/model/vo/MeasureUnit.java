package com.todaysoft.lims.system.model.vo;

import java.math.BigDecimal;

public class MeasureUnit
{
    private String id;
    
    private MeasureDimension measureDimension;
    
    private String name;
    
    private BigDecimal ratio;//单位换算比列
    
    private boolean delFlag;//删除标记
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public MeasureDimension getMeasureDimension()
    {
        return measureDimension;
    }
    
    public void setMeasureDimension(MeasureDimension measureDimension)
    {
        this.measureDimension = measureDimension;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public BigDecimal getRatio()
    {
        return ratio;
    }
    
    public void setRatio(BigDecimal ratio)
    {
        this.ratio = ratio;
    }
    
    public boolean isDelFlag()
    {
        return delFlag;
    }
    
    public void setDelFlag(boolean delFlag)
    {
        this.delFlag = delFlag;
    }
    
}
