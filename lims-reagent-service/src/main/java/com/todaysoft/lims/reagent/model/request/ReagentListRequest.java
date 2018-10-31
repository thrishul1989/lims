package com.todaysoft.lims.reagent.model.request;

public class ReagentListRequest
{
    private String code;
    
    private String name;
    
    private String abbr;
    
    private Integer pageNo;
    
    private Integer pageSize;
    
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
    
    public String getAbbr()
    {
        return abbr;
    }
    
    public void setAbbr(String abbr)
    {
        this.abbr = abbr;
    }
    
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
}
