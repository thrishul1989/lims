package com.todaysoft.lims.gateway.model.request.sample;

public class SampleListRequest
{
    private String keywords;
    
    private String code;
    
    private String name;
    
    private Integer type;
    
    private int pageNo;
    
    private int pageSize;
    
    private String unit;//单位
    
    
    
    
    public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getKeywords()
    {
        return keywords;
    }
    
    public void setKeywords(String keywords)
    {
        this.keywords = keywords;
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
    
}
