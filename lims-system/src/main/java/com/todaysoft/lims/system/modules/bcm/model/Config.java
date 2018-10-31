package com.todaysoft.lims.system.modules.bcm.model;

public class Config
{
    private String id;
    
    private String configName;
    
    private String key;
    
    private String value;
    
    private Integer maintainType; //维护类型 0-显示 1-不显示
    
    private Integer valueType; //值类型 1-数字 2-字符串
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getConfigName()
    {
        return configName;
    }
    
    public void setConfigName(String configName)
    {
        this.configName = configName;
    }
    
    public String getKey()
    {
        return key;
    }
    
    public void setKey(String key)
    {
        this.key = key;
    }
    
    public String getValue()
    {
        return value;
    }
    
    public void setValue(String value)
    {
        this.value = value;
    }
    
    public Integer getMaintainType()
    {
        return maintainType;
    }
    
    public void setMaintainType(Integer maintainType)
    {
        this.maintainType = maintainType;
    }
    
    public Integer getValueType()
    {
        return valueType;
    }
    
    public void setValueType(Integer valueType)
    {
        this.valueType = valueType;
    }
}
