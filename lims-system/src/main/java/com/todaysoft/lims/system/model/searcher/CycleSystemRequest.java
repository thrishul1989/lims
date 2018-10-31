package com.todaysoft.lims.system.model.searcher;

public class CycleSystemRequest
{
    private String taskId;
    
    private String userId;

    private String userName;
    
    private Integer pageNo;

    private Integer pageSize;
    
    private String productName;
    
    private String testingMethod;
    
    private String ownerId;
    private String marketingCenter;
    private String createTimeStart;
    private String createTimeEnd;
    private String planTimeStart;
    private String planTimeEnd;
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
    public String getProductName()
    {
        return productName;
    }
    public void setProductName(String productName)
    {
        this.productName = productName;
    }
    public String getTestingMethod()
    {
        return testingMethod;
    }
    public void setTestingMethod(String testingMethod)
    {
        this.testingMethod = testingMethod;
    }
    public String getOwnerId()
    {
        return ownerId;
    }
    public void setOwnerId(String ownerId)
    {
        this.ownerId = ownerId;
    }
    public String getMarketingCenter()
    {
        return marketingCenter;
    }
    public void setMarketingCenter(String marketingCenter)
    {
        this.marketingCenter = marketingCenter;
    }
    public String getCreateTimeStart()
    {
        return createTimeStart;
    }
    public void setCreateTimeStart(String createTimeStart)
    {
        this.createTimeStart = createTimeStart;
    }
    public String getCreateTimeEnd()
    {
        return createTimeEnd;
    }
    public void setCreateTimeEnd(String createTimeEnd)
    {
        this.createTimeEnd = createTimeEnd;
    }
    public String getPlanTimeStart()
    {
        return planTimeStart;
    }
    public void setPlanTimeStart(String planTimeStart)
    {
        this.planTimeStart = planTimeStart;
    }
    public String getPlanTimeEnd()
    {
        return planTimeEnd;
    }
    public void setPlanTimeEnd(String planTimeEnd)
    {
        this.planTimeEnd = planTimeEnd;
    }
    
    
}
