package com.todaysoft.lims.order.model;

import java.util.List;

public class AccountBalanceModel
{
    private String batch;
    
    private String count;
    
    private List<String> data;
    
    private String totalBatch;
    
    private String header;
    
    public String getBatch()
    {
        return batch;
    }
    
    public void setBatch(String batch)
    {
        this.batch = batch;
    }
    
    public String getCount()
    {
        return count;
    }
    
    public void setCount(String count)
    {
        this.count = count;
    }
    
    public String getTotalBatch()
    {
        return totalBatch;
    }
    
    public void setTotalBatch(String totalBatch)
    {
        this.totalBatch = totalBatch;
    }
    
    public List<String> getData()
    {
        return data;
    }
    
    public void setData(List<String> data)
    {
        this.data = data;
    }
    
    public String getHeader()
    {
        return header;
    }
    
    public void setHeader(String header)
    {
        this.header = header;
    }
    
}
