package com.todaysoft.lims.system.model.searcher;

public class ConnectSearcher
{
    
    private Integer connectNum;
    
    private Integer pageNo;
    
    private Integer pageSize;
    
    private String connectSequence;
    
    public String getConnectSequence()
    {
        return connectSequence;
    }
    
    public void setConnectSequence(String connectSequence)
    {
        this.connectSequence = connectSequence;
    }
    
    private String id;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    private String dataObject;
    
    public String getDataObject()
    {
        return dataObject;
    }
    
    public void setDataObject(String dataObject)
    {
        this.dataObject = dataObject;
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
    
}
