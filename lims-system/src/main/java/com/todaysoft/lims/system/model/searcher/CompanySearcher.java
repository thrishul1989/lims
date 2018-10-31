package com.todaysoft.lims.system.model.searcher;

public class CompanySearcher
{
    private Integer pageNo;
    
    private Integer pageSize;
    
    private String id;
    
    private String name;//单位名称
    
    private String province;//所在区域-省
    
    private String city;//所在区域-市
    
    private String code;
    
    private String otherName;
    
    public String getCity()
    {
        return city;
    }
    
    public void setCity(String city)
    {
        this.city = city;
    }
    
    public String getOtherName()
    {
        return otherName;
    }
    
    public void setOtherName(String otherName)
    {
        this.otherName = otherName;
    }
    
    public String getCode()
    {
        return code;
    }
    
    public void setCode(String code)
    {
        this.code = code;
    }
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public Integer getPageNo()
    {
        return pageNo;
    }
    
    public void setPageNo(Integer pageNo)
    {
        this.pageNo = pageNo;
    }
    
    public Integer getPageSize()
    {
        return pageSize;
    }
    
    public void setPageSize(Integer pageSize)
    {
        this.pageSize = pageSize;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getProvince()
    {
        return province;
    }
    
    public void setProvince(String province)
    {
        this.province = province;
    }
    
}
