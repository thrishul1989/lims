package com.todaysoft.lims.product.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_CONFIG")
public class Config extends UuidKeyEntity
{
    private static final long serialVersionUID = 5305025259769461379L;
    
    private String configName;
    
    private String key;
    
    private String value;
    
    private Integer maintainType; //维护类型 0-显示 1-不显示
    
    private Integer valueType; //值类型 1-数字 2-字符串
    
    @Column(name = "CONFIG_NAME")
    public String getConfigName()
    {
        return configName;
    }
    
    public void setConfigName(String configName)
    {
        this.configName = configName;
    }
    
    @Column(name = "CONFIG_KEY")
    public String getKey()
    {
        return key;
    }
    
    public void setKey(String key)
    {
        this.key = key;
    }
    
    @Column(name = "CONFIG_VALUE")
    public String getValue()
    {
        return value;
    }
    
    public void setValue(String value)
    {
        this.value = value;
    }
    
    @Column(name = "MAINTAIN_TYPE")
    public Integer getMaintainType()
    {
        return maintainType;
    }
    
    public void setMaintainType(Integer maintainType)
    {
        this.maintainType = maintainType;
    }
    
    @Column(name = "VALUE_TYPE")
    public Integer getValueType()
    {
        return valueType;
    }
    
    public void setValueType(Integer valueType)
    {
        this.valueType = valueType;
    }
}
