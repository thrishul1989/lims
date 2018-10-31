package com.todaysoft.lims.product.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_MEASURE_UNIT")
public class MeasureUnit extends UuidKeyEntity
{
    /**
     * 
     */
    private static final long serialVersionUID = -1990337054624072055L;
    
    private MeasureDimension measureDimension;
    
    private String name;
    
    private BigDecimal ratio;//单位换算比列
    
    private boolean delFlag;//删除标记
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DIMENSION_ID")
    @JsonIgnore
    public MeasureDimension getMeasureDimension()
    {
        return measureDimension;
    }
    
    public void setMeasureDimension(MeasureDimension measureDimension)
    {
        this.measureDimension = measureDimension;
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
    
    @Column(name = "RATIO")
    public BigDecimal getRatio()
    {
        return ratio;
    }
    
    public void setRatio(BigDecimal ratio)
    {
        this.ratio = ratio;
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
