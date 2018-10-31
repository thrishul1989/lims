package com.todaysoft.lims.sample.model;



public class Sample
{
    private Integer id;

    private String code; //样本编号
    
    private String name; //样本名称
    
    private Integer type; //样本类型（1.实验样本，2.非实验样本）
    
    private String storeContainer; //存储容器编号
    
    private String sampleType;//样本类型
    
    
    public String getSampleType() {
		return sampleType;
	}

	public void setSampleType(String sampleType) {
		this.sampleType = sampleType;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
