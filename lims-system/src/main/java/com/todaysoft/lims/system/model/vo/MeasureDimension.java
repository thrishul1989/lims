package com.todaysoft.lims.system.model.vo;

import java.util.ArrayList;
import java.util.List;

public class MeasureDimension
{
    private String id;
    
    private String name;//量纲名称
    
    private String baseUnit;//基本单位；
    
    private boolean delFlag; //标记 0-未删除 1-已删除',
    
    private List<MeasureUnit> measureUnitList = new ArrayList<MeasureUnit>();
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public List<MeasureUnit> getMeasureUnitList()
    {
        return measureUnitList;
    }
    
    public void setMeasureUnitList(List<MeasureUnit> measureUnitList)
    {
        this.measureUnitList = measureUnitList;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getBaseUnit()
    {
        return baseUnit;
    }
    
    public void setBaseUnit(String baseUnit)
    {
        this.baseUnit = baseUnit;
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
