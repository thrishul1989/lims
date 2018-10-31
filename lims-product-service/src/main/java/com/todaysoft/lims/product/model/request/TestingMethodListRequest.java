package com.todaysoft.lims.product.model.request;

public class TestingMethodListRequest
{
    private String name;
    
    private Integer pageNo;
    
    private Integer pageSize;
    
    private Integer type;
    
    public Integer getType()
    {
        return type;
    }
    
    public void setType(Integer type)
    {
        this.type = type;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public int getPageNo()
    {
        return null == pageNo ? 1 : pageNo;
    }
    
    public void setPageNo(Integer pageNo)
    {
        this.pageNo = pageNo;
    }
    
    public int getPageSize()
    {
        return null == pageSize ? 10 : pageSize;
    }
    
    public void setPageSize(Integer pageSize)
    {
        this.pageSize = pageSize;
    }
}
