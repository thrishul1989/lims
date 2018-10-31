package com.todaysoft.lims.system.model.vo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Pagination<T>
{
    private int pageNo;
    
    private int pageSize = 10;
    
    private int totalCount;
    
    private List<T> records;
    
    public Pagination(int pageNo, int pageSize, int totalCount)
    {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.totalCount = totalCount;
    }
    
    public Pagination()
    {
        
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
    
    @JsonIgnore
    public int getTotalPage()
    {
        int pageCount = totalCount / pageSize;
        
        if (pageCount == 0 || totalCount % pageSize != 0)
        {
            pageCount++;
        }
        
        return pageCount;
    }
    
    @JsonIgnore
    public boolean isLastPage()
    {
        return pageNo >= getTotalPage();
    }
}
