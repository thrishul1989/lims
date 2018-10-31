package com.todaysoft.lims.system.model.searcher;

public class SampleSearcher
{
    public static final int SAMPLE_NA = 1;
    
    public static final int SAMPLE_OG = 2;
    
    private String keywords;
    
    private String code;
    
    private String name;
    
    private Integer type;
    
    private String unit;//单位
    
    public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public static int getSampleNa() {
		return SAMPLE_NA;
	}

	public static int getSampleOg() {
		return SAMPLE_OG;
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
