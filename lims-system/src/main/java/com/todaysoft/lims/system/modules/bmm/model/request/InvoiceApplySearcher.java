package com.todaysoft.lims.system.modules.bmm.model.request;

public class InvoiceApplySearcher
{
    private Integer status;//状态：1-审核中，2-未通过，3-已开票，4-已寄送
    
    private String testingType;//营销中心
    
    private String creatorId;//申请人id
    
    private String customerId;//客户ID
    
    private String applyTimeStart;//申请时间
    
    private String applyTimeEnd;
    
    private int pageNo;
    
    private int pageSize;
    
    private String code;
    
    public Integer getStatus()
    {
        return status;
    }
    
    public void setStatus(Integer status)
    {
        this.status = status;
    }
    
    public String getTestingType()
    {
        return testingType;
    }
    
    public void setTestingType(String testingType)
    {
        this.testingType = testingType;
    }
    
    public String getCreatorId()
    {
        return creatorId;
    }
    
    public void setCreatorId(String creatorId)
    {
        this.creatorId = creatorId;
    }
    
    public String getCustomerId()
    {
        return customerId;
    }
    
    public void setCustomerId(String customerId)
    {
        this.customerId = customerId;
    }
    
    public String getApplyTimeStart()
    {
        return applyTimeStart;
    }
    
    public void setApplyTimeStart(String applyTimeStart)
    {
        this.applyTimeStart = applyTimeStart;
    }
    
    public String getApplyTimeEnd()
    {
        return applyTimeEnd;
    }
    
    public void setApplyTimeEnd(String applyTimeEnd)
    {
        this.applyTimeEnd = applyTimeEnd;
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
    
    public String getCode()
    {
        return code;
    }
    
    public void setCode(String code)
    {
        this.code = code;
    }
}
