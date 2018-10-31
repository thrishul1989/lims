package com.todaysoft.lims.smm.request;

public class RoleListRequest
{
    private Integer pageNo;
    
    private Integer pageSize;
    
    private String name;
    
    public int getPageNo()
    {
        return null == pageNo ? 1 : pageNo;
    }
    
    public void setPageNo(int pageNo)
    {
        this.pageNo = pageNo;
    }
    
    public int getPageSize()
    {
        return null == pageSize ? 10 : pageSize;
    }
    
    public void setPageSize(int pageSize)
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
}
