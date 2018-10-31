package com.todaysoft.lims.gateway.model;

import java.util.List;

public class Pagination<T>
{
    private int pageNo;
    
    private int pageSize;
    
    private int totalCount;
    
    private List<T> records;
    
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
    
    public int getTotalCount()
    {
        return totalCount;
    }
    
    public void setTotalCount(int totalCount)
    {
        this.totalCount = totalCount;
    }
    
    public List<T> getRecords()
    {
        return records;
    }
    
    public void setRecords(List<T> records)
    {
        this.records = records;
    }
}
