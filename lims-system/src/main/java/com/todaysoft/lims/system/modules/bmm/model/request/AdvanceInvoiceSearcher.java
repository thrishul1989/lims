package com.todaysoft.lims.system.modules.bmm.model.request;

import java.util.Set;

public class AdvanceInvoiceSearcher
{
    private String testingType;//营销中心
    
    private String creatorId;//申请人id
    
    private String customerId;//客户ID
    
    private String applyTimeStart;//申请时间
    
    private String applyTimeEnd;
    
    private int pageNo;
    
    private int pageSize;
    
    private String code;
    
    private Integer solveStatus;
    
    private String institution;//开票机构
    
    private String orderCode;
    
    private String keys;
    
    private Set<String> ids;
    
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
    
    public Integer getSolveStatus()
    {
        return solveStatus;
    }
    
    public void setSolveStatus(Integer solveStatus)
    {
        this.solveStatus = solveStatus;
    }
    
    public String getInstitution()
    {
        return institution;
    }
    
    public void setInstitution(String institution)
    {
        this.institution = institution;
    }
    
    public String getOrderCode()
    {
        return orderCode;
    }
    
    public void setOrderCode(String orderCode)
    {
        this.orderCode = orderCode;
    }

    public String getKeys()
    {
        return keys;
    }

    public void setKeys(String keys)
    {
        this.keys = keys;
    }

    public Set<String> getIds()
    {
        return ids;
    }

    public void setIds(Set<String> ids)
    {
        this.ids = ids;
    }

    
    
}
