package com.todaysoft.lims.system.model.searcher;

import java.util.Date;

import com.todaysoft.lims.system.model.vo.enums.MaterialsStatus;

public class MaterialsApplySearcher
{
    private Integer pageNo;
    
    private Integer pageSize;
    
    private MaterialsStatus status;
    
    private String testingType;
    
    private String proposer;
    
    private Date start;
    
    private Date end;
    
    private String code;
    
    public String getCode()
    {
        return code;
    }
    
    public void setCode(String code)
    {
        this.code = code;
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
    
    public MaterialsStatus getStatus()
    {
        return status;
    }
    
    public void setStatus(MaterialsStatus status)
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
    
    public String getProposer()
    {
        return proposer;
    }
    
    public void setProposer(String proposer)
    {
        this.proposer = proposer;
    }
    
    public Date getStart()
    {
        return start;
    }
    
    public void setStart(Date start)
    {
        this.start = start;
    }
    
    public Date getEnd()
    {
        return end;
    }
    
    public void setEnd(Date end)
    {
        this.end = end;
    }
    
}
