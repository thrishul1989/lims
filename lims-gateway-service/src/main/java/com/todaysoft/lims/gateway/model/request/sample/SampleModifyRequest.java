package com.todaysoft.lims.gateway.model.request.sample;

public class SampleModifyRequest
{
    private Integer id;
    
    private String code;
    
    private String name;
    
    private Integer type;
    
    private String storeContainer;
    
    private String unit;//单位
    
    public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Integer getId()
    {
        return id;
    }
    
    public void setId(Integer id)
    {
        this.id = id;
    }
    
    public String getCode()
    {
        return code;
    }
    
    public void setCode(String code)
    {
        this.code = code;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public Integer getType()
    {
        return type;
    }
    
    public void setType(Integer type)
    {
        this.type = type;
    }
    
    public String getStoreContainer()
    {
        return storeContainer;
    }
    
    public void setStoreContainer(String storeContainer)
    {
        this.storeContainer = storeContainer;
    }
}
