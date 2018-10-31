package com.todaysoft.lims.product.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_MEASURE_DIMENSION")
public class MeasureDimension extends UuidKeyEntity
{
    /**
     * 
     */
    private static final long serialVersionUID = -8304322152440741006L;
    
    private String name;//量纲名称
    
    private String baseUnit;//基本单位；
    
    private boolean delFlag; //标记 0-未删除 1-已删除',
    
    private List<MeasureUnit> measureUnitList = new ArrayList<MeasureUnit>();
    
    @OneToMany(mappedBy = "measureDimension", cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @NotFound(action = NotFoundAction.IGNORE)
    @Fetch(FetchMode.SUBSELECT)
    public List<MeasureUnit> getMeasureUnitList()
    {
        return measureUnitList;
    }
    
    public void setMeasureUnitList(List<MeasureUnit> measureUnitList)
    {
        this.measureUnitList = measureUnitList;
    }
    
    @Column(name = "NAME")
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    @Column(name = "BASE_UNIT")
    public String getBaseUnit()
    {
        return baseUnit;
    }
    
    public void setBaseUnit(String baseUnit)
    {
        this.baseUnit = baseUnit;
    }
    
    @Column(name = "DEL_FLAG")
    public boolean isDelFlag()
    {
        return delFlag;
    }
    
    public void setDelFlag(boolean delFlag)
    {
        this.delFlag = delFlag;
    }
    
}
