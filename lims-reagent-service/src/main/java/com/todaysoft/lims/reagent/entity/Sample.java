package com.todaysoft.lims.reagent.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "LIMS_SAMPLE")
public class Sample extends AutoKeyEntity
{
    private static final long serialVersionUID = 8971472981680521731L;
    
    private String code; //样本编号
    
    private String name; //样本名称
    
    private Integer type; //样本类型（1.实验样本，2.非实验样本）
    
    private String storeContainer; //存储容器编号
    
    private String unit; //单位
    
    
    @Column(name = "UNIT")
    public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Column(name = "CODE")
    public String getCode()
    {
        return code;
    }
    
    public void setCode(String code)
    {
        this.code = code;
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
    
    @Column(name = "TYPE")
    public Integer getType()
    {
        return type;
    }
    
    public void setType(Integer type)
    {
        this.type = type;
    }
    
    @Column(name = "STORE_CONTAINER")
    public String getStoreContainer()
    {
        return storeContainer;
    }
    
    public void setStoreContainer(String storeContainer)
    {
        this.storeContainer = storeContainer;
    }
}
