package com.todaysoft.lims.gateway.model.request.sample;

public class SampleCreateRequest
{
    private String code;
    
    private String name;
    
    private Integer type;
    
    private String storeContainer;
    
    private String unit;//单位
    
    private String sampleType;
    
    public String getSampleType() {
		return sampleType;
	}

	public void setSampleType(String sampleType) {
		this.sampleType = sampleType;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
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
