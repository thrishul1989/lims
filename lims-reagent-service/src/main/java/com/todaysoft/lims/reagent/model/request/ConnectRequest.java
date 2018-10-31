package com.todaysoft.lims.reagent.model.request;

public class ConnectRequest
{
    
    private Integer connectNum;
    
    private Integer pageNo;
    
    private Integer pageSize;
    
    private String id;

    private String deleted;//删除标记
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public Integer getConnectNum()
    {
        return connectNum;
    }
    
    public void setConnectNum(Integer connectNum)
    {
        this.connectNum = connectNum;
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

    public String getDeleted() {
        return deleted;
    }

    public void setDeleted(String deleted) {
        this.deleted = deleted;
    }
}
