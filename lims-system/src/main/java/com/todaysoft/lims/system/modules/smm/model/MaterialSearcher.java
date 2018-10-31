package com.todaysoft.lims.system.modules.smm.model;

public class MaterialSearcher
{
    private String id;
    private Integer type;
    
    private String name;
    
    private String equalName;
    
    private String sortId;
    
    private int pageNo;
    
    private int pageSize;

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

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

    public String getEqualName()
    {
        return equalName;
    }

    public void setEqualName(String equalName)
    {
        this.equalName = equalName;
    }

    public String getSortId()
    {
        return sortId;
    }

    public void setSortId(String sortId)
    {
        this.sortId = sortId;
    }

    public int getPageNo()
    {
        return pageNo;
    }

    public void setPageNo(int pageNo)
    {
        this.pageNo = pageNo;
    }

    public int getPageSize()
    {
        return pageSize;
    }

    public void setPageSize(int pageSize)
    {
        this.pageSize = pageSize;
    }
    
    
}
