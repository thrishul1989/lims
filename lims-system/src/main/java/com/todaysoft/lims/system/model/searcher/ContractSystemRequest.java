package com.todaysoft.lims.system.model.searcher;

public class ContractSystemRequest
{
    private String taskId;

    private String userId;

    private String userName;
    
    private Integer pageNo;

    private Integer pageSize;
    
    private String name;
    
    private String marketingCenter;
    
    private String signUser;
    
    private String status;

    private String colNames;
    
    public String getTaskId()
    {
        return taskId;
    }

    public void setTaskId(String taskId)
    {
        this.taskId = taskId;
    }

    public String getUserId()
    {
        return userId;
    }

    public void setUserId(String userId)
    {
        this.userId = userId;
    }

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
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

    public String getMarketingCenter()
    {
        return marketingCenter;
    }

    public void setMarketingCenter(String marketingCenter)
    {
        this.marketingCenter = marketingCenter;
    }

    public String getSignUser()
    {
        return signUser;
    }

    public void setSignUser(String signUser)
    {
        this.signUser = signUser;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getColNames()
    {
        return colNames;
    }

    public void setColNames(String colNames)
    {
        this.colNames = colNames;
    }
    
}
